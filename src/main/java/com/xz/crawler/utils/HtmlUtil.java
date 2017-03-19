package com.xz.crawler.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * html工具类 主要作用：标签转义、提取摘要、删除不安全的tag（比如<script></script>）
 */
public class HtmlUtil {

	/**
	 * 获取html
	 * 
	 * @param url
	 * @return
	 */
	public static String getHtml(String url) {

		if (StringUtils.isBlank(url)) {
			return null;
		}
		// 创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 根据请求方法创建请求方法的示例，以请求的服务器url为参数，如get()方法和post()方法：
		HttpGet httpGet = new HttpGet(url);

		// 伪装成浏览器
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
		httpGet.addHeader("User-Agent", userAgent);

		try {
			// 获取响应
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
