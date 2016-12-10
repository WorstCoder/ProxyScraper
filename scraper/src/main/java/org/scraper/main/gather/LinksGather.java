package org.scraper.main.gather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.scraper.main.scraper.ScrapeType;
import org.scraper.main.web.BrowserVersion;
import org.scraper.main.web.LanguageCheck;
import org.scraper.main.web.Site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinksGather extends Observable {
	
	private Integer depth;
	
	public LinksGather(Integer depth) {
		this.depth = depth;
	}
	
	public LinksGather() {
		this.depth = 2;
	}
	
	public List<Site> gather(Site site) {
		List<String> start = startGather(site);
		List<Site> gathered = start
				.stream()
				.map(link -> new Site(link, ScrapeType.UNCHECKED))
				.collect(Collectors.toList());
		
		setChanged();
		notifyObservers(gathered);
		return gathered;
	}
	
	private List<String> startGather(Site site) {
		List<String> all = new ArrayList<>();
		List<String> newLinks = new ArrayList<>();
		List<String> tempLinks = new ArrayList<>();
		
		all.add(site.getAddress());
		newLinks.add(site.getAddress());
		
		IntStream.range(0, depth)
				.forEach(i -> {
					newLinks.forEach(link -> {
						List<String> got = gatherLinks(new Site(link, null), all);
						all.addAll(got);
						tempLinks.addAll(got);
					});
					newLinks.clear();
					newLinks.addAll(tempLinks.stream()
											.distinct()
											.collect(Collectors.toList()));
					tempLinks.clear();
				});
		return all;
	}
	
	private List<String> gatherLinks(Site site, List<String> all) {
		String address = site.getAddress();
		String root = site.getRoot();
		
		Document document = connect(address);
		
		if (document == null)
			return new ArrayList<>();
		
		Elements redirects = document.getElementsByAttribute("href");
		
		return redirects
				.stream()
				.map(redirect ->
							 redirect.attr("href"))
				.filter(link ->
								link != null && link.length() > 1)
				.map(link ->
							 link.charAt(0) == '/' ? root + link : link)
				.map(link ->
							 link.contains("#") ? link.split("#")[0] : link)
				.filter(link ->
								link.length() > 4
								&& !LanguageCheck.isFromOtherLang(link, all)
								&& link.contains(root)
								&& !all.contains(link)
								&& link.substring(0, 4).equals("http")
								&& !link.matches(".*\\.[a-z]{3}\\?.*"))
				.distinct()
				.collect(Collectors.toList());
	}
	
	private Document connect(String url) {
		Document document = null;
		try {
			document = Jsoup
					.connect(url)
					.userAgent(BrowserVersion.random().ua())
					.timeout(5000)
					.get();
			
		} catch (IOException ignored) {}
		return document;
	}
	
	public List<Site> gatherList(List<Site> sites) {
		List<List<Site>> proxyList = new ArrayList<>();
		
		sites.forEach(site ->
							proxyList.add(gather(site)));
		
		List<Site> proxy = new ArrayList<>();
		
		proxyList.forEach(proxy::addAll);
		return proxy;
	}
	
	public Integer getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
}