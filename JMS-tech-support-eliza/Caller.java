import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Random;
import java.io.Serializable;

/**
 * Request-Response Caller
 * cf.
 * https://activemq.apache.org/how-should-i-implement-request-response-with-jms
 * 
 * @author Wolfgang Renz
 * @version May 2020
 */
class Caller extends JMSClient implements ICaller{
    private static final int TIMESTEP=2000; // milliseconds
    private static final int SIZE = 256;

    private MessageProducer producer;
    private MessageConsumer consumer;
    private Destination responseChannel;
    private int messageID;

    public Caller(String destination) throws JMSException
    {
        super(destination);
        assert(connected==true);
    }

    public Caller() throws JMSException
    {   
        this("request");
    }

    @Override
    protected boolean connect() throws JMSException
    {
        if(!connected) connected= super.connect();
        Destination dest = session.createQueue(destination);
        producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);     
        messageID=0;
        // response channel
        responseChannel= session.createTemporaryQueue();
        consumer= session.createConsumer(responseChannel);
        return connected;
    }
       
    public void printResponseToCall(String text) throws JMSException
    {
        System.out.println(responseToCall( text));
    }

    public String responseToCall(Serializable text) throws JMSException
    {
        // check precondition
        if(!connected) connect();
        assert(connected== true);
        // 
        Message msg= session.createObjectMessage(text);
        msg.setIntProperty("id", ++messageID);
        msg.setJMSReplyTo(responseChannel);
        String correlationId = this.createRandomString();
        msg.setJMSCorrelationID(correlationId);
        producer.send(msg);
        return listen(correlationId);
    }

    private String listen(String correlationId) throws JMSException
    {
        String response= "no response";
        Message msg = consumer.receive();
        if( msg instanceof TextMessage ) {
            if( msg.getJMSCorrelationID().equals(correlationId) ){
                response = ((TextMessage) msg).getText();
            }
            else{
                System.err.println("Unexpected message id");
            }
        }
        else {
            System.err.println("Unexpected message type: "+msg.getClass());
        }
        return response;
    }

    private void sendMessage(String text) throws JMSException
    {
        if(!connected) connect();
        assert(connected== true);
        TextMessage textMessage= session.createTextMessage(text);
        textMessage.setIntProperty("id", ++messageID);
        producer.send(textMessage);
    }

    public void eot() throws JMSException
    {
        sendMessage("EOT");
        System.err.println("EOT sent.");
        close();
    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
