/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取properties文件工具类
 * <p>
 * 读取properties文件工具类
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年3月31日
 */

public class PropertiesUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

	/**
	 * 通过配置文件名称和key 读取值
	 * 
	 * @param fileName 文件名称
	 * @param key properties的key
	 * @return
	 * @throws IOException
	 */
	public static String getValue(String fileName, String key) throws IOException {
		// 以URL形式获取工程的资源文件 classpath 路径, 得到以file:/为开头的URL
		// 例如返回: file:/D:/workspace/myproject01/WEB-INF/classes/
		URL classPath = Thread.currentThread().getContextClassLoader().getResource("");
		String proFilePath = classPath.toString();

		// 移除开通的file:/六个字符
		proFilePath = proFilePath.substring(6);

		// 如果为window系统下,则把路径中的路径分隔符替换为window系统的文件路径分隔符
		proFilePath = proFilePath.replace("/", java.io.File.separator);

		// 兼容处理最后一个字符是否为 window系统的文件路径分隔符,同时建立 properties 文件路径
		// 例如返回: D:\workspace\myproject01\WEB-INF\classes\config.properties
		if (!proFilePath.endsWith(java.io.File.separator)) {
			proFilePath = proFilePath + java.io.File.separator + fileName;
		} else {
			proFilePath = proFilePath + fileName;
		}
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(proFilePath);
			// 如果将in改为下面的方法，必须要将.Properties文件和此class类文件放在同一个包中
			// in = propertiesTools.class.getResourceAsStream(fileNamePath);
			props.load(in);
			String value = props.getProperty(key);
			// 有乱码时要进行重新编码
			// new String(props.getProperty("name").getBytes("ISO-8859-1"),
			// "GBK");
			return value;

		} catch (IOException e) {
			LOGGER.error("配置文件:" + fileName + "的" + key + "值获取失败!");
			return null;

		} finally {
			if (null != in)
				in.close();
		}
	}
}
