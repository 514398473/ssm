/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.test.test;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

/**
 * 用solrj测试solr
 * <p>
 * 用solrj测试solr
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年2月27日
 */
public class SolrCloudTest {

	// zookeeper地址
	private static String zkHostString = "192.168.38.3:2181,192.168.38.4:2181,192.168.38.5:2181";
	// collection默认名称，比如我的solr服务器上的collection是collection2_shard1_replica1，就是去掉“_shard1_replica1”的名称
	private static String defaultCollection = "collection2";
	// 客户端连接超时时间
	private static int zkClientTimeout = 3000;
	// zookeeper连接超时时间
	private static int zkConnectTimeout = 3000;

	// cloudSolrServer实际
	private CloudSolrServer cloudSolrServer;

	// 测试方法之前构造 CloudSolrServer
	@Before
	public void init() {
		cloudSolrServer = new CloudSolrServer(zkHostString);
		cloudSolrServer.setDefaultCollection(defaultCollection);
		cloudSolrServer.setZkClientTimeout(zkClientTimeout);
		cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);
		cloudSolrServer.connect();
	}

	// 向solrCloud上创建索引
	@Test
	public void testCreateIndexToSolrCloud() throws SolrServerException, IOException {

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "100001");
		document.addField("title", "李四");
		cloudSolrServer.add(document);
		cloudSolrServer.commit();

	}

	// 搜索索引
	@Test
	public void testSearchIndexFromSolrCloud() throws Exception {

		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		try {
			QueryResponse response = cloudSolrServer.query(query);
			SolrDocumentList docs = response.getResults();

			System.out.println("文档个数：" + docs.getNumFound());
			System.out.println("查询时间：" + response.getQTime());

			for (SolrDocument doc : docs) {
				ArrayList title = (ArrayList) doc.getFieldValue("title");
				String id = (String) doc.getFieldValue("id");
				System.out.println("id: " + id);
				System.out.println("title: " + title);
				System.out.println();
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unknowned Exception!!!!");
			e.printStackTrace();
		}
	}

	// 删除索引
	@Test
	public void testDeleteIndexFromSolrCloud() throws SolrServerException, IOException {

		// 根据id删除
		UpdateResponse response = cloudSolrServer.deleteById("zhangsan");
		// 根据多个id删除
		// cloudSolrServer.deleteById(ids);
		// 自动查询条件删除
		// cloudSolrServer.deleteByQuery("product_keywords:教程");
		// 提交
		cloudSolrServer.commit();
	}

}
