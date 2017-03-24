/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.qrcode.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.druid.util.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xz.base.controller.BaseController;
import com.xz.base.utils.Result;
import com.xz.qrcode.utils.QRCodeUtil;

/**
 * 二维码controller
 * <p>
 * 二维码controller
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年3月24日
 */

@Controller
@RequestMapping("/QRCode")
public class QRCodeController extends BaseController {

	/**
	 * 跳转页面
	 * 
	 * @param pageName
	 * @return
	 */
	@RequestMapping("/goPage/{pageName}")
	public String goPage(@PathVariable String pageName) {
		return "qrcode/" + pageName;
	}

	/**
	 * 生成带logo和文字描述的二维码
	 * 
	 * @param request
	 * @param description
	 * @param url
	 * @return
	 */
	@RequestMapping("/createLogoQRCode")
	@ResponseBody
	public Result createLogoQRCode(HttpServletRequest request, String description, String url) {
		String logoQRCode = null;
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
						String path = request.getSession().getServletContext().getRealPath("/") + "upload/" + makeFileName(myFileName);
						// 保存文件
						File localFile = new File(path);
						try {
							file.transferTo(localFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// 生成二维码
						logoQRCode = QRCodeUtil.getLogoQRCode(url, path, description);
						logger.info("生成成功");
					}
				}
			}
		}
		return Result.createSuccess(logoQRCode);
	}

	/**
	 * 生成普通二维码
	 * 
	 * @param request
	 * @param description
	 * @param url
	 * @return
	 */
	@RequestMapping("/createCommonQRCode")
	@ResponseBody
	public Result createCommonQRCode(HttpServletRequest request, String content) {
		String logoQRCode = null;
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			// 内容所使用编码
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
			// 生成二维码
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			logoQRCode = Base64.byteArrayToBase64(baos.toByteArray());
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.createSuccess(logoQRCode);
	}

	/**
	 * 从二维码中，解析数据
	 * 
	 * @param file 二维码图片文件
	 * @return 返回从二维码中解析到的数据值
	 * @throws Exception
	 */
	@RequestMapping("/parseQRCode")
	@ResponseBody
	public Result parseQRCode(HttpServletRequest request) {
		String text = null;
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
						String path = request.getSession().getServletContext().getRealPath("/") + "upload/" + makeFileName(myFileName);
						// 保存文件
						File localFile = new File(path);
						try {
							file.transferTo(localFile);
							FileInputStream fileInputStream = new FileInputStream(localFile);
							BufferedImage read = ImageIO.read(fileInputStream);
							BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(read);
							HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
							BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
							Map<DecodeHintType, ErrorCorrectionLevel> decodeMap = new HashMap<DecodeHintType, ErrorCorrectionLevel>();
							text = new MultiFormatReader().decode(binaryBitmap, decodeMap).getText();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return Result.createSuccess(text);
	}

	private String makeFileName(String filename) {
		if (StringUtils.isBlank(filename)) {
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString().replace("-", "").toUpperCase() + "_" + filename;
	}

}
