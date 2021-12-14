import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Generic JMSClient for Openwire ActiveMQ.
 *
 * @author Wolfgang Renz
 * @version May 2020
 */
public abstract class JMSClient
{
    protected Session session;
    protected String destination;
    protected boolean connected;

    private ActiveMQConnectionFactory factory;
    private Connection connection;
    private String user, password;

    /**
     * Constructor for objects of class JMSClient
     */
    public JMSClient(String host, int port, String user, String password, 
    String dest) throws JMSException
    {
        factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
        setTrustedPackages();
        this.user= user;
        this.password= password;
        this.destination= dest;
        connected= connect();
    }

    public void setTrustedPackages()
    {
        factory.setTrustedPackages(new ArrayList(
                Arrays.asList(
                    "org.apache.activemq.test,org.apache.camel.test,java.util"
                    .split(",")
                )
            ));
    }

    /**
     * Default constructor
     */
    public JMSClient(String destination) throws JMSException
    {
        this("localhost", 61616, "admin", "password", destination);
    }

    protected boolean connect() throws JMSException
    {    
        connection = factory.createConnection(user, password);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        return true;
    }

    protected boolean close() throws JMSException
    {
        connection.close();
        connected= false;
        return connected;
    }
}
