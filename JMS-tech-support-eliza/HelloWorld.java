import javax.jms.JMSException;

/**
 * Write a description of class HelloWorld here.
 *
 * @author Wolfgang Renz
 * @version May 2020
 */
public class HelloWorld
{
    public static void main(String [] args) throws JMSException
    {
        // Responder responder= new Responder((ref,call)-> (String)call+ " world");
        Responder responder= new Responder(call-> (String)call+ " world!");
        responder.answer();
        System.out.println("responded.");
    }
}
