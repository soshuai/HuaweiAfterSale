package cherish.cn.huaweiaftersale.net;

import android.content.Context;
import android.text.TextUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import java.io.File;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import cherish.cn.huaweiaftersale.base.SecurityHolder;
import cherish.cn.huaweiaftersale.net.callback.BaseJsonCallback;
import cherish.cn.huaweiaftersale.net.callback.DownloadCallback;
import cherish.cn.huaweiaftersale.net.cookie.PersistentCookieStore;
import cherish.cn.huaweiaftersale.util.LogUtils;
import cherish.cn.huaweiaftersale.util.MD5;


public final class HttpManager {

    static final String REQUEST_GET = "get";
    static final String REQUEST_POST = "post";

    private static HttpManager mInstance;

    private OkHttpClient mClient;

    /**
     * 通过http获取数据
     * @param context
     */
    private HttpManager(Context context) {
        super();

        this.mClient = new OkHttpClient();
        this.mClient.setConnectTimeout(8, TimeUnit.SECONDS);
        this.mClient.setReadTimeout(16, TimeUnit.SECONDS);
        this.mClient.setWriteTimeout(8, TimeUnit.SECONDS);
        this.mClient.setRetryOnConnectionFailure(true);

        this.mClient.setCookieHandler(new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL));
    }

    public OkHttpClient getHttpClient() {
        return mClient;
    }

    public static synchronized HttpManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpManager(context);
        }
        return mInstance;
    }

    /**
     * 上传文件，主要用于ocr
     * @param context
     * @param url
     * @param pars
     * @param targetFile
     * @param callback
     */
    public void uploadFile(Context context, String url, Map<String, String> pars, File targetFile, Callback callback) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        builder.addFormDataPart("file", targetFile.getName(), RequestBody.create(MediaType.parse("image/jpeg"), targetFile));
        Iterator<Entry<String, String>> it = pars.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> me = it.next();
            if (me.getValue() == null) {
                continue;
            }
            String key = me.getKey();
            if (key.startsWith("&")) {
                continue;
            }
            builder.addFormDataPart(key, me.getValue());
        }

        Request request = new Request.Builder().url(url).tag(context.getClass().getCanonicalName())
                .post(builder.build()).build();
        LogUtils.i("httpManager", request.urlString());
        mClient.newCall(request).enqueue(callback);
    }

    public void getUrl(Context context, String url) {
        Request request = new Request.Builder().url(url).tag(context.getClass().getCanonicalName()).build();
        mClient.newCall(request).enqueue(null);
    }

    public void sendRequest(Context context, UrlData urlData) {
        sendRequest(context, urlData, null);
    }

    public void sendRequest(Context context, UrlData urlData, BaseJsonCallback callback) {
        sendRequest(context, urlData, null, callback);
    }

    /**
     * 获取http 请求的url
     * @param context
     * @param urlData
     * @param pars
     * @return
     */
    public String getRequestUrl(final Context context, UrlData urlData, Map<String, String> pars) {
        LogUtils.i("httpManager", pars == null ? "" : pars.toString());

        if (pars == null) {
            pars = new HashMap(0);
        }
        if (REQUEST_GET.equalsIgnoreCase(urlData.getMethod())) {
            return this.createGetUrl(urlData, pars);
        } else if (REQUEST_POST.equalsIgnoreCase(urlData.getMethod())) {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            Iterator<Entry<String, String>> it = pars.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> me = it.next();
                if (me.getValue() == null) {
                    continue;
                }
                String key = me.getKey();
                if (key.startsWith("&")) {
                    continue;
                }
                String value = me.getValue();
                if (!TextUtils.isEmpty(urlData.getEncrypt()) && urlData.getEncrypt().contains(key)) {
                    try {
                        value = SecurityHolder.findSecurityData().getAesEncrypt().encrytorAsString(value, "utf8");
                    } catch (Exception e) {
                        throw new IllegalArgumentException("encrypt error");
                    }
                }
                builder.add(key, value);
            }
            return this.createPostUrl(urlData);
        }
        return null;
    }

    public void downloadFile(final Context context, String url, DownloadCallback callback){
        Request request = new Request.Builder().url(url).tag(context.getClass().getCanonicalName())
                .build();
        mClient.newCall(request).enqueue(callback);
    }
    /**
     * 发送http请求
     * @param context
     * @param urlData
     * @param pars
     * @param callback
     */
    public void sendRequest(final Context context, UrlData urlData, Map<String, String> pars, BaseJsonCallback callback) {
        LogUtils.i("httpManager", pars == null ? "" : pars.toString());

        if (pars == null) {
            pars = new HashMap(0);
        }
        if (REQUEST_GET.equalsIgnoreCase(urlData.getMethod())) {
            Request request = new Request.Builder().url(this.createGetUrl(urlData, pars)).tag(context.getClass().getCanonicalName())
                    .build();
            LogUtils.i("httpManager", request.url() + "");
            mClient.newCall(request).enqueue(callback);
        } else if (REQUEST_POST.equalsIgnoreCase(urlData.getMethod())) {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            Iterator<Entry<String, String>> it = pars.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> me = it.next();
                if (me.getValue() == null) {
                    continue;
                }
                String key = me.getKey();
                if (key.startsWith("&")) {
                    continue;
                }
                String value = me.getValue();
                if (!TextUtils.isEmpty(urlData.getEncrypt()) && urlData.getEncrypt().contains(key)) {
                    try {
                        value = SecurityHolder.findSecurityData().getAesEncrypt().encrytorAsString(value, "utf8");
                    } catch (Exception e) {
                        throw new IllegalArgumentException("encrypt error");
                    }
                }
                builder.add(key, value);
            }

            Request request = new Request.Builder().url(this.createPostUrl(urlData)).tag(context.getClass().getCanonicalName())
                    .post(builder.build()).build();
            LogUtils.i("httpManager", request.urlString());
            mClient.newCall(request).enqueue(callback);
        } else {
            throw new UnsupportedOperationException("http method must be GET or POST");
        }
    }

    /**
     * 设置完整的url
     * @param urlData
     * @return
     */
    private String makeFullUrl(UrlData urlData) {
        if (urlData.isFullUrl()) {
            return urlData.getUrl();
        } else {
            return UrlConfigManager.getInstance().getBaseUrl(urlData.isHttps(), urlData.getVersion()) + File.separator + urlData.getUrl();
        }
    }

    /**
     * 添加url签名，保证url不会被篡改
     * @param urlData
     * @param url
     * @param pars
     * @return
     */
    private String addSignToUrl(UrlData urlData, String url, Map<String, String> pars) {
        String[] arr = createSign(urlData, pars, SecurityHolder.findSecurityData().getSecretKey());
        if (urlData.isFullUrl()) {
            if (url.contains("?")) {
                url = (url + "&" + arr[0]);
            } else {
                url = (url + "?" + arr[0]);
            }
        } else {
            if (urlData.isNeedToken()) {
                url = (url + "?" + arr[0] + "&sign=" + arr[1]);
            } else {
                url = (url + "?" + arr[0]);
            }
        }
        return url;
    }

    private String createPostUrl(UrlData urlData) {
        String url = makeFullUrl(urlData);
        if (urlData.isNeedToken()) {
            Map<String, String> pars = new HashMap(1);
            pars.put(UrlConfigManager.getInstance().getTokenName(), SecurityHolder.findSecurityData().getToken());
            url = addSignToUrl(urlData, url, pars);
        }
        return url;
    }

    private String createGetUrl(UrlData urlData, Map<String, String> pars) {
        String url = makeFullUrl(urlData);
        if (urlData.isNeedToken()) {
            if (pars == null) {
                pars = new HashMap(1);
            }
            pars.put(UrlConfigManager.getInstance().getTokenName(), SecurityHolder.findSecurityData().getToken());
            url = addSignToUrl(urlData, url, pars);
        } else {
            String queryString = createQueryString(urlData, pars);
            if (!TextUtils.isEmpty(queryString)) {
                if (url.contains("?")) {
                    url = url + ("&" + queryString);
                } else {
                    url = url + ("?" + queryString);
                }
            }
        }
        return url;
    }

    public void cancelRequest(Context context) {
        mClient.cancel(context.getClass().getCanonicalName());
    }

    /**
     * 分析所有的参数，并排序，保证生成的签名一致
     * @param urlData
     * @param pars
     * @return
     */
    private static String createQueryString(UrlData urlData, Map<String, String> pars) {
        Map<String, String> tempParas = new TreeMap<String, String>(pars);

        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : tempParas.entrySet()) {
            if (sb.length() > 0)
                sb.append("&");

            String key = entry.getKey();
            if (key.startsWith("&")) {
                continue;
            }
            String value = entry.getValue();
            if (value != null) {
                if (!TextUtils.isEmpty(urlData.getEncrypt()) && urlData.getEncrypt().contains(key)) {
                    try {
                        value = SecurityHolder.findSecurityData().getAesEncrypt().encrytorAsString(value, "utf8");
                    } catch (Exception e) {
                        throw new IllegalArgumentException("encrypt error");
                    }
                }
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    private static String[] createSign(UrlData urlData, Map<String, String> pars, String secretKey) {
        String queryString = createQueryString(urlData, pars);

        StringBuilder sb2 = new StringBuilder(queryString);
        if (queryString.length() > 0)
            sb2.append("&");
        sb2.append(UrlConfigManager.getInstance().getSignName()).append("=").append(secretKey);

        String[] ret = new String[2];
        ret[0] = queryString;
        ret[1] = MD5.calcMD5(sb2.toString()).toUpperCase();
        return ret;
    }
}
