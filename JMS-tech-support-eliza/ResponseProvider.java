import java.io.Serializable;

/**
 * ResponseProvider for non-distributed Version of Eliza tech support
 * ICaller supported for use in ElizaClient.<br>
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ResponseProvider extends ReplyToStringSet implements ICaller
{
    /**
     * Constructor for objects of class ResponseProvider
     */
    public ResponseProvider()
    {
        // super();
    }

    /**
     * Client-side responseToCall implementation
     *
     * @param text shall be {@code java.util.HashSet<String>} 
     * @return reponse
     */
    @Override
    public String responseToCall(Serializable text)
    {
        return replyTo(text);
    }

    @Override
    public void eot()
    {
        // nothing to be done
    }
}
