package com.test.utility;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendFileEmail {

	public static Session getMailSession() {
		


		// Get system properties
		Properties properties = System.getProperties();

		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");

		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");

		// Get the default Session object.
		Session session = Session.getInstance(properties,

		new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("demolibrarysm", "Demo@123");

			}

		});

		return session;
	}
	
	public static void sendUserCreationMail(String fname,String msg)
	{
		// Recipient's email ID needs to be mentioned.
				String to = "sonalimishra2525@gmail.com";

				// Sender's email ID needs to be mentioned
				String from = "demolibrarysm@gmail.com";
		try {
			Session session = getMailSession();
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			message.setSubject("Card Id Created");
			StringBuffer emailMessage = new StringBuffer("Dear "+fname+",");
			emailMessage.append("<br />");
			emailMessage.append(msg);
			/*message.setText("Dear "+fname+",");
			message.setText("\n");*/
			//message.setText( emailMessage.toString());
			message.setContent(emailMessage.toString(), "text/html; charset=utf-8");
			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
