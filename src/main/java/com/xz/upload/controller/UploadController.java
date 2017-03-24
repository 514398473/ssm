/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.upload.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.xz.base.controller.BaseController;
import com.xz.upload.model.Path;
import com.xz.upload.model.PathExample;
import com.xz.upload.service.UploadService;

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
public class UploadController extends BaseController {

	@Autowired
	private UploadService uploadService;

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
	public void upload(HttpServletRequest request) {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						logger.info("文件名称:" + myFileName);
						String path = PATH + makeFileName(myFileName);
						// 保存文件
						File localFile = new File(path);
						try {
							file.transferTo(localFile);
							logger.info("上传成功");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 分块文件上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/chunkUpload")
	public void chunkUpload(HttpServletRequest request, HttpServletResponse response, String fileMd5, String chunk, String chunkSize) {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 保存分块文件
					// 1.创建一个唯一目录，保存分块文件
					File newfile = new File(PATH + fileMd5);
					if (!newfile.exists()) {
						// 创建目录
						newfile.mkdir();
					}
					// 2.保存文件
					File chunkFile = new File(PATH + fileMd5 + "/" + chunk);
					response.setContentType("text/html;charset=utf-8");
					// 检查文件是否存在，且大小是否一致
					if (chunkFile.exists() && chunkFile.length() == Integer.parseInt(chunkSize)) {
						// 上传过了
						return;
					}
					try {
						FileUtils.copyInputStreamToFile(file.getInputStream(), chunkFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 检查分块文件
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/checkChunk")
	public void checkChunk(HttpServletRequest request, HttpServletResponse response, String action, String fileMd5, String chunk, String chunkSize, String ext)
			throws IOException {
		// 如果文件上传过 直接提示上传成功 实现文件秒传
		PathExample example = new PathExample();
		example.createCriteria().andMd5EqualTo(fileMd5);
		List<Path> pathList = uploadService.selectByExample(example);
		Path p = null;
		if (CollectionUtils.isNotEmpty(pathList)) {
			p = pathList.get(0);
		}
		if (null != p && fileMd5.equals(p.getMd5())) {
			response.getWriter().write("{\"ifExist\":1}");
		}
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
			String filePath = PATH + UUID.randomUUID().toString() + "." + ext;
			File outputFile = new File(filePath);
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
			Path path = new Path();
			path.setMd5(fileMd5);
			path.setPath(filePath);
			uploadService.insert(path);
			logger.info("合并成功");
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
	}

	private String makeFileName(String filename) {
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString().replace("-", "").toUpperCase() + "_" + filename;
	}

}
