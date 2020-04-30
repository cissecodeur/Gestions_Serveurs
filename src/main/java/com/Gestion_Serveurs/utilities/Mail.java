package com.Gestion_Serveurs.utilities;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class Mail {
	@Bean
	public JavaMailSender getJavaMailSender() {
	       JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	       mailSender.setHost("smtp.gmail.com");
	       mailSender.setPort(587);

	       mailSender.setUsername("yacoubc01@gmail.com");
	       mailSender.setPassword("78773960#");

	       Properties props = mailSender.getJavaMailProperties();
	       props.put("mail.transport.protocol", "smtp");
	       props.put("mail.smtp.auth", "true");
	       props.put("mail.smtp.starttls.enable", "true");
	       props.put("mail.debug", "true");

	       return mailSender;
	}

}
