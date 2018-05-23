package cherish.cn.huaweiaftersale.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.net.DefaultErrorHandler;
import cherish.cn.huaweiaftersale.net.ErrorHandler;
import cherish.cn.huaweiaftersale.net.HttpManager;
import cherish.cn.huaweiaftersale.net.UrlConfigManager;
import cherish.cn.huaweiaftersale.net.UrlData;
import cherish.cn.huaweiaftersale.net.callback.BaseJsonCallback;
import cherish.cn.huaweiaftersale.net.callback.JsonArrayCallback;
import cherish.cn.huaweiaftersale.net.callback.JsonObjectCallback;
import cherish.cn.huaweiaftersale.okhttp.entity.BaseApiEntity;
import cherish.cn.huaweiaftersale.okhttp.entity.VideoCheckDictEntity;
import cherish.cn.huaweiaftersale.okhttp.utils.JsonUtils;
import cherish.cn.huaweiaftersale.util.LogUtils;
import cherish.cn.huaweiaftersale.util.UploadUtil;

/**
 * 数据管理器
 *
 * @author Veryxyf
 */
public final class DataManager {

    private static DataManager sInstance;

    private Map<String, List<BaseJsonCallback>> mCallbackMap;

    private ErrorHandler mDefaultErrorHandler;

    private SparseArray<Integer> mUsedUrlMap;

    private DataManager() {
        super();
        mCallbackMap = new ConcurrentHashMap<String, List<BaseJsonCallback>>();
        mUsedUrlMap = new SparseArray();
        mDefaultErrorHandler = new DefaultErrorHandler();
    }

