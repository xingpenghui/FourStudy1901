package com.qfedu.fourstudy.email;

import com.qfedu.fourstudy.sms.CodeUtil;

/**
 *@Author feri
 *@Date Created in 2019/6/10 15:50
 */
public class Email_Main {
    public static void main(String[] args) {
//        EmailMsg emailMsg=new EmailMsg();
//        emailMsg.setCompany("深圳优之优贸易有限公司");
//        emailMsg.setContent("欢迎注册我们平台，您本次的验证码："+CodeUtil.createNum(6));
//        emailMsg.setEmail("18137797731@163.com");
//        emailMsg.setTitle("优之优注册-验证码");
//       EmailUtils.sendEmail(emailMsg);

        EmailMsg emailMsg=new EmailMsg();
        emailMsg.setCompany("深圳优之优贸易有限公司");
        emailMsg.setEmail("18137797731@163.com");
        String url="http://localhost:8080/useractive.do?email="+emailMsg.getEmail()+"&code="+CodeUtil.createNum(6);
        emailMsg.setContent("欢迎注册我们平台，为了账号安全，您需要点击激活:<a href='"+url+"'>"+
                url+"</a>");

        emailMsg.setTitle("优之优账号激活");
        EmailUtils.sendEmail(emailMsg);
    }
}
