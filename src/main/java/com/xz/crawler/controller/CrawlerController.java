package com.xz.crawler.controller;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xz.crawler.utils.HtmlUtil;

@Controller
@RequestMapping("/crawler")
public class CrawlerController {

	@RequestMapping("/index")
	public String index(String url) throws Exception {
		String html = HtmlUtil.getHtml(url);
		if (StringUtils.isNotBlank(html)) {
			Document document = Jsoup.parse(html);
			Elements elements = document.getElementsByTag("title");
		}
		return "";
	}
	
}
