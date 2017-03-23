package com.xz.mail.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xz.base.test.BaseTest;
import com.xz.mail.model.MailBean;
import com.xz.mail.service.MailSenderService;

public class MailSenderUtil extends BaseTest {

	@Autowired
	private MailSenderService mailSenderService;

	/**
	 * 测试发送邮件
	 */
	@Test
	public void testSendMail() {
		MailBean mailBean = new MailBean();
		mailBean.setFrom("15931216045@163.com");
		mailBean.setFromName("XXX");
		mailBean.setSubject("你好");
		mailBean.setToEmails(new String[] { "15931216045@163.com" });
		mailBean.setContext("<a href='www.baidu.com'><font color='red'>fdsfdsf</font></a>");
		try {
			mailSenderService.sendMail(mailBean);
			logger.info("测试邮件发送成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
