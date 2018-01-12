package com.xz.faceIdentification.utils;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ImageUtil {

	/**
	 * 背景去除 简单案列，只适合背景单一的图像
	 * 
	 * @param frame
	 * @return
	 */
	public static Mat doBackgroundRemoval(Mat frame) {
		// init
		Mat hsvImg = new Mat();
		List<Mat> hsvPlanes = new ArrayList<>();
		Mat thresholdImg = new Mat();

		int thresh_type = Imgproc.THRESH_BINARY_INV;

		// threshold the image with the average hue value
		hsvImg.create(frame.size(), CvType.CV_8U);
		Imgproc.cvtColor(frame, hsvImg, Imgproc.COLOR_BGR2HSV);
		Core.split(hsvImg, hsvPlanes);

		// get the average hue value of the image

		Scalar average = Core.mean(hsvPlanes.get(0));
		double threshValue = average.val[0];
		Imgproc.threshold(hsvPlanes.get(0), thresholdImg, threshValue, 179.0, thresh_type);

		Imgproc.blur(thresholdImg, thresholdImg, new Size(5, 5));

		// dilate to fill gaps, erode to smooth edges
		Imgproc.dilate(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 1);
		Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 3);

		Imgproc.threshold(thresholdImg, thresholdImg, threshValue, 179.0, Imgproc.THRESH_BINARY);

		// create the new image
		Mat foreground = new Mat(frame.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
		thresholdImg.convertTo(thresholdImg, CvType.CV_8U);
		frame.copyTo(foreground, thresholdImg);// 掩膜图像复制
		return foreground;
	}

	/**
	 * 人脸检测技术 （靠边缘的和侧脸检测不准确）
	 * 
	 * @param image
	 * @param filename
	 */
	public static void FaceIdentification(Mat image, String filename) {
		// Create a face detector from the cascade file in the resources
		// directory.
		// CascadeClassifier faceDetector = new
		// CascadeClassifier(getClass().getResource("haarcascade_frontalface_alt2.xml").getPath());
		// Mat image = Highgui.imread(getClass().getResource("lena.png").getPath());
		// 注意：源程序的路径会多打印一个‘/’，因此总是出现如下错误
		/*
		 * Detected 0 faces Writing faceDetection.png libpng warning: Image width is
		 * zero in IHDR libpng warning: Image height is zero in IHDR libpng error:
		 * Invalid IHDR data
		 */
		// 因此，我们将第一个字符去掉
		String xmlfilePath = System.getProperty("user.dir")
				+ "\\src\\main\\resources\\xml\\haarcascade_frontalface_alt2.xml";
		CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
		// Mat image = Highgui.imread("d:\\timg (1).png");
		// Detect faces in the image.
		// MatOfRect is a special container class for Rect.
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

		// Draw a bounding box around each face.
		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 255, 0));
		}

		// Save the visualized detection.
		System.out.println(String.format("Writing %s", filename));
		System.out.println(filename);
		Highgui.imwrite(filename, image);
	}

	/**
	 * 边缘检测
	 * 
	 * @param frame
	 * @return
	 */
	public static Mat doCanny(Mat frame) {
		// init
		Mat grayImage = new Mat();
		Mat detectedEdges = new Mat();
		double threshold = 10;
		// convert to grayscale
		Imgproc.cvtColor(frame, grayImage, Imgproc.COLOR_BGR2GRAY);
		// reduce noise with a 3x3 kernel
		Imgproc.blur(grayImage, detectedEdges, new Size(3, 3));
		// canny detector, with ratio of lower:upper threshold of 3:1
		Imgproc.Canny(detectedEdges, detectedEdges, threshold, threshold * 3);
		// using Canny's output as a mask, display the result
		Mat dest = new Mat();
		frame.copyTo(dest, detectedEdges);
		return dest;
	}

	/**
	 * 将图片转换成灰度图片
	 * 
	 * @param in
	 * @param out
	 */
	public static void grayImage(String in, String out) {

		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\dll\\opencv_java2413.dll";
		System.load(path);
		// 读取图像，不改变图像的原始信息
		Mat m = Highgui.imread(in, Highgui.CV_LOAD_IMAGE_COLOR);
		if (m.empty()) {
			return;
		}
		// 将图片转换成灰度图片
		Mat gray = new Mat(m.size(), CvType.CV_8UC1);
		Imgproc.cvtColor(m, gray, Imgproc.COLOR_RGB2GRAY);

		// 计算灰度直方图
		List<Mat> images = new ArrayList<Mat>();
		images.add(gray);

		MatOfInt channels = new MatOfInt(0);
		MatOfInt histSize = new MatOfInt(256);
		MatOfFloat ranges = new MatOfFloat(0, 256);
		Mat hist = new Mat();
		Imgproc.calcHist(images, channels, new Mat(), hist, histSize, ranges);

		// mat求和
		System.out.println(Core.sumElems(hist));

		// 保存转换的图片
		Highgui.imwrite(out, gray);
	}

}
