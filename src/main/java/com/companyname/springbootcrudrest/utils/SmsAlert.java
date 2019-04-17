package com.companyname.springbootcrudrest.utils;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

public class SmsAlert {
    // 短信应用SDK AppID
    static int appid = 1400203235; // 1400开头

    // 短信应用SDK AppKey
    static String appkey = "94b943158e2411b200e9ca3b3d05c331";

    // 需要发送短信的手机号码
//    String[] phoneNumbers = {"15867867810", "13575404606", "15336665843"};

    // 短信模板ID，需要在短信应用中申请

    public static final int TEMPLATE_ID_ONLINE = 314795; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    public static final int TEMPLATE_ID_OFFLINE = 314931; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    //templateId7839对应的内容是"您的验证码是: {1}"
// 签名
    static String smsSign = "宁波树莓网络科技有限公司"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台申请。


    public static void sendToOne(String phoneNumber, String[] params, int templateId) {
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
//            System.out.println(result);
        } catch (
                HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (
                JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (
                IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

    }

    public static void sendToAll(String[] phoneNumbers, String[] params, int templateId) {
        try {
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult result = msender.sendWithParam("86", phoneNumbers,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
//            System.out.println(result);
        } catch (
                HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (
                JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (
                IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

    }

}
