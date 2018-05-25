package cherish.cn.huaweiaftersale.okhttp.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cherish.cn.huaweiaftersale.base.AppContext;
import cherish.cn.huaweiaftersale.net.HttpManager;
import cherish.cn.huaweiaftersale.net.UrlConfigManager;
import cherish.cn.huaweiaftersale.net.UrlData;
import cherish.cn.huaweiaftersale.okhttp.BaseApi;
import cherish.cn.huaweiaftersale.okhttp.bean.TokenBean;
import cherish.cn.huaweiaftersale.okhttp.entity.GetTokenEntity;
import cherish.cn.huaweiaftersale.util.LogUtils;


public class GetTokenApi extends BaseApi {
    public GetTokenEntity getConfig(int funcKey,Context context, String status,String recordId,String descp) {
        // TODO Auto-generated method stub
        UrlData urlData = UrlConfigManager.getInstance().findURL(context, funcKey);
        String url=HttpManager.getInstance(context).createPostUrl(urlData);
        LogUtils.i("httpManager",url);
        JSONObject js=new JSONObject();
        try {
            js.put("recordId", recordId);
            js.put("descp", descp);
            js.put("status", status);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LogUtils.i("http_url","params:"+js.toString());
        String result=doJsonPost(url,js.toString());
        try {
            JSONObject jsonObject = new JSONObject(result);
        } catch (Exception e) {
            // TODO: handle exception
        }
        LogUtils.i("httpResponse","http result : "+"----"+result.toString());
        return null;
    }

    //发送JSON字符串 如果成功则返回成功标识。
    public static String doJsonPost(String urlPath, String Json) {
        // HttpClient 6.0被抛弃了
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("hlhupload", "doJsonPost: conn"+conn.getResponseCode());
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }
            Log.i("AAAA",result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