    /**
     * 单例
     * @return
     */
    public static synchronized DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }

    /**
     * 取消数据请求
     * @param context
     */
    public void cancelData(Context context) {
        List<BaseJsonCallback> clist = mCallbackMap.remove(context.getClass().getCanonicalName());
        if (clist != null) {
            for (BaseJsonCallback c : clist) {
                c.release();
            }
            clist.clear();
        }
        HttpManager.getInstance(context).cancelRequest(context);
    }

    /**
     * 执行url
     * @param context
     * @param url
     */
    public void exeUrl(Context context, String url) {
        if (!TextUtils.isEmpty(url)) {
            HttpManager.getInstance(context).getUrl(context, url);
        }
    }

    /**
     * 从bundle中获取参数映射
     * @param bundle
     * @return
     */
    private Map<String, String> getMapFromBundle(Bundle bundle) {
        if (bundle != null) {
            Map<String, String> pars = new HashMap();
            if (bundle != null) {
                Iterator<String> it = bundle.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    Object value = bundle.get(key);
                    if (value != null) {
                        pars.put(key, value.toString());
                    }
                }
            }
            return pars;
        }
        return null;
    }

    /**
     * 根据配置文件中的class全名生成实例
     * @param context
     * @param classFullname
     * @return
     */
    private Class classForName(Context context, String classFullname) {
        try {
            if (classFullname.startsWith(".")) {
                return Class.forName(context.getPackageName() + classFullname);
            } else {
                return Class.forName(classFullname);
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
        }
        return null;
    }

    /**
     * 获取数据
     * @param context
     * @param funcKey
     * @param callback
     */
    public void getData(Context context, int funcKey, DataCallback callback) {
        getData(context, funcKey, null, callback);
    }
    /**
     * 获取数据
     * @param context
     * @param funcKey
     * @param callback
     */
    public void getData(Context context, int funcKey, Bundle pars, DataCallback callback) {
        getData(context, funcKey, pars, callback, null);
    }

    /**
     * 获取数据
     * @param context
     * @param funcKey
     * @param callback
     */
    public void getData(Context context, int funcKey, DataCallback callback, ErrorHandler errorHandler) {
        getData(context, funcKey, null, callback, errorHandler);
    }

    /**
     * 数据取得结束后
     */
    public void dataEnd(int funcKey) {
        synchronized (this.mUsedUrlMap) {
            Integer usedCount = this.mUsedUrlMap.get(funcKey);
            if (usedCount != null && usedCount.intValue() > 0) {
                this.mUsedUrlMap.put(funcKey, usedCount.intValue() - 1);
            }
        }
    }

    /**
     * 获取请求的url
     * @param context
     * @param funcKey
     * @param bundle
     * @return
     */
    public String getRequestUrl(Context context, int funcKey, Bundle bundle) {
        UrlData urlData = UrlConfigManager.getInstance().findURL(context, funcKey);
        if (urlData == null || TextUtils.isEmpty(urlData.getReturnClass())) {
            throw new IllegalArgumentException();
        }
        if (urlData.isNeedToken() && TextUtils.isEmpty(SecurityHolder.findSecurityData().getToken())) {
            return null;
        }
        return HttpManager.getInstance(context).getRequestUrl(context, urlData, getMapFromBundle(bundle));
    }

    /**
     * 获取数据
     * @param context
     * @param funcKey
     * @param bundle
     * @param callback
     * @param errorHandler
     */
    public void getData(Context context, int funcKey, Bundle bundle, DataCallback callback, ErrorHandler errorHandler) {
        UrlData urlData = UrlConfigManager.getInstance().findURL(context, funcKey);
        if (urlData == null || TextUtils.isEmpty(urlData.getReturnClass())) {
            throw new IllegalArgumentException();
        }
        if (urlData.isNeedToken() && TextUtils.isEmpty(SecurityHolder.findSecurityData().getToken())) {
            return;
        }
        synchronized (this.mUsedUrlMap) {
            Integer usedCount = this.mUsedUrlMap.get(funcKey);
            if (usedCount != null && usedCount.intValue() >= urlData.getCountLimit()) {
                LogUtils.w("dataManager", "funcKey get count limit " + funcKey);
                return;
            }
            this.mUsedUrlMap.put(funcKey, usedCount == null ? 1 : usedCount.intValue() + 1);
        }

        if (errorHandler == null) {
            errorHandler = mDefaultErrorHandler;
        }

        String jsonClass = urlData.getReturnClass().trim();
        BaseJsonCallback jcallback = null;
        if (jsonClass.startsWith("[")) {
            jcallback = new JsonArrayCallback(classForName(context, jsonClass.substring(1, jsonClass.length())),
                    funcKey, bundle, callback, errorHandler);
        } else {
            jcallback = new JsonObjectCallback(classForName(context, jsonClass), funcKey, bundle, callback, errorHandler);
        }

        String contextClassName = context.getClass().getCanonicalName();
        List<BaseJsonCallback> clist = mCallbackMap.get(contextClassName);
        if (clist == null) {
            clist = new ArrayList();
            mCallbackMap.put(contextClassName, clist);
        }
        clist.add(jcallback);
        HttpManager.getInstance(context).sendRequest(context, urlData, getMapFromBundle(bundle), jcallback);
    }


    /**
     * 获取数据
     * @param context
     * @param callback
     */
    public void submit(Context context, String url, Map<String, String> params, DataCallback callback,Map<String, File> files) {
//        UrlData urlData = UrlConfigManager.getInstance().findURL(context, funcKey);
//        if (urlData == null || TextUtils.isEmpty(urlData.getReturnClass())) {
//            throw new IllegalArgumentException();
//        }
//        if (urlData.isNeedToken() && TextUtils.isEmpty(SecurityHolder.findSecurityData().getToken())) {
//            return;
//        }
//        synchronized (this.mUsedUrlMap) {
//            Integer usedCount = this.mUsedUrlMap.get(funcKey);
//            if (usedCount != null && usedCount.intValue() >= urlData.getCountLimit()) {
//                LogUtils.w("dataManager", "funcKey get count limit " + funcKey);
//                return;
//            }
//            this.mUsedUrlMap.put(funcKey, usedCount == null ? 1 : usedCount.intValue() + 1);
//        }
//
//        String jsonClass = urlData.getReturnClass().trim();

        String result = "";
        VideoCheckDictEntity entity = null;
        Class<VideoCheckDictEntity> entityClass = VideoCheckDictEntity.class;
        try {
            result = UploadUtil.post(url, params, files);
            if (TextUtils.isEmpty(result)) {
                entity = entityClass.getDeclaredConstructor(Integer.class, String.class).newInstance(
                        BaseApiEntity.ERROR_CODE_NET, "网络异常");
            } else {
                entity= JsonUtils.parseGson(result,entityClass);
                if (null == entity) {
                    entity = entityClass.getDeclaredConstructor(Integer.class, String.class).newInstance(
                            BaseApiEntity.ERROR_CODE_JSON, "json格式不正确");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                entity = entityClass.getDeclaredConstructor(Integer.class, String.class).newInstance(
                        BaseApiEntity.ERROR_CODE_UNKOWN, "未知异常");
            } catch (Exception e1) {
            }
        }
        Log.i("http url", url+"");
        Log.i("http result", result);
    }
}
