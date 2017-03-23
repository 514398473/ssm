package com.xz.crawler.test;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xz.base.test.BaseTest;
import com.xz.crawler.model.Html;
import com.xz.crawler.model.Urlmd5;
import com.xz.crawler.model.Urlmd5Example;
import com.xz.crawler.service.CrawlerService;
import com.xz.crawler.service.Urlmd5Service;
import com.xz.crawler.utils.HtmlUtil;
import com.xz.crawler.utils.MD5Util;

public class CrawlerTest extends BaseTest {

	@Autowired
	private CrawlerService crawlerService;

	@Autowired
	private Urlmd5Service urlmd5Service;

	public void crawler(String url) {

		if (StringUtils.isBlank(url)) {
			return;
		} else {
			String str = url.substring(url.length() - 1, url.length());
			if ("/".equals(str)) {
				url = url.substring(0, url.length() - 1);
			}
			String md5 = MD5Util.MD5(url);
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
					try {
						crawlerService.insert(h);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Urlmd5 urlmd5 = new Urlmd5();
				urlmd5.setMd5(MD5Util.MD5(url));
				urlmd5Service.insert(urlmd5);
				logger.info("地址+" + url + "已完成");
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

	@Test
	public void testCrawler() throws Exception {
		try {
			this.crawler("http://www.glodon.com/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
