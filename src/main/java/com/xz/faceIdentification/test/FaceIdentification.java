package com.xz.faceIdentification.test;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.xz.faceIdentification.utils.ImageUtil;

public class FaceIdentification {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\dll\\opencv_java2413.dll";
		System.load(path);
		// Mat img = Highgui.imread("D:\\1.jpg");// 读图像
		// Mat new_img = ImageUtil.doBackgroundRemoval(img);
		// Highgui.imwrite("D:\\1.png", new_img);// 写图像

		Mat img = Highgui.imread("D:\\timg (1).png");// 读图像
		ImageUtil.FaceIdentification(img, "D:\\1.png");

		// Mat img = Highgui.imread("D:\\1.jpg");//读图像
		// Mat new_img = ImageUtil.doCanny(img);
		// Highgui.imwrite("D:\\1.png",new_img);//写图像

		// ImageUtil.grayImage("D:\\timg (1).png", "D:\\1.png");
	}

}
