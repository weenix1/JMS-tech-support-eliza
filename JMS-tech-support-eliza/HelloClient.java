import javax.jms.JMSException;

/**
 * Write a description of class Client here.
 *
 * @author Wolfgang Renz
 * @version May 2020
 */
public class HelloClient
{
    public static void main(String [] args) throws JMSException
    {
        ICaller caller= new Caller();
        String response= caller.responseToCall("Hello");
        System.out.println("Hello sent.");
        System.out.println("Response received: "+response);
        caller.eot();
        System.out.println("EOT sent.");
    }
}
