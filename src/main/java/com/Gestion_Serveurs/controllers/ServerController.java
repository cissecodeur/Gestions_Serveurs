package com.Gestion_Serveurs.controllers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Gestion_Serveurs.entities.Server;
import com.Gestion_Serveurs.exportExcel.ServerFichierExcelExporter;
import com.Gestion_Serveurs.repositories.ServerRepository;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
@RequestMapping("/server")
@CrossOrigin(origins = "http://localhost:3000")
public class ServerController {
	
	 //Les elements a recuperer sur le website de twilio
	 public static final String ACCOUNT_SID = "ACdaaa09d9c9a59cddf916a552025f3ce6";
	 public static final String AUTH_TOKEN = "906739b9b8ef808473fbbb83d8d038c9";
	 public static final String TWILIO_NUMBER = "+17137664406";
	 
	 public static class MessageDetails {
	        public List<String> numbers;
	        public String message;
	    }
	
	   static {
	      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	   } 
	   
	 
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private ServerRepository serverRepository;
	
	  
	
	@GetMapping("/all")
	public List<Server> getAll(){
		List<Server> servers = serverRepository.findAll();
		   if(servers == null && servers.isEmpty()) {
			    throw new RuntimeException("Liste vide");
		   }
		   
		   return servers ;
	}
	
	@GetMapping("/all/{id}")
	public  Optional<Server>AfficherParId(@PathVariable("id") int id) {		 
		return serverRepository.findById(id);
	}
	

//	@PostMapping("/ajouter")
//	public ResponseEntity<Server> ajouterProduit(@RequestBody Server server, HttpServletRequest request) throws URISyntaxException{
//		  Server serverExixting = serverRepository.findByLibelle(server.getLibelle()); 
//		   if(serverExixting != null) {
//			   throw new RuntimeException("Ce nom de server est deja occupe");
//		   }
//	     Server resultat = serverRepository.save(server);    
//	     return ResponseEntity.created(new URI("api/ajouter/" + resultat.getId())).body(resultat);
//	}
	
	@PostMapping("/ajouter")
	public Server ajouterServeur(@RequestBody Server server) {
		return serverRepository.save(server);
	}
	
	

	@PutMapping("/modifier")
	public ResponseEntity<Server> ModifierProduit(@RequestParam("id") int id , @RequestBody Server server){
		Optional<Server> serverExixting = serverRepository.findById(server.getId()); 
		    if(serverExixting == null) {
		    	throw new RuntimeException("Ce utilisateur est introuvable");
		    }
		    
		Server resultat = serverRepository.save(server) ;
	   return ResponseEntity.ok().body(resultat);
	}

	@DeleteMapping("/supprimer/{id}")
	public ResponseEntity<?> SupprimerParId(@PathVariable int id) {
		serverRepository.deleteById(id);
	       return ResponseEntity.ok().build();
	}
	@DeleteMapping("/supprimer")
	public ResponseEntity<?> SupprimerTout() {
		serverRepository.deleteAll();
	       return ResponseEntity.ok().build();
	}
	
	
//	@RequestMapping("/rechercher")
//	public Page<Server> chercherParMotCle(@RequestParam(name = "mc" , defaultValue = "") String mc , 
//			                             @RequestParam(name = "page" , defaultValue = "0") int page , 
//			                             @RequestParam(name = "size" , defaultValue = "5") int size){
//		return serverRepository.Rechercher("%"+mc+"%",false,PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"id")));
//		
//	}
	
	
	
	

