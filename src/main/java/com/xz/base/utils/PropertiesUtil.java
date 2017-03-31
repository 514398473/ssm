/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

	/**
	 * 通过路径和key 读取值
	 * 
	 * @param fileNamePath 文件路径
	 * @param key properties的key
	 * @return
	 * @throws IOException
	 */
	public static String getValue(String fileNamePath, String key) throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(fileNamePath);
			// 如果将in改为下面的方法，必须要将.Properties文件和此class类文件放在同一个包中
			// in = propertiesTools.class.getResourceAsStream(fileNamePath);
			props.load(in);
			String value = props.getProperty(key);
			// 有乱码时要进行重新编码
			// new String(props.getProperty("name").getBytes("ISO-8859-1"),
			// "GBK");
			return value;

		} catch (IOException e) {
			e.printStackTrace();
			return null;

		} finally {
			if (null != in)
				in.close();
		}
	}
}
