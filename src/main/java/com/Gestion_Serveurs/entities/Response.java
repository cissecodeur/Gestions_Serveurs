package com.Gestion_Serveurs.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Data @AllArgsConstructor @NoArgsConstructor
public class Response {
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;
	private List<Server> datasServer;
	

	private String info1;
	private String info2;
	private String info3;

	


}












