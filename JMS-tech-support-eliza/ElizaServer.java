import javax.jms.JMSException;

/**
 * Write a description of class ElizaServer here.
 *
 * @author Wolfgang Renz
 * @version May 2020
 */
public class ElizaServer
{
    public static void main(String [] args) throws JMSException
    {
        Responder responder= new Responder(new ReplyToStringSet());
        responder.answer();
        System.out.println("Tech support Eliza finished.");
    }
}
