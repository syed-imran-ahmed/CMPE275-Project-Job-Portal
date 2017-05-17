package edu.sjsu.cmpe275.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.sjsu.cmpe275.model.CompanyJobPosts;

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
	
	
	public static void emailAppliedJob(String receiverMail, long jobid, String company, String title, String description,String location){
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
	            message.setSubject("Thank you for applying to "+company);
	            message.setText("You have applied to job with job id: "+jobid+
	            		"\n The position is for "+title+
	            		" located in "+location+
	            		"\n Description of the job \n"+description+
	            		"\n\nRegards,\n" + "275 Project Team");
	            System.out.println("message"+receiverMail);
	            Transport.send(message);
	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }
	
	public static void emailModifiedJobTrigger(String receiverMail, long jobId){
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
	            message.setSubject("Job Post "+ jobId+" Modified");
	            message.setText("\n The job post you applied have been modified. Please check.\n\nRegards,\n" + "275 Project Team");
	            System.out.println("message"+receiverMail);
	            Transport.send(message);
	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }
}
