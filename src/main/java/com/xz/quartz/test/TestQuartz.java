package com.xz.quartz.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xz.mail.model.MailBean;
import com.xz.mail.service.MailSenderService;


public class TestQuartz {

	public static Logger logger = LoggerFactory.getLogger(TestQuartz.class);

	/**
	 * 测试用Quartz定时自动发送邮件
	 */
	public void TestQuartzSendMailMethod() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-*.xml");
		MailSenderService mailSenderService = (MailSenderService) ac.getBean("mailSenderService");
		MailBean mailBean = new MailBean();
		mailBean.setFrom("15931216045@163.com");
		mailBean.setFromName("XXX");
		mailBean.setSubject("你好");
		mailBean.setToEmails(new String[] { "15931216045@163.com" });
		mailBean.setContext("<a href='www.baidu.com'><font color='red'>fdsfdsf</font></a>");
		try {
			mailSenderService.sendMail(mailBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
