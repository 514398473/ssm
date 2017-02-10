/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.upload.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用webupload上传文件
 * <p>
 * 用webupload上传文件
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年2月9日
 */

@Controller
@RequestMapping("/upload")
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
	private static final String PATH = "F:\\download\\";

	/**
	 * 跳转普通文件上传页面
	 * 
	 * @param request
	 */
	@RequestMapping("/commonUpload")
	public String commonUpload() {
		return "upload/common_upload";
	}

	/**
	 * 跳转webUploader文件上传页面
	 * 
	 * @param request
	 */
	@RequestMapping("/webUploaderUpload")
	public String webUploaderUpload() {
		return "upload/webUploader_upload";
	}

	/**
	 * 普通文件上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request) {
		// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
		factory.setSizeThreshold(1024 * 100);// 设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 按照传统方式获取数据
			return null;
		}
		// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					LOGGER.info(name + "=" + value);
				} else {// 如果fileitem中封装的是上传文件
					// 得到上传的文件名称，
					String filename = item.getName();
					LOGGER.info(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					// 得到上传文件的扩展名
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
					LOGGER.info("上传的文件的扩展名是：" + fileExtName);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 保存文件
					FileUtils.copyInputStreamToFile(in, new File(PATH + makeFileName(filename)));
					LOGGER.info("上传成功");
				}
			}
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分块文件上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/chunkUpload")
	public String chunkUpload(HttpServletRequest request, HttpServletResponse response, String fileMd5, String chunk, String chunkSize) {
		// 1.创建DiskFileItemFactory对象,配置缓存信息
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2.创建ServletFileUpload对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 3.设置文件名称的编码
		sfu.setHeaderEncoding("utf-8");
		// 4.开始解析文件
		try {
			List<FileItem> items = sfu.parseRequest(request);
			// 5.获取文件信息
			for (FileItem item : items) {
				// 6.判断是文件还是普通数据
				if (item.isFormField()) {
					// 普通数据
					String fieldName = item.getFieldName();
					if (fieldName.equals("info")) {
						// 获取文件信息
						String info = item.getString("utf-8");
						LOGGER.info(info);
					}
					if (fieldName.equals("fileMd5")) {
						// 获取文件信息
						fileMd5 = item.getString("utf-8");
						LOGGER.info(fileMd5);
					}
					if (fieldName.equals("chunk")) {
						// 获取文件信息
						chunk = item.getString("utf-8");
						LOGGER.info(chunk);
					}
					if (fieldName.equals("chunkSize")) {
						// 获取文件信息
						chunkSize = item.getString("utf-8");
						LOGGER.info(chunkSize);
					}
				} else {
					// 保存分块文件
					// 1.创建一个唯一目录，保存分块文件
					File file = new File(PATH + fileMd5);
					if (!file.exists()) {
						// 创建目录
						file.mkdir();
					}

					// 2.保存文件
					File chunkFile = new File(PATH + fileMd5 + "/" + chunk);
					response.setContentType("text/html;charset=utf-8");
					// 检查文件是否存在，且大小是否一致
					if (chunkFile.exists() && chunkFile.length() == Integer.parseInt(chunkSize)) {
						// 上传过了
						return null;
					}
					FileUtils.copyInputStreamToFile(item.getInputStream(), chunkFile);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查分块文件
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/checkChunk")
	public String checkChunk(HttpServletRequest request, HttpServletResponse response, String action, String fileMd5, String chunk, String chunkSize, String ext)
			throws IOException {
		if (action.equals("mergeChunks")) {
			// 合并文件

			// 读取目录里面的所有文件
			File f = new File(PATH + fileMd5);
			File[] fileArray = f.listFiles(new FileFilter() {

				// 排除目录，只要文件
				public boolean accept(File pathname) {
					if (pathname.isDirectory()) {
						return false;
					}
					return true;
				}
			});

			// 转成集合，便于排序
			List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));

			// 从小到大排序
			Collections.sort(fileList, new Comparator<File>() {
				public int compare(File o1, File o2) {
					if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
						return -1;
					}
					return 1;
				}
			});

			File outputFile = new File(PATH + UUID.randomUUID().toString() + "." + ext);
			// 创建文件
			outputFile.createNewFile();
			// 输出流
			FileChannel outChannel = new FileOutputStream(outputFile).getChannel();

			// 合并
			FileChannel inChannel;
			for (File file : fileList) {
				inChannel = new FileInputStream(file).getChannel();
				inChannel.transferTo(0, inChannel.size(), outChannel);
				inChannel.close();

				// 删除分片
				file.delete();
			}
			// 清除文件夹
			File tempFile = new File(PATH + fileMd5);
			if (tempFile.isDirectory() && tempFile.exists()) {
				tempFile.delete();
			}

			LOGGER.info("合并成功");
		} else if (action.equals("checkChunk")) {
			// 检查当前分块是否上传成功

			// 找到分块文件
			File checkFile = new File(PATH + fileMd5 + "/" + chunk);
			response.setContentType("text/html;charset=utf-8");
			// 检查文件是否存在，且大小是否一致
			if (checkFile.exists() && checkFile.length() == Integer.parseInt(chunkSize)) {
				// 上传过了
				response.getWriter().write("{\"ifExist\":1}");
			} else {
				// 没有上传过
				response.getWriter().write("{\"ifExist\":0}");
			}
		}
		return null;
	}

	private String makeFileName(String filename) {
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}

}
