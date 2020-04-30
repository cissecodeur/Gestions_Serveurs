package com.Gestion_Serveurs.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Gestion_Serveurs.entities.Statut;
import com.Gestion_Serveurs.repositories.StatutRepository;

@RestController
@RequestMapping("/statut")
public class StatutController {
	
	@Autowired
	private StatutRepository statutRepository;
	
	@GetMapping("/all")
	public List<Statut> getAll(){
		List<Statut> Statuts = statutRepository.findAll();
		   if(Statuts == null && Statuts.isEmpty()) {
			    throw new RuntimeException("Liste vide");
		   }
		   
		   return Statuts ;
	}
	
	@GetMapping("/{id}")
	public  Optional<Statut>AfficherParId(@RequestParam int id) {
		Optional<Statut>  Statut = statutRepository.findById(id);
		     if(Statut == null) {
		    	 ResponseEntity.notFound();
		     }
		return Statut;
	}
	


	@PostMapping("/ajouter")
	public ResponseEntity<Statut> ajouterStatut(@RequestBody Statut Statut, HttpServletRequest request) throws URISyntaxException{
		Statut StatutExixting = statutRepository.findByLibelle(Statut.getLibelle()); 
		   if(StatutExixting != null) {
			   throw new RuntimeException("Ce nom de Statut est deja occupe");
		   }
		   Statut resultat = statutRepository.save(Statut);    
	     return ResponseEntity.created(new URI("api/ajouter/" + resultat.getId())).body(resultat);
	}

	@PutMapping("/modifier")
	public ResponseEntity<Statut> modifierStatut(@PathVariable("id") int id , @RequestBody Statut Statut){
		Optional<Statut> StatutExixting = statutRepository.findById(Statut.getId()); 
		    if(StatutExixting == null) {
		    	throw new RuntimeException("Ce utilisateur est introuvable");
		    }
		    
		Statut resultat = statutRepository.save(Statut) ;
	   return ResponseEntity.ok().body(resultat);
	}

	@DeleteMapping("/supprimer/{id}")
	public ResponseEntity<?> SupprimerParId(@PathVariable int id) {
		statutRepository.deleteById(id);
	       return ResponseEntity.ok().build();
	}
	@DeleteMapping("/supprimer")
	public ResponseEntity<?> SupprimerTout() {
		statutRepository.deleteAll();
	       return ResponseEntity.ok().build();
	}
	

}
