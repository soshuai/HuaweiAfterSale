package cherish.cn.huaweiaftersale.base;

import android.content.Context;
import android.os.Bundle;

import java.io.File;
import java.util.Map;

import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.net.ErrorHandler;

/**
 * 数据加载工具
 *
 * @author Veryxyf
 */
public final class ApiHelper {

    public static void load(Context context, String url) {
        DataManager.getInstance().exeUrl(context, url);
    }

    public static String getUrl(Context context, int funcKey, Bundle pars) {
        return DataManager.getInstance().getRequestUrl(context, funcKey, pars);
    }

    public static String getUrl(Context context, int funcKey) {
        return DataManager.getInstance().getRequestUrl(context, funcKey, null);
    }

    public static void load(Context context, int funcKey, Bundle pars, DataCallback callback) {
        load(context, funcKey, pars, callback, null);
    }

    public static void load(Context context, int funcKey, DataCallback callback) {
        load(context, funcKey, null, callback, null);
    }

    public static void load(Context context, int funcKey, Bundle pars, DataCallback callback, ErrorHandler errorHandler) {
        DataManager.getInstance().getData(context, funcKey, pars, callback, errorHandler);

    }

    public static void submit(Context context, int funcKey, Map<String, String> params, DataCallback callback, Map<String, File> files) {
        DataManager.getInstance().submit(context, funcKey, params, callback,files);

    }

    public static void load(Context context, int funcKey, DataCallback callback, ErrorHandler errorHandler) {
        load(context, funcKey, null, callback, errorHandler);
    }

    public static void cancel(Context context) {
        DataManager.getInstance().cancelData(context);
    }
}
