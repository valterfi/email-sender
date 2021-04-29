package com.valterfi.emailsender.service;

import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSenderTask extends TimerTask {

	private String subject;

	private String content;

	private String recipient;

	private String username;

	private String password;

	public EmailSenderTask(String subject, String content, String recipient, String username, String password) {
		this.subject = subject;
		this.content = content;
		this.recipient = recipient;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				try {
					return new PasswordAuthentication(username, ProtectedConfigFile.decrypt(password));
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.recipient));
			message.setSubject(this.subject);
			message.setText(this.content);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
