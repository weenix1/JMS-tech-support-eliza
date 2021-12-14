import javax.jms.JMSException;
import java.io.Serializable;

/**
 * ICaller
 *
 * @author Wolfgang Renz
 * @version May 2020
 */
public interface ICaller
{
    /**
     *  eot sends an EndOfTransmission and quits the connection
     */
    void eot() throws JMSException;
    
    /**
     * responseToCall calls with the object argument and returns text.
     * Future versions could return a general object
     *
     * @param objec to be sent
     * @return response received
     */
    String responseToCall(Serializable object) throws JMSException;
}
