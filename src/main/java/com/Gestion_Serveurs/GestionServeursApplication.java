package com.Gestion_Serveurs;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.Gestion_Serveurs.entities.Server;
import com.Gestion_Serveurs.repositories.ServerRepository;

@SpringBootApplication
public class GestionServeursApplication implements CommandLineRunner {

	
	   
	 @Autowired
	private ServerRepository serverRepository;
	public static void main(String[] args) {
		SpringApplication.run(GestionServeursApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		serverRepository.save(new Server(null,"ubuntu2","192.168.0.3","cisse", 
				"78773960#","yacoubc01@gmail.com","+22578773960"));
		
		serverRepository.save(new Server(null,"ubuntu3","192.168.0.104","cisse", 
				"78773960#","yacoubc01@gmail.com","+22578773960"));


		serverRepository.save(new Server(null,"ubuntu4","192.168.0.105","cisse", 
				"78773960#","yacoubc01@gmail.com","+22578773960"));

       
	
		
	}
	
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
