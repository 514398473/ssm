package com.xz.mail.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xz.mail.model.MailBean;
import com.xz.mail.service.MailSenderService;

public class MailSenderUtil {

	 /**
	  * 测试发送邮件
	  * @param args
	  * @throws Exception
	  */
    public static void main(String[] args) throws Exception{  
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");  
        MailSenderService mailSenderService = (MailSenderService) ac.getBean("mailSenderService");  
        MailBean mailBean = new MailBean();  
        mailBean.setFrom("15931216045@163.com");  
        mailBean.setFromName("XXX");  
        mailBean.setSubject("你好");  
        mailBean.setToEmails(new String[]{"15931216045@163.com"});  
        mailBean.setContext("<a href='www.baidu.com'><font color='red'>fdsfdsf</font></a>");  
        mailSenderService.sendMail(mailBean);  
    }  
}
