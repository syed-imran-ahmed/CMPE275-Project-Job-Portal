package edu.sjsu.cmpe275.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author imran
 *
 */
class GMailAuthenticator extends Authenticator {
     String userName;
     String password;
     
     /**
     * @param username
     * @param password
     */
    public GMailAuthenticator (String username, String password)
     {
        super();
        this.userName = username;
        this.password = password;
     }
    
    
    public PasswordAuthentication getPasswordAuthentication()
    {
       return new PasswordAuthentication(userName, password);
    }
}