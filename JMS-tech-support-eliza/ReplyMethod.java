
/**
 * Functional interface ReplyMethod.
 *
 * @author Wolfgang Renz
 * @version May 2020
 */
public interface ReplyMethod
{
    String replyTo(Object paramter); // single method returning String
    
    // Object replyTo(Object paramter); // single method call
    
    // Object replyTo(Object call, Object parameter);
}
