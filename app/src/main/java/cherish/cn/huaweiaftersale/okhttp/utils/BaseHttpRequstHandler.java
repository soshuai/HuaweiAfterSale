package cherish.cn.huaweiaftersale.okhttp.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

/**
 * http/https请求处理器
 */
public abstract class BaseHttpRequstHandler {

    /**
     * 默认连接超时时间 单位：毫秒
     */
    final int     CONNECT_TIME_OUT = 20 * 1000;
    /**
     * 默认读取超时时间 单位：毫秒
     */
    final int     READ_TIME_OUT    = 20 * 1000;
    /**
     * 默认url是否encode
     */
    final boolean IS_ENCODE        = true;
    /**
     * 默认传输是否gzip
     */
    final boolean IS_GZIP          = true;

    /**
     * @see #getMethod(String, Map, Map, boolean, boolean, HttpResponseListener)
     */
    public String getMethod(String url) {
        return getMethod(url, null, null, IS_ENCODE, IS_GZIP, null);
    }

    /**
     * @see #getMethod(String, Map, Map, boolean, boolean, HttpResponseListener)
     */
    public String getMethod(String url, HttpResponseListener handle) {
        return getMethod(url, null, null, IS_ENCODE, IS_GZIP, handle);
    }

    /**
     * @see #getMethod(String, Map, Map, boolean, boolean, HttpResponseListener)
     */
    public String getMethod(String url, Map<String, String> paramter) {
        return getMethod(url, null, paramter, IS_ENCODE, IS_GZIP, null);
    }

    /**
     * @see #getMethod(String, Map, Map, boolean, boolean, HttpResponseListener)
     */
    public String getMethod(String url, Map<String, String> header, Map<String, String> paramter,
                            HttpResponseListener handle) {
        return getMethod(url, header, paramter, IS_ENCODE, IS_GZIP, handle);
    }

    /**
     * @see #getMethod(String, Map, Map, boolean, boolean, HttpResponseListener)
     */
    public String getMethod(String url, Map<String, String> paramter, HttpResponseListener handle) {
        return getMethod(url, null, paramter, IS_ENCODE, IS_GZIP, handle);
    }

