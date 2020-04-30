package com.Gestion_Serveurs.exportExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import com.Gestion_Serveurs.entities.Server;

public class ServerFichierExcelExporter {

	  public static ByteArrayInputStream listServerAExporter(List<Server> server) {
		   
		    try {
				
		    	//creer un document de travail en excel
		    	HSSFWorkbook workbook = new HSSFWorkbook();
		    	
		    	//Creer une feuille dans le classeur
		    	HSSFSheet sheet = workbook.createSheet("server");
		    	
		    	//Creer des lignes dans la feuille
		    	Row row = sheet.createRow(0);
		    	
		    	//Donner un style au entetes des celules de la feuille
		    	CellStyle headerCellStyle = workbook.createCellStyle(); 
		    	
		    	
		    	headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		    	
		    	

//		    	headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    	headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    	
		    	
		    	// Creation des cellules
		    	
		    	Cell cell = row.createCell(0);
		    	cell.setCellValue("libelle");
		    	cell.setCellStyle(headerCellStyle);
		    	
		    	cell = row.createCell(1);
		    	cell.setCellValue("host");
		    	cell.setCellStyle(headerCellStyle);
		    	
		    	cell = row.createCell(2);
		    	cell.setCellValue("login");
		    	cell.setCellStyle(headerCellStyle);
		    	
		    	cell = row.createCell(3);
		    	cell.setCellValue("password");
		    	cell.setCellStyle(headerCellStyle);
		    	
		    	cell = row.createCell(4);
		    	cell.setCellValue("email");
		    	cell.setCellStyle(headerCellStyle);
		    	
		     	cell = row.createCell(5);
		    	cell.setCellValue("numero");
		    	cell.setCellStyle(headerCellStyle);
		    	
		    	
		   //Ajouter des donnees a chaque ligne de notre feuille	
		    	
		    	for (int i = 0; i < server.size(); i++) {
		    		
		    		Row ajouterRow = sheet.createRow(i + 1);
		   
		  // Recuperer les donnees de l'objet et les setter dans la feuille
		    		
		    		ajouterRow.createCell(0).setCellValue(server.get(i).getLibelle());
		    		ajouterRow.createCell(1).setCellValue(server.get(i).getHost());
		    		ajouterRow.createCell(2).setCellValue(server.get(i).getLogin());
		    		ajouterRow.createCell(3).setCellValue(server.get(i).getPassword());
		    		ajouterRow.createCell(4).setCellValue(server.get(i).getEmail());
		    		ajouterRow.createCell(5).setCellValue(server.get(i).getNumero());
				}
		    	
		    	
		  //Verifier si les champs sont automatiquement adaptés au données
		    	
		    	sheet.autoSizeColumn(0);
		    	sheet.autoSizeColumn(1);
		    	sheet.autoSizeColumn(2);
		    	sheet.autoSizeColumn(3);
		    	sheet.autoSizeColumn(4);
		    	sheet.autoSizeColumn(5);
		    	
		  // Generation du fichier
		    	
		    	ByteArrayOutputStream fichierGenere = new ByteArrayOutputStream();
		    	workbook.write(fichierGenere);
		    	
		    	return new ByteArrayInputStream(fichierGenere.toByteArray());
		    	
			} catch (Exception e) {
				return null;
			}
	   }
}
