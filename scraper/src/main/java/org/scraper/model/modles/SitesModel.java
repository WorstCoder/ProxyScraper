package org.scraper.model.modles;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.scraper.model.IPool;
import org.scraper.model.MainPool;
import org.scraper.model.managers.AssignManager;
import org.scraper.model.gather.LinksGather;
import org.scraper.model.scrapers.ProxyScraper;
import org.scraper.model.web.IDataBase;
import org.scraper.model.web.Site;

import java.util.ArrayList;
import java.util.List;

public class SitesModel {
	
	private ProxyScraper scraper;
	
	private AssignManager assigner;
	
	private LinksGather gather;
	
	private IDataBase IDataBase;
	
	private ObservableList<Site> visible = FXCollections.observableArrayList();
	
	public SitesModel(AssignManager assigner, ProxyScraper scraper, LinksGather gather, IDataBase IDataBase) {
		this.assigner = assigner;
		this.scraper = scraper;
		this.gather = gather;
		this.IDataBase = IDataBase;
	}
	
	public void addSite(Site site){
		Platform.runLater(() -> visible.add(site));
	}
	
	public ObservableList<Site> getVisible(){
		return visible;
	}
	
	public void scrape(List<Site> sites) {
		scraper.scrapeList(sites);
	}
	
	public void check(List<Site> sites) {
		MainPool.getInstance().sendTask(() ->{
		assigner.assignList(sites);
		IDataBase.postSites(new ArrayList<>(sites));
		}, false);
	}
	
	public void gather(List<Site> sites, Integer depth){
		gather.setDepth(depth);
		gather.gatherList(sites);
	}
}
