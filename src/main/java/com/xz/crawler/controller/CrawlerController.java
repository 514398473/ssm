package com.xz.crawler.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xz.crawler.model.Html;
import com.xz.crawler.model.Urlmd5;
import com.xz.crawler.model.Urlmd5Example;
import com.xz.crawler.service.CrawlerService;
import com.xz.crawler.service.Urlmd5Service;
import com.xz.crawler.utils.HtmlUtil;
import com.xz.crawler.utils.MD5Util;

public class CrawlerController {

	// @Autowired
	// private CrawlerService crawlerService;

	private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	private BeanFactory bf = (BeanFactory) ac;

	public void crawler(String url) {
		
		if (StringUtils.isBlank(url)) {
			return;
		} else {
			String str = url.substring(url.length() - 1, url.length());
			if ("//".equals(str)) {
				url = url.substring(0, url.length() - 1);
			}
			String md5 = MD5Util.MD5(url);
			Urlmd5Service urlmd5Service = (Urlmd5Service) bf.getBean("urlmd5Service");
			Urlmd5Example urlmd5Example = new Urlmd5Example();
			urlmd5Example.createCriteria().andMd5EqualTo(md5);
			List<Urlmd5> urlmd5List = urlmd5Service.selectByExample(urlmd5Example);
			if (CollectionUtils.isNotEmpty(urlmd5List)) {
				return;
			}
			String html = HtmlUtil.get(url);
			if (StringUtils.isNotBlank(html)) {
				Document document = Jsoup.parse(html);
				Element element = document.getElementsByTag("title").first();
				if (null != element && StringUtils.isNotBlank(element.toString())) {
					String title = HtmlUtil.text(element.toString());
					String content = HtmlUtil.text(html);
					Html h = new Html();
					h.setContent(content);
					h.setTitle(title);
					h.setUrl(url);
					CrawlerService crawlerService = (CrawlerService) bf.getBean("crawlerService");
					try {
						crawlerService.insert(h);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Urlmd5 urlmd5 = new Urlmd5();
				urlmd5.setMd5(MD5Util.MD5(url));
				urlmd5Service.insert(urlmd5);
				System.out.println("地址+" + url + "已完成");
				Elements links = document.select("a[href]");
				for (Element link : links) {
					String newURL = link.attr("abs:href");
					if (StringUtils.isNotBlank(newURL)) {
						try {
							crawler(newURL);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		CrawlerController crawlerController = new CrawlerController();
		crawlerController.crawler("http://www.glodon.com/");
	}

}
