package com.blz.bookstoreuser.service;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

/**
 *  
 * Purpose:Mail service created for the sending mail to the user
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 
@Component
public class MailService {
	public static void send(String toEmail, String subject, String body){

		final String fromEmail = System.getenv("Email");
		// requires valid gmail id
		final String password = System.getenv("password1"); // correct password for gmail id
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		// SMTP Host
		props.put("mail.smtp.port", "587");
		// TLS Port
		props.put("mail.smtp.auth", "true");
		// enable authentication
		props.put("mail.smtp.ssl.protocols","TLSv1.2");

		props.put("mail.smtp.starttls.enable", "true");
		// enable STARTTLS
		// to check email sender credentials are valid or not
		Authenticator auth = new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(fromEmail, password);

			}
		};
		javax.mail.Session session = Session.getInstance(props, auth);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress("no_reply@gmail.com", "NoReply"));
			msg.setReplyTo(InternetAddress.parse(System.getenv("Email"), false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
			System.out.println("Email Sent Successfully........."); }
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getLink(String string, Long id) {
		return null;
	}
}