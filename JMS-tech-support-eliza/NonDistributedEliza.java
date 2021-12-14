import java.util.HashSet;
import javax.jms.JMSException;

/**
 * This class implements a technical support system. It is the top level class 
 * in this project. The support system communicates via text input/output 
 * in the text terminal.
 * 
 * This class uses an object of class InputReader to read input from the user,
 * and an object of class Responder to generate responses. It contains a loop
 * that repeatedly reads input and generates output until the users wants to 
 * leave.
 * 
 * @author     Michael Kölling and David J. Barnes
 * @version    1.0 (2016.02.29)
 */
public class NonDistributedEliza
{
    public static void main(String [] args) throws JMSException
    {
        (new ElizaClient(new ResponseProvider())).start();
    }
}
