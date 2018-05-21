package cherish.cn.huaweiaftersale.jpush;

import android.os.Bundle;
import android.util.Log;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.AppContext;
import cherish.cn.huaweiaftersale.util.SecurityHelper;
import cn.jpush.android.api.JPushInterface;

public final class JPushHelper {

    public static void init() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(AppContext.getInstance());
    }

    public static void resumePush() {
        JPushInterface.resumePush(AppContext.getInstance());
        setRegistrationId();
    }

    public static void setRegistrationId() {
        Log.e("channelId",JPushInterface.getRegistrationID(AppContext.getInstance())+"");
        if (SecurityHelper.isLogin()) {
            Bundle pars = new Bundle();
            pars.putString("deviceType", "6");
            pars.putString("channelId", JPushInterface.getRegistrationID(AppContext.getInstance()));
            ApiHelper.load(AppContext.getInstance(), R.id.api_set_push_channel, pars,null);
        }
    }

    public static void stopPush() {
        JPushInterface.stopPush(AppContext.getInstance());
    }
}
