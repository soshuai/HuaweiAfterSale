package cherish.cn.huaweiaftersale.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志打印工具类
 */
public final class LogUtils {
    public static boolean isDebug = true;

    private LogUtils() {
    }

    public static void e(String tag, Throwable e) {
        if(null == e)
            return;
        if (isDebug) {
            Log.e(tag, Log.getStackTraceString(e));
        }
    }

    /**
     * 日志打印
     * 
     * @param tag
     * @param message
     */
    public static void v(String tag, String message) {
        if(TextUtils.isEmpty(message))
            return;
        if (isDebug) {
            Log.v(tag, message);
        }
    }

    /**
     * 日志打印
     * 
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        if(TextUtils.isEmpty(message))
            return;
        if (isDebug) {
            Log.d(tag, message);
        }
    }

    /**
     * 日志打印
     * 
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        if(TextUtils.isEmpty(message))
            return;
        if (isDebug) {
            Log.e(tag, getLogPointInfo() + "\n" + message);
        }
    }

    /**
     * 日志打印
     *
     * @param tag
     * @param ex
     */
    public static void e(String tag, Exception ex) {
        if(null == ex)
            return;
        if (isDebug) {
            Log.e(tag, getLogPointInfo() + "\n" + ex.getMessage());
        }
    }

    /**
     * 日志打印
     * 
     * @param tag
     * @param message
     */
    public static void w(String tag, String message) {
        if(TextUtils.isEmpty(message))
            return;
        if (isDebug) {
            Log.w(tag, getLogPointInfo() + "\n" + message);
        }
    }

    /**
     * 日志打印
     * 
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        if(TextUtils.isEmpty(message))
            return;
        if (isDebug) {
            Log.i(tag, message);
        }
    }

    /**
     * 获取日志打印点相关信息
     * 
     * @return
     */
    private static String getLogPointInfo() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return "";
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            String mClassName = st.getClassName();
            if (mClassName.equals(Thread.class.getName())) {
                continue;
            }
            if (mClassName.equals(LogUtils.class.getName())) {
                continue;
            }
            return mClassName + "[ " + st.getLineNumber() + " : " + st.getMethodName() + " ]";
        }
        return "";
    }

    public static void LongI(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.i(tag, msg);
    }
}
