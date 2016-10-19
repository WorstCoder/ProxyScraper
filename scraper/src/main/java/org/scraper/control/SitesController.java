package org.scraper.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.scraper.model.modles.MainModel;

public class SitesController {
	@FXML
	private Button scrapeButton;
	
	@FXML
	private Button checkButton;
	
	@FXML
	private TextField addField;
	
	@FXML
	private Button addSite;
	
	@FXML
	private Button gatherButton;
	
	@FXML
	private TextField depthField;
	
	private MainModel model;
	
	@FXML
	public void initialize(MainModel model) {
		
		this.model = model;
		
		scrapeButton.setOnAction(event -> model.scrape());
		
		checkButton.setOnAction(event -> model.check());
		
		gatherButton.setOnAction(event -> model.crawl());
		
		checkButton.setOnAction(event -> model.setCheckOnFly(checkButton.isArmed()));
	}
	
}