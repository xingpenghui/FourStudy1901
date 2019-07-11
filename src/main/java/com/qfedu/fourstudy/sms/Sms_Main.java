package com.qfedu.fourstudy.sms;

/**
 *@Author feri
 *@Date Created in 2019/6/10 15:23
 */
public class Sms_Main {
    public static void main(String[] args) {
        int code=CodeUtil.createNum(6);
        System.out.println(SmsUtil.mobileQuery("18515990152",code));
    }
}