package com.xz.crawler.utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * html工具类 主要作用：标签转义、提取摘要、删除不安全的tag（比如<script></script>）
 */
public class HtmlUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

	private static PoolingHttpClientConnectionManager cm = null;

	static {
		LayeredConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("创建SSL连接失败");
		}
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
	}

	private static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		return httpClient;
	}

	/**
	 * 多线程获取html
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = HtmlUtil.getHttpClient();
		CloseableHttpResponse httpResponse = null;
		// 发送get请求
		try {
			// 用get方法发送http请求
			HttpGet get = new HttpGet(url);
			// LOGGER.info("执行get请求, uri: " + get.getURI());
			// RequestConfig requestConfig =
			// RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(3000).setSocketTimeout(3000).build();
			// get.setConfig(requestConfig);
			httpResponse = httpClient.execute(get);
			// response实体
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				String response = EntityUtils.toString(entity, "utf-8");
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				// LOGGER.info("响应状态码:" +statusCode);
				// LOGGER.info("响应内容:" + response);
				if (statusCode == HttpStatus.SC_OK) {
					// 成功
					return response;
				} else {
					return null;
				}
			}
			return null;
		} catch (IOException e) {
			LOGGER.error("httpclient请求失败", e);
			return null;
		} finally {
			if (httpResponse != null) {
				try {
					EntityUtils.consume(httpResponse.getEntity());
					httpResponse.close();
				} catch (IOException e) {
					LOGGER.error("关闭response失败", e);
				}
			}
		}
	}

	/**
	 * 单线程获取html
	 * 
	 * @param url
	 * @return
	 */

	public static String getHtml(String url) {

		if (StringUtils.isBlank(url)) {
			return null;
		}
		// 创建httpclient对象 //
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 根据请求方法创建请求方法的示例，以请求的服务器url为参数，如get()方法和post()方法：
		HttpGet httpGet = new HttpGet(url);
		// 使用代理 依次是代理地址，代理端口号，协议类型 //
		// HttpHost proxy = new HttpHost("221.180.170.108", 80, "http");
		// 设置超时
		// RequestConfig requestConfig =
		// RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(1000).setSocketTimeout(10000).setProxy(proxy).build();
		// httpGet.setConfig(requestConfig);
		// 伪装成浏览器
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
		httpGet.addHeader("User-Agent", userAgent);
		try { // 获取响应
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		return null;
	}

	/**
	 * 获取html文档中的文本
	 * 
	 * @return
	 */
	public static String text(String html) {
		if (StringUtils.isEmpty(html)) {
			return html;
		}
		return Jsoup.parse(html.replace("<", "<").replace(">", ">")).text();
	}

	/**
	 * 获取html文档中的文本 并仅提取文本中的前maxLength个 超出部分使用……补充
	 * 
	 * @param html
	 * @param maxLength
	 * @return
	 */
	public static String text(String html, int maxLength) {
		String text = text(html);
		if (text.length() <= maxLength) {
			return text;
		}
		return text.substring(0, maxLength) + "……";
	}

	/**
	 * 删除指定标签
	 * 
	 * @param html
	 * @param tagName
	 * @return
	 */
	public static String removeTag(String html, String tagName) {
		Element bodyElement = Jsoup.parse(html).body();
		bodyElement.getElementsByTag(tagName).remove();
		return bodyElement.html();
	}

}
