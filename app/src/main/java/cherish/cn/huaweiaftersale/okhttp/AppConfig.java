package cherish.cn.huaweiaftersale.okhttp;

import android.content.Context;

import cherish.cn.huaweiaftersale.net.BaseAppConfig;

public final class AppConfig extends BaseAppConfig {

    public static final String SAVE_CONFIG_KEY = "appConfig";

    /**
     * 消息提示-声音播放的间隔时间
     */
    public static final long MSG_NOTIFY_SOUND_INTERVAL = 2 * 1000;
    /**
     * 语音消息最长录音时间
     */
    public static final int MSG_VOICE_MIN_LENGTH = 1 * 1000;
    public static final int MSG_VOICE_MAX_LENGTH = 35 * 1000;

    public static final String KEY_EXTENSION = "collectorhunter";
    public static final String KEY_EXTENSION_NAMESPACE = "collector_hunter";
    public static final String KEY_TIME = "time";
    public static final String KEY_MSG_CONTENT_TYPE = "msg_content_type";
    public static final String KEY_MSG_TYPE = "msg_type";
    public static final String KEY_URI = "uri";
    public static final String KEY_DATA = "data";

    private static AppConfig sAppConfig;

    public static final int BOSS_MAIN_TAB_COUNT = 3;
    public static final int COUNT_DOWN_TIME = 60000;

//    public static final String ECARDURL="http://47.92.92.147:80/jk_api/v1/view/myCard";
//    public static final String BEHAVIORURL="http://47.92.92.147:80/jk_api/v1/view/customBehavior";
//    public static final String REPORTURL="http://47.92.92.147:80/jk_api/v1/view/statistics";

    //http://121.40.63.64:80/jk_api/

    public static final String ECARDURL = "http://47.92.92.147:80/jk_api/v1/view/myCard";
    public static final String BEHAVIORURL = "http://47.92.92.147:80/jk_api/v1/view/customBehavior";
    public static final String REPORTURL = "http://47.92.92.147:80/jk_api/v1/view/statistics";
    public static final String ADDSTATE = "http://47.92.92.147:80/jk_api/v1/view/addState";

    private AppConfig(Context context) {
        super(context);
    }

    public static synchronized void initialize(Context context) {
        if (null == sAppConfig) {
            sAppConfig = new AppConfig(context);
        }
    }


}

