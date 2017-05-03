package edu.sjsu.cmpe275.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author imran
 *
 */
public class ActivationEmail {

	 /**
	 * @param name
	 * @param receiverMail
	 * @param tokenID
	 */
	public static void emailRecommendTrigger(String receiverMail, String tokenID){
	       final String username = "sjsuprojects@gmail.com";
	      final String password = "Aios@123";
	        String[] to = { receiverMail };
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        Session session = Session.getInstance(props, new GMailAuthenticator(username, password));
	        
	        try {
	            Message message = new MimeMessage(session);
	             InternetAddress me = new InternetAddress("sjsuprojects@gmail.com");
	                try {
	                    me.setPersonal("Job Portal 275");
	                } catch (UnsupportedEncodingException e) {
	                    e.printStackTrace();
	                }
	                message.setFrom(me);
	            for (int i = 0; i < to.length; i++) {
	                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
	            }
	            message.setSubject("Activation Email");
	            message.setText("\n Your Verification Code "+tokenID+"\n\nRegards,\n" + "275 Project Team");
	            System.out.println("message"+receiverMail);
	            Transport.send(message);
	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    

	 /**
	 * @param name
	 * @param receiverMail
	 * @param tokenID
	 */
	public static void emailAckTrigger(String receiverMail, String tokenID){
		 final String username = "sjsuprojects@gmail.com";
	      final String password = "Aios@123";
	        String[] to = { receiverMail };
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        Session session = Session.getInstance(props, new GMailAuthenticator(username, password));
	        
	        try {
	            Message message = new MimeMessage(session);
	             InternetAddress me = new InternetAddress("sjsuprojects@gmail.com");
	                try {
	                    me.setPersonal("Job Portal 275");
	                } catch (UnsupportedEncodingException e) {
	                    e.printStackTrace();
	                }
	                message.setFrom(me);
	            for (int i = 0; i < to.length; i++) {
	                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
	            }
	            message.setSubject("Thank you for verification");
	            message.setText("\n"+tokenID+"\n\nRegards,\n" + "275 Project Team");
	            System.out.println("message"+receiverMail);
	            Transport.send(message);
	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }   
}