    /**
     * get方法
     * 
     * @param url
     *            请求地址
     * @param header
     *            请求头
     * @param paramter
     *            请求参数
     * @param encode
     *            url是否encode
     * @param gzip
     *            传输是否gzip压缩
     * @param handle
     *            自定义回调
     * @return
     */
    public String getMethod(String url, Map<String, String> header, Map<String, String> paramter, boolean encode,
                            boolean gzip, HttpResponseListener handle) {
        setConnectionFactory();

        String result = "";
        HttpRequest request = HttpRequest.get(url, paramter, encode);
        httpRequestConfig(request);
        // 是否压缩
        if (gzip) {
            request.acceptGzipEncoding().uncompress(true);
        }
        // 设置header
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> temp : header.entrySet()) {
                request.header(temp);
            }
        }
        try {
            if (request.ok()) {
                result = request.body();
                if (handle != null) {
                    handle.onSuccess(result, request.code());
                }
            }
        } catch (HttpRequest.HttpRequestException exception) {
            if (handle != null && SocketTimeoutException.class.isInstance(exception.getCause())) {
                handle.onTimeOut();
            } else if (handle != null) {
                handle.onFailure(exception.getCause());
            }
            result = "";
        }
        return result;
    }

    /**
     * @see #postMethod(String, Map, Map, Map, HttpResponseListener)
     */
    public String postMethod(String url, Map<String, String> form) {
        return postMethod(url, null, null, form, IS_ENCODE, null);
    }

    /**
     * @see #postMethod(String, Map, Map, Map, HttpResponseListener)
     */
    public String postMethod(String url, Map<String, String> form, HttpResponseListener handle) {
        return postMethod(url, null, null, form, IS_ENCODE, handle);
    }

    /**
     * @see #postMethod(String, Map, Map, Map, HttpResponseListener)
     */
    public String postMethod(String url, Map<String, String> paramter, Map<String, String> form) {
        return postMethod(url, null, paramter, form, IS_ENCODE, null);
    }

    /**
     * @see #postMethod(String, Map, Map, Map, HttpResponseListener)
     */
    public String postMethod(String url, Map<String, String> paramter, Map<String, String> form,
                             HttpResponseListener handle) {
        return postMethod(url, null, paramter, form, IS_ENCODE, handle);
    }

    /**
     * @see #postMethod(String, Map, Map, Map, HttpResponseListener)
     */
    public String postMethod(String url, Map<String, String> header, Map<String, String> paramter,
                             Map<String, String> form) {
        return postMethod(url, header, paramter, form, IS_ENCODE, null);
    }

    /**
     * post方法
     * 
     * @param url
     *            请求地址
     * @param header
     *            请求头
     * @param paramter
     *            请求url上的参数
     * @param form
     *            表单数据
     * @param handle
     *            自定义回调
     * @return 请求返回内容
     */
    public String postMethod(String url, Map<String, String> header, Map<String, String> paramter,
                             Map<String, String> form, HttpResponseListener handle) {
        return postMethod(url, header, paramter, form, IS_ENCODE, handle);
    }

    /**
     * post方法
     * 
     * @param url
     *            请求地址
     * @param header
     *            请求头
     * @param paramter
     *            请求url上的参数
     * @param form
     *            表单数据
     * @param encode
     *            url是否encode
     * @param handle
     *            自定义回调
     * @return
     */
    public String postMethod(String url, Map<String, String> header, Map<String, String> paramter,
                             Map<String, String> form, boolean encode, HttpResponseListener handle) {
        setConnectionFactory();

        String result = "";
        HttpRequest request = HttpRequest.post(url, paramter, encode);
        httpRequestConfig(request);

        // 设置header
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> temp : header.entrySet()) {
                request.header(temp);
            }
        }

        try {
            // form数据
            if (form != null && form.size() > 0) {
                request.form(form);
            }

            // 设置参数
            if (paramter != null && paramter.size() > 0) {
                request.form(paramter);
            }

            if (request.ok()) {
                result = request.body();
                if (handle != null) {
                    handle.onSuccess(result, request.code());
                }
            }
        } catch (HttpRequest.HttpRequestException exception) {
            if (handle != null && SocketTimeoutException.class.isInstance(exception.getCause())) {
                handle.onTimeOut();
            } else if (handle != null) {
                handle.onFailure(exception.getCause());
            }
            exception.printStackTrace();
            result = "";
        }

        return result;
    }

    protected void httpRequestConfig(HttpRequest request) {
    }

    /**
     * 初始化HttpRequest
     */
    protected void setConnectionFactory() {
        setConnectionFactory(sConnectionFactory);
    }

    protected void setConnectionFactory(HttpRequest.ConnectionFactory factory) {
        HttpRequest.setConnectionFactory(factory);
    }

    /**
     * 默认连接的设置
     */
    private HttpRequest.ConnectionFactory sConnectionFactory = new HttpRequest.ConnectionFactory() {
                                                                 public HttpURLConnection create(URL url)
                                                                         throws IOException {
                                                                     HttpURLConnection ret = (HttpURLConnection) url
                                                                             .openConnection();
                                                                     ret.setConnectTimeout(CONNECT_TIME_OUT);
                                                                     ret.setReadTimeout(READ_TIME_OUT);
                                                                     return ret;
                                                                 }

                                                                 public HttpURLConnection create(URL url, Proxy proxy)
                                                                         throws IOException {
                                                                     HttpURLConnection ret = (HttpURLConnection) url
                                                                             .openConnection(proxy);
                                                                     ret.setConnectTimeout(CONNECT_TIME_OUT);
                                                                     ret.setReadTimeout(READ_TIME_OUT);
                                                                     return ret;
                                                                 }
                                                             };

    public byte[] loadByteFromNetwork(String url) throws IOException {
        byte[] ret = null;
        setConnectionFactory();

        HttpRequest request = HttpRequest.get(url);

        if (request.ok()) {
            BufferedInputStream in = new BufferedInputStream(request.getConnection().getInputStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            while (true) {
                int len = in.read(buf);
                if (len < 0) {
                    break;
                }
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            ret = out.toByteArray();
        }
        return ret;
    }

    //发送JSON字符串 如果成功则返回成功标识。
    public static String doJsonPost(String urlPath, String Json,String header) {
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
            conn.setRequestProperty("Authorization","Bearer "+header);
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
