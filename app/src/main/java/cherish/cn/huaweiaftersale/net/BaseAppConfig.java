package cherish.cn.huaweiaftersale.net;

import android.content.Context;
import android.os.Environment;


import java.io.File;

import cherish.cn.huaweiaftersale.util.SdkUtils;

public abstract class BaseAppConfig {
    public static final int LOAD_PAGESIZE = 20;
    public static final String LOAD_TYPE_NEW = "new";
    public static final String LOAD_TYPE_ADD = "add";
    /**
     * 默认缓存路径
     */
    public static String PATH_CACHE_DEFAULT;
    /**
     * 图片缓存路径
     */
    public static String PATH_IMAGE_CACHE;
    /**
     * 下载缓存路径
     */
    public static String PATH_DOWNLOAD_CACHE;
    /**
     * http缓存
     */
    public static String PATH_HTTP_CACHE;

    public static String PATH_HTTP_COOKIE;

    protected Context mContext;

    /**
     * sdcard 最小空间，如果小于10M，不会再向sdcard里面写入任何数据
     */
    public static final long SDCARD_MIN_SPACE = 1024 * 1024 * 100;

    public static boolean sIsBigARM = false;

    protected BaseAppConfig(Context context) {
        this.mContext = context;
        if (SdkUtils.checkSdCard()) {
            PATH_CACHE_DEFAULT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                    + context.getPackageName();
            new File(PATH_CACHE_DEFAULT).mkdirs();
        } else {
            PATH_CACHE_DEFAULT = context.getCacheDir().getAbsolutePath();
        }
        PATH_HTTP_CACHE = PATH_CACHE_DEFAULT + File.separator + "httpcache" + File.separator;
        new File(PATH_HTTP_CACHE).mkdirs();

        PATH_HTTP_COOKIE = PATH_CACHE_DEFAULT + "cookie";
        new File(PATH_HTTP_COOKIE).mkdirs();

        PATH_IMAGE_CACHE = PATH_CACHE_DEFAULT + File.separator + "image";
        new File(PATH_IMAGE_CACHE).mkdirs();

        PATH_DOWNLOAD_CACHE = PATH_CACHE_DEFAULT + File.separator + "dl";
        new File(PATH_DOWNLOAD_CACHE).mkdirs();
        // 手机内存>2G为标识为大内存设备
        if (SdkUtils.hasJellyBean_4_1()) {
            sIsBigARM = (2 * 1024 * 1024 * 0.8) > SdkUtils.getDeviceARMSize(context);
        }
    }
}
