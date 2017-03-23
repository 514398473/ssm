package com.xz.crawler.service.impl;

import org.springframework.stereotype.Service;

import com.xz.base.service.impl.BaseServiceImpl;
import com.xz.crawler.model.Html;
import com.xz.crawler.model.HtmlExample;
import com.xz.crawler.service.CrawlerService;

@Service
public class CrawlerServiceImpl extends BaseServiceImpl<Html, HtmlExample, Integer> implements CrawlerService {

}