 @PostMapping("/eteindreOne")
 public String ExtinctionOneServer(@RequestBody Server serv) throws Exception{
	  	    
        
        String command1 = "sudo shutdown -h now";
		if(serv != null) {
			 				
				  JSch jsch = new JSch();
				 // jsch.addIdentity(privateKeyPath,passphrase);
				  Session session = jsch.getSession( serv.getLogin(), serv.getHost(),22);	
				 // jsch.setKnownHosts("/home/cisse/known_hosts");
				  session.setPassword(serv.getPassword());					  
				  Properties config = new Properties();
				  config.put("StrictHostKeyChecking", "no");
				  session.setConfig(config);
		          session.connect();
				  Channel channel = session.openChannel("exec");
				  
				  
				  ((ChannelExec) channel).setCommand(command1);
				  ((ChannelExec) channel).setErrStream(System.err);
				  channel.connect();
				  
				  InputStream in = channel.getInputStream();
				  BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				  String line ;
				  while((line = reader.readLine()) != null ) {
					  throw new Exception("Mise a jour du serveur en cours");
				  }
			      channel.disconnect();
			      session.disconnect();
			      
		
			      
	//==================================================================== Debut Notfification par email ========================================================================//   
			      Server existingServeurEmail = serverRepository.findByLibelle(serv.getLibelle());
	                 if(existingServeurEmail == null) {
	                	 throw	new RuntimeException("serveur introuvable");
	                 }
			        SimpleMailMessage msg = new SimpleMailMessage();
			        msg.setTo(existingServeurEmail.getEmail());

			       msg.setSubject("Arret du serveur");
			       msg.setText(" Votre server " + existingServeurEmail.getHost() + " vient d'etre arrete par le user " + serv.getLogin());

			       javaMailSender.send(msg);
 //=============================================================== Fin Notfification par email ==============================================================================//		       
			       Server existingServeurNumber = serverRepository.findByLibelle(serv.getLibelle());
	                 if(existingServeurNumber == null) {
	                	 throw	new RuntimeException("serveur introuvable");
	                 }
			       Message.creator(new PhoneNumber(existingServeurNumber.getNumero()), new PhoneNumber(TWILIO_NUMBER),
			    	         " Extinction de la machine ayant pour ip " + serv.getHost() + " par le user " + serv.getLogin()).create();
  //============================================================Fin Sms==================================================================================================//		       
			       
		}    
		else {
			
			throw new RuntimeException("ce serveur est introuvable");
		}
		return "Votre serveur a ete eteint";
  }
	
	
	
	
	
	
	
	

