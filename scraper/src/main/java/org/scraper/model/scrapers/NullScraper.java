package org.scraper.model.scrapers;

import org.scraper.model.MainLogger;
import org.scraper.model.Proxy;
import org.scraper.model.web.Site;

import java.util.ArrayList;
import java.util.List;

class NullScraper extends Scraper {
	
	NullScraper(){
		type = ScrapeType.BLACK;
	}
	
	@Override
	public List<Proxy> scrape(Site site) {
		MainLogger.log().warn("URL {} doesn't contain any proxies", site.getAddress());
		return new ArrayList<>();
	}
	
}
