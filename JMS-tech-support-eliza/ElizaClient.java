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
 * @author   Michael Kölling and David J. Barnes, Wolfgang Renz (JMS-version)
 * @version  1.1 (2016.02.29) (2020.05.25)
 */
public class ElizaClient
{
    private InputReader reader;
    ICaller caller;

    /**
     * Creates a technical support system.
     */
    public ElizaClient(ICaller caller) 
    {
        reader = new InputReader();
        this.caller= caller;
    }

    public void start() throws JMSException
    {
        boolean finished = false;
        printWelcome();
        while(!finished) {
            HashSet<String> input = reader.getInput();
            // get all the "words" in the input sentence as a HashSet
            finished= input.contains("bye")||
            input.contains("quit")||
            input.contains("exit");            
            if( !finished) {
                String response= caller.responseToCall(input);
                // blocking call, a time-out should be introduced!
                // give the words to the responder so that it can
                // find a keyword in the set to retrieve a related answer!
                System.out.println(response);
            }
        }
        printGoodbye();
        caller.eot();
        System.out.println("EOT");
    }

    /**
     * Print a welcome message to the screen.
     */
    private void printWelcome()
    {
        System.out.println("Welcome to the DodgySoft Technical Support System.");
        System.out.println("You talk to an Eliza type reponder.");
        System.out.println("Please tell us about your problem.");
        System.out.println("We will assist you with any problem you might have.");
        System.out.println("To exit, please type 'bye', 'quit' or 'exit'.");
    }

    /**
     * Print a good-bye message to the screen.
     */
    private void printGoodbye()
    {
        System.out.println("Nice talking to you. Bye...");
    }
    
    public static void main(String [] args) throws JMSException
    {
        (new ElizaClient(new Caller())).start();
    }
}
