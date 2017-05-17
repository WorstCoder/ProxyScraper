package scraper.view.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scraper.view.control.IClipboard;
import scraper.view.control.IDeleter;
import scraper.Pool;
import scraper.SitesUtility;
import scraper.data.Site;
import scraper.gather.LinksGather;
import scraper.manager.AssignManager;
import scraper.scraper.ProxyScraper;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class SitesModelFX extends SitesUtility implements IDeleter<Site>, IClipboard<Site> {
	
	private ObservableList<Site> visible = FXCollections.observableArrayList();
	
	public SitesModelFX(AssignManager assigner, ProxyScraper scraper, LinksGather gather, Pool pool) {
		super(assigner, scraper, gather, pool);
	}
	
	public void addSite(Site site) {
		super.addSite(site);
		Platform.runLater(() -> visible.add(site));
	}
	
	public Collection<Site> filterAvgProxies(Integer minSites) {
		Collection<Site> filtered = super.filterAvgProxies(minSites);
		visible.clear();
		visible.addAll(filtered);
		return filtered;
	}
	
	public Collection<Site> filterAvgWorking(Integer minWorking) {
		Collection<Site> filtered = super.filterAvgWorking(minWorking);
		visible.clear();
		visible.addAll(filtered);
		return filtered;
	}
	
	@Override
	public void deleteSelected(List<Site> selected) {
		for (Site site : new CopyOnWriteArrayList<>(selected)) {
			visible.remove(site);
		}
	}
	
	
	@Override
	public void handleCopy(List<Site> proxyList) {
		String selectedProxiesString = proxyList.stream()
				.map(Site::getAddress)
				.collect(Collectors.joining("\n"));
		copy(selectedProxiesString);
	}
	
	@Override
	public void handlePaste() {
		List<String> clipboardContent = paste();
		clipboardContent.forEach(this::addSite);
	}
	
	public ObservableList<Site> getVisible() {
		return visible;
	}
}