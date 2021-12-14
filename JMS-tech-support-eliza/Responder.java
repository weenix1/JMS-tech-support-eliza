import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import javax.jms.*;

/**
 * Class Responder
 * 
 * @author Wolfgang Renz
 * @version May 2020
 * 
 */
class Responder extends JMSClient{
    // private String destination; //in super class initialized befor connect
    private MessageConsumer consumer;
    private MessageProducer replyChannel;
    private ReplyMethod replyToRequest;

    public Responder(String destination) throws JMSException
    {
        super(destination);
        assert(connected==true);
        this.destination= destination;
    }

    public Responder(ReplyMethod replyToRequest) throws JMSException
    {
        this("request");
        this.replyToRequest= replyToRequest;
    }

    @Override
    protected boolean connect() throws JMSException
    {
        if(!connected) connected= super.connect();
        Destination dest = session.createQueue(destination);
        consumer = session.createConsumer(dest);
        // replay channel
        replyChannel= session.createProducer(null);
        replyChannel.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        return connected;
    }

    public void answer() throws JMSException {
        if(!connected)connect();
        System.err.println(this+ ": Listening to call...");
        int received= 0, answered= 0;
        while(true) {
            Message msg = consumer.receive();
            if( msg instanceof ObjectMessage ) {
                Object body = ((ObjectMessage) msg).getObject();
                TextMessage answer= session.createTextMessage();
                String reply= (String)replyToRequest.replyTo(body);
                answer.setText(reply);
                answer.setJMSCorrelationID(msg.getJMSCorrelationID());
                replyChannel.send(msg.getJMSReplyTo(), answer);
            } else if (msg instanceof TextMessage){
                String body = ((TextMessage) msg).getText();
                if( body.equals("EOT")) {
                    System.out.println(this+ ": EOT arrived");
                    close();
                    break;
                }
                else {
                    System.err.println(this+ ": Unexpected message: "+body);
                }
            }
            else {
                System.err.println("Unexpected message type: "+msg.getClass());
            }
        }
    }

}
