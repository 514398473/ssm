/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.solr.controller;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xz.crawler.model.Html;
import com.xz.crawler.model.HtmlExample;
import com.xz.crawler.service.CrawlerService;
import com.xz.solr.model.Doc;
import com.xz.solr.utils.PageUtil;

/**
 * solr搜索Controller
 * <p>
 * solr搜索Controller
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年2月28日
 */
@Controller
@RequestMapping("/solr")
public class SolrController {

	// 单机版solr
	@Autowired
	private HttpSolrServer httpSolrServer;

	// 集群版solr
	@Autowired
	private CloudSolrServer cloudSolrServer;

	@Autowired
	private CrawlerService crawlerService;

	private final String DATA_DIR = "F:\\www.bjsxt.com";

	@RequestMapping("/goPage/{pageName}")
	public String goPage(@PathVariable String pageName) throws Exception {
		return "solr/" + pageName;
	}

	/**
	 * 搜索
	 * 
	 * @param keywords
	 * @param num
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public String search(String keywords, Integer num, Model model) throws Exception {
		// 是否需要高亮
		boolean isHighlighting = !StringUtils.equals("*", keywords) && StringUtils.isNotEmpty(keywords);

		SolrQuery query = new SolrQuery();
		query.setQuery("title:" + keywords + " OR content:" + keywords);
		query.setHighlight(true); // 开启高亮组件或query.setParam("hl", "true");
		query.addHighlightField("title");// 高亮字段
		query.addHighlightField("content");// 高亮字段
		query.setHighlightSimplePre("<font color='red'>");// 标记，高亮关键字前缀
		query.setHighlightSimplePost("</font>");// 后缀
		query.setHighlightSnippets(10);// 结果分片数，默认为1
		query.setHighlightFragsize(30);// 每个分片的最大长度，默认为100
		query.setStart((Math.max(num, 1) - 1) * 10);
		query.setRows(10);
		QueryResponse rsp = httpSolrServer.query(query);
		List<Doc> docs = rsp.getBeans(Doc.class);
		long numFound = rsp.getResults().getNumFound();
		if (isHighlighting) {
			// 将高亮的标题数据写回到数据对象中
			Map<String, Map<String, List<String>>> map = rsp.getHighlighting();
			for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
				for (Doc doc : docs) {
					if (!highlighting.getKey().equals(doc.getId().toString())) {
						continue;
					}
					if (highlighting.getValue().get("title") != null) {
						doc.setTitle(StringUtils.join(highlighting.getValue().get("title"), ""));
					}
					if (highlighting.getValue().get("content") != null) {
						doc.setContent(StringUtils.join(highlighting.getValue().get("content"), ""));
					}
					break;
				}
			}
		}

		PageUtil<Doc> page = new PageUtil<Doc>(num + "", 10 + "", Integer.parseInt(String.valueOf(numFound)));
		page.setList(docs);
		model.addAttribute("page", page);
		model.addAttribute("keywords", keywords);
		return "solr/search";
	}

	/**
	 * 创建索引
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createIndex")
	public String createIndex() throws Exception {

		httpSolrServer.setMaxRetries(1);
		httpSolrServer.setConnectionTimeout(1 * 60 * 1000);
		httpSolrServer.setParser(new XMLResponseParser()); // binary parser is
															// used by default
		httpSolrServer.setSoTimeout(1 * 60 * 1000); // socket read timeout
		httpSolrServer.setDefaultMaxConnectionsPerHost(100);
		httpSolrServer.setMaxTotalConnections(100);
		httpSolrServer.setFollowRedirects(false); // defaults to false
		httpSolrServer.setAllowCompression(true);
		// 删除所有索引
		httpSolrServer.deleteByQuery("*:*");
		httpSolrServer.commit();
		// 通过本地文件目录添加索引
		Collection<File> files = FileUtils.listFiles(new File(DATA_DIR), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		int i = 0;
		int j = 0;
		for (File file : files) {
			Document document = Jsoup.parse(file, "UTF-8");
			Element element = document.getElementsByTag("title").first();
			String title = null;
			if (null != element) {
				title = element.text();
			}
			String content = document.text();
			Doc doc = new Doc();
			doc.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			if (StringUtils.isNotBlank(content)) {
				doc.setContent(content);
			}
			if (null != title) {
				doc.setTitle(title);
			}
			String path = file.getAbsolutePath();
			doc.setUrl("http://" + path.substring(3));
			httpSolrServer.addBean(doc);
			i++;
			j++;
			if (i == 50) {
				httpSolrServer.commit();
				i = 0;
			}
			if (j == files.size()) {
				httpSolrServer.commit();
			}
		}
		// 通过从数据库查询数据 添加索引
		HtmlExample example = new HtmlExample();
		i = 0;
		j = 0;
		List<Html> selectByExampleWithBLOBs = crawlerService.selectByExampleWithBLOBs(example);
		for (Html html : selectByExampleWithBLOBs) {
			Doc doc = new Doc();
			doc.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			doc.setContent(html.getContent());
			doc.setTitle(html.getContent());
			doc.setUrl(html.getUrl());
			i++;
			j++;
			httpSolrServer.addBean(doc);
			if (i == 50) {
				httpSolrServer.commit();
				i = 0;
			}
			if (j == selectByExampleWithBLOBs.size()) {
				httpSolrServer.commit();
			}
		}
		return "solr/search";
	}
}
