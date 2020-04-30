package com.Gestion_Serveurs.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
@JsonInclude(Include.NON_NULL)
@Data
public class NewServer {

	 private int id;
	 private String libelle;
	 private String login;
	 private String password;
	 private String ip;
	
}
