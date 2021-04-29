package com.valterfi.emailsender.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.valterfi.emailsender.service.EmailSenderTask;

@RestController
@CrossOrigin(origins = "*")
public class EmailSenderController {

	@Value( "${email.credential.username}")
	private String username;

	@Value( "${email.credential.password}")
	private String password;

	@PostMapping("/send/{recipient}")
	public void send(@PathVariable("recipient") String recipient, @RequestBody String content) {

		String subject = "Message from Cabidi Studio Website";

		LocalDateTime timeLater = LocalDateTime.now().plusSeconds(2);
		Date timeLaterAsDate = Date.from(timeLater.atZone(ZoneId.systemDefault()).toInstant());

		new Timer().schedule(new EmailSenderTask(subject, content, recipient, username, password), timeLaterAsDate);

	}

}
