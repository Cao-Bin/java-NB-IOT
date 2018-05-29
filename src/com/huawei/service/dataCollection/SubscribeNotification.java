package com.huawei.service.dataCollection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * Subscribe Device Change Info :
 * This interface is used by NAs to subscribe to device change.
 * When device information is changed, the IoT platform sends a notification to NAs.
 */
public class SubscribeNotification {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication，get token
        String accessToken = login(httpsUtil);

        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;
        String urlSubscribe = Constant.SUBSCRIBE_NOTIFYCATION;

        /*
         * subscribe deviceAdded notification
         */
        String callbackurl = Constant.DEVICE_ADDED_CALLBACK_URL;
        String notifyType = Constant.DEVICE_ADDED;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.DEVICE_INFO_CHANGED_CALLBACK_URL;
        notifyType = Constant.DEVICE_INFO_CHANGED;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.DEVICE_DATA_CHANGED_CALLBACK_URL;
        notifyType = Constant.DEVICE_DATA_CHANGED;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.COMMAND_RSP_CALLBACK_URL;
        notifyType = Constant.COMMAND_RSP;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.DEVICE_DELETED_CALLBACK_URL;
        notifyType = Constant.DEVICE_DELETED;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.MESSAGE_CONFIRM_CALLBACK_URL;
        notifyType = Constant.MESSAGE_CONFIRM;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.DEVICE_EVENT_CALLBACK_URL;
        notifyType = Constant.DEVICE_EVENT;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.SERVICE_INFO_CHANGED_CALLBACK_URL;
        notifyType = Constant.SERVICE_INFO_CHANGED;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.RULE_EVENT_CALLBACK_URL;
        notifyType = Constant.RULE_EVENT;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.BIND_DEVICE_CALLBACK_URL;
        notifyType = Constant.BIND_DEVICE;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
        
        callbackurl = Constant.DEVICE_DATAS_CHANGED_CALLBACK_URL;
        notifyType = Constant.DEVICE_DATAS_CHANGED;
        subscribe(httpsUtil, accessToken, appId, urlSubscribe, callbackurl, notifyType);
    }


	private static void subscribe(HttpsUtil httpsUtil, String accessToken, String appId, String urlSubscribe,
			String callbackurl_deviceAdded, String notifyType_deviceAdded) throws IOException {
		Map<String, Object> paramSubscribe = new HashMap<>();
        paramSubscribe.put("notifyType", notifyType_deviceAdded);
        paramSubscribe.put("callbackurl", callbackurl_deviceAdded);
        String resultstr="notifyType："+notifyType_deviceAdded+",callbackurl："+callbackurl_deviceAdded;
        System.out.println("{\"notifyType\":\""+notifyType_deviceAdded+"\",\"callbackurl\":\""+callbackurl_deviceAdded+"\"}");
        String jsonRequest = JsonUtil.jsonObj2Sting(paramSubscribe);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        HttpResponse httpResponse = httpsUtil.doPostJson(urlSubscribe, header, jsonRequest);

        String bodySubscribe = httpsUtil.getHttpResponseBody(httpResponse);

        System.out.println("SubscribeNotification，" + resultstr + ", response content:");
        System.out.print(httpResponse.getStatusLine());
        System.out.println(bodySubscribe);
        System.out.println();
	}


    /**
     * Authentication，get token
     * */
    @SuppressWarnings("unchecked")
    public static String login(HttpsUtil httpsUtil) throws Exception {

        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);

        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

        System.out.println("app auth success,return accessToken:");
        System.out.print(responseLogin.getStatusLine());
        System.out.println(responseLogin.getContent());
        System.out.println();

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("accessToken");
    }

}