  @PostMapping("/eteindreAll")
  public List<Server> ExtinctionAllServer() throws JSchException, IOException{
	
		
		 List<Server> listServersExisting = serverRepository.findAll();
        String command1 = "sudo shutdown -h now";
        
        
        if(listServersExisting != null && !listServersExisting.isEmpty()) {
        	listServersExisting.stream().forEach(serv->{
 				
			  JSch jsch = new JSch();
			
			  Session session;
			try {
				session = jsch.getSession( serv.getLogin(), serv.getHost(),22);
				session.setPassword(serv.getPassword());					  
				Properties config = new Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
		          session.connect(30000);
				  Channel channel = session.openChannel("exec");
				  
				  
				  ((ChannelExec) channel).setCommand(command1);
				  ((ChannelExec) channel).setErrStream(System.err);
				  channel.connect();

			      channel.disconnect();
			      session.disconnect();
			      
			      
			    //==================================================================== Debut Notfification par email ========================================================================//   
    		      Server existingServeurEmail = serverRepository.findByLibelle(serv.getLibelle());
                  if(existingServeurEmail == null) {
                 	 throw	new RuntimeException("serveur introuvable");
                  }
    		        SimpleMailMessage msg = new SimpleMailMessage();
    		        msg.setTo(existingServeurEmail.getEmail());

    		       msg.setSubject("Arret du serveur");
    		       msg.setText(" Votre server " + existingServeurEmail.getHost() + " vient d'etre arrete par le user " + serv.getLogin());

    		       javaMailSender.send(msg);
    //=============================================================== Fin Notfification par email ==============================================================================//		       
    		       Server existingServeurNumber = serverRepository.findByLibelle(serv.getLibelle());
                  if(existingServeurNumber == null) {
                 	 throw	new RuntimeException("serveur introuvable");
                  }
    		       Message.creator(new PhoneNumber(existingServeurNumber.getNumero()), new PhoneNumber(TWILIO_NUMBER),
    		    	         " Extinction de la machine ayant pour ip " + serv.getHost() + " par le user " + serv.getLogin()).create();
    //============================================================Fin Sms==================================================================================================//		       

			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		    

	                          } );
   
                        }   else {
		
		                          throw new RuntimeException("La liste de serveurs existant est vide");
	                             }
	    	   
		
		       return listServersExisting;
  }
	
	
	
	
	
	
 @PostMapping("/redemarrerOne")
 public String RedemarrageOneServer(@RequestBody Server serv,HttpServletRequest request) throws JSchException, IOException{

        String command1 = "sudo reboot";
		if(serv != null && request != null) {
			 				
				  JSch jsch = new JSch();
				  Session session = jsch.getSession( serv.getLogin(), serv.getHost(),22);	
				  session.setPassword(serv.getPassword());					  
				  Properties config = new Properties();
				  config.put("StrictHostKeyChecking", "no");
				  session.setConfig(config);
		          session.connect(30000);
				  Channel channel = session.openChannel("exec");
				           
				  ((ChannelExec) channel).setCommand(command1);
				  ((ChannelExec) channel).setErrStream(System.err);
				  channel.connect();
			      channel.disconnect();
			      session.disconnect();
			      
	//==================================================================== Debut Notfification par email ========================================================================//      
			      Server existingServeurEmail = serverRepository.findByLibelle(serv.getLibelle());
	                 if(existingServeurEmail == null) {
	                	 throw	new RuntimeException("serveur introuvable");
	                 }
			    	   SimpleMailMessage msg = new SimpleMailMessage();
			    	   msg.setTo(existingServeurEmail.getEmail());

				       msg.setSubject("Redemarrage serveur");
				       msg.setText(" Votre server " + serv.getHost() + " vient d'etre redemarre par le user " + serv.getLogin() 
				       + " la requete a ete initiée par le ip distant suivant " + request.getRemoteAddr());

				       javaMailSender.send(msg);
			       
	//=============================================================== Fin Notfification par email ==============================================================================//	
				       
			           Server existingServeurNumber = serverRepository.findByLibelle(serv.getLibelle());
			                 if(existingServeurNumber == null) {
			                	 throw	new RuntimeException("serveur introuvable");
			                 }
				       Message.creator(new PhoneNumber(existingServeurNumber.getNumero()), new PhoneNumber(TWILIO_NUMBER),
				    		   " Votre server " + serv.getHost() + " vient d'etre redemarre par le user " + serv.getLogin() 
						       + " la requete a ete initiée par le ip distant suivant " + request.getRemoteAddr()).create();
				       
//				       OkHttpClient client = new OkHttpClient();
//
//				       MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//				       RequestBody body = RequestBody.create(mediaType, "username=yacoubc01@gmail.com&password=your_password&sender_id=your_sender_id&phone=your_receiver_phone_number&message=your_message_here");
//				       Request request = new Request.Builder()
//				         .url("https://vavasms.com/api/v1/text/single")
//				         .post((com.squareup.okhttp.RequestBody) body)
//				         .addHeader("Content-Type", "application/x-www-form-urlencoded")
//				         .addHeader("Accept", "/")
//				         .addHeader("Host", "vavasms.com")
//				         .build();
//				       Response response = client.newCall(request).execute();
				       
  //============================================================Fin Sms==================================================================================================//			
		}    
		else {
			
			throw new RuntimeException("ce serveur est introuvable");
		}
		return "Votre serveurs a ete redemarre";
 }
	
	
	
	

@PostMapping("/redemarrerAll")
public List<Server> RedemarrageAllServer() throws JSchException, IOException{
	
		 List<Server> listServersExisting = serverRepository.findAll();
         String command1 = "sudo reboot";
        
        if(listServersExisting != null && !listServersExisting.isEmpty()) {
        	listServersExisting.stream().forEach(serv->{
 				
				JSch jsch = new JSch();
			
			 // jsch.addIdentity(privateKeyPath,passphrase);
			  Session session;
			try {
				session = jsch.getSession( serv.getLogin(), serv.getHost(),22);
				session.setPassword(serv.getPassword());					  
				Properties config = new Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
		          session.connect(30000);
				  Channel channel = session.openChannel("exec");
				  
				  
				  ((ChannelExec) channel).setCommand(command1);
				  ((ChannelExec) channel).setErrStream(System.err);
				  channel.connect();

			      channel.disconnect();
			      session.disconnect();
			      
			      
	//==================================================================== Debut Notfification par email ========================================================================//   
    		      Server existingServeurEmail = serverRepository.findByLibelle(serv.getLibelle());
                  if(existingServeurEmail == null) {
                 	 throw	new RuntimeException("serveur introuvable");
                  }
    		        SimpleMailMessage msg = new SimpleMailMessage();
    		        msg.setTo(existingServeurEmail.getEmail());

    		       msg.setSubject("Arret du serveur");
    		       msg.setText(" Votre server " + existingServeurEmail.getHost() + " vient d'etre arrete par le user " + serv.getLogin());

    		       javaMailSender.send(msg);
    //=============================================================== Fin Notfification par email ==============================================================================//		       
    		       Server existingServeurNumber = serverRepository.findByLibelle(serv.getLibelle());
                  if(existingServeurNumber == null) {
                 	 throw	new RuntimeException("serveur introuvable");
                  }
    		       Message.creator(new PhoneNumber(existingServeurNumber.getNumero()), new PhoneNumber(TWILIO_NUMBER),
    		    	         " Extinction de la machine ayant pour ip " + serv.getHost() + " par le user " + serv.getLogin()).create();
    //============================================================Fin Sms==================================================================================================//		       

			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			    

	} );
   
 }   else {
		
		throw new RuntimeException("La liste de serveurs existant est vide");
	}
	    	   		
		return listServersExisting;
  }

	
	
	
	
 @PostMapping("/secureDdos")
 public String CopyDistant(@RequestBody Server serv) throws JSchException, IOException, SftpException, InterruptedException{
	 
	        String command1 = "cd /tmp/ && chmod +x config.sh && ./config.sh";
	        String remotePath = "/tmp/config.sh";
	           //Indiquer le fichier a ajouter
	         InputStream localInputFile = new FileInputStream("/home/cisse/Documents/smile/Gestion_Serveurs/config.sh");
	         	                    
	          
			if(serv != null) {
				 				
					  JSch jsch = new JSch();
					  Session session = jsch.getSession( serv.getLogin(), serv.getHost(),22);	
					  session.setPassword(serv.getPassword());					  
					  Properties config = new Properties();
					  config.put("StrictHostKeyChecking", "no");
					  session.setConfig(config);
			          session.connect(30000);
					  Channel channel = session.openChannel("sftp");
					  channel.connect();
					  ((ChannelSftp) channel).put(localInputFile, remotePath);
					  ((ChannelSftp) channel).chmod(777, remotePath);
					
					  			  					  				       
					  channel.disconnect();
				      session.disconnect();
					  
				      
			}
	 return "Votre serveur est protegé contre les attaques de type DDOS";
		
  }
	


 
 
 
 
  @PostMapping("/miseAjourMachine")
  public String UpdateOsServer(@RequestBody Server serv) throws JSchException, IOException{
		
        String command1 = "sudo apt update -y";
		if(serv != null) {
			 				
				  JSch jsch = new JSch();
				 // jsch.addIdentity(privateKeyPath,passphrase);
				  Session session = jsch.getSession( serv.getLogin(), serv.getHost(),22);	
				  jsch.setKnownHosts("/home/cisse/known_hosts");
				  session.setPassword(serv.getPassword());					  
				  Properties config = new Properties();
				  config.put("StrictHostKeyChecking", "no");
				  session.setConfig(config);
		          session.connect(30000);
				  Channel channel = session.openChannel("exec");
				  
				  
				  ((ChannelExec) channel).setCommand(command1);
				  ((ChannelExec) channel).setErrStream(System.err);
				  channel.connect();
				  
				  
			      
	//==================================================================== Debut Notfification par email ========================================================================//   
			      Server existingServeurEmail = serverRepository.findByLibelle(serv.getLibelle());
	                 if(existingServeurEmail == null) {
	                	 throw	new RuntimeException("serveur introuvable");
	                 }
			        SimpleMailMessage msg = new SimpleMailMessage();
			        msg.setTo(existingServeurEmail.getEmail());

			       msg.setSubject("Mise a jour");
			       msg.setText(" Votre server " + existingServeurEmail.getHost() + " vient d'etre mis a jour  par le user " + serv.getLogin());

			       javaMailSender.send(msg);
 //=============================================================== Fin Notfification par email ==============================================================================//		       
			       Server existingServeurNumber = serverRepository.findByLibelle(serv.getLibelle());
	                 if(existingServeurNumber == null) {
	                	 throw	new RuntimeException("serveur introuvable");
	                 }
			       Message.creator(new PhoneNumber(existingServeurNumber.getNumero()), new PhoneNumber(TWILIO_NUMBER),
			    	         " mise a jour  de la machine ayant pour ip " + serv.getHost() + " par le user " + serv.getLogin()).create();
  //============================================================Fin Sms==================================================================================================//		       
			       
		}    
		else {
			
			throw new RuntimeException("ce serveur est introuvable");
		}
		return "le serveur a ete mis a jour";
  }
	

	
	
	
	//Export du fichier au format excell
	@GetMapping("exporter/servers.xlsx")
	public void exporterFichierAuformatExcel(HttpServletResponse response) throws IOException {
		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
		dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment ; filename = servers.xlsx"+ dateTimeInGMT.format(new Date()) );
		List<Server> servers = serverRepository.findAll();
		ByteArrayInputStream fichierAtraiter = ServerFichierExcelExporter.listServerAExporter(servers);
	    IOUtils.copy(fichierAtraiter, response.getOutputStream());
	}

}
