package edu.sjsu.cmpe275.email;
import java.util.UUID;

/**
 * @author imran
 *
 */
public class TokenGenerator {
    
     /**
     * @return
     */
    public static String randomToken() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
       
        return randomUUIDString;
    }
    
    
}
