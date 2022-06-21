package com.training.tasks.task1.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailService implements EmailSender {
	private static final String FROM_USERNAME = "company Email";
	private static final String FROM_PASSWORD = "company Email App Pasword";

	public void send(String recipientEmails, String messageSubject, String messageText) {
		Properties prop = new Properties();

//		prop.put("mail.smtp.auth", true);
//		prop.put("mail.smtp.starttls.enable", "true");
//		prop.put("mail.smtp.host", "smtp.mailtrap.io");
//		prop.put("mail.smtp.port", "25");
//		prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

		String host = "smtp.gmail.com";
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_USERNAME, FROM_PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM_USERNAME));
			message.setRecipients(
					Message.RecipientType.TO, InternetAddress.parse(recipientEmails));
			message.setSubject(messageSubject);

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(messageText, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
