package cherish.cn.huaweiaftersale.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cherish.cn.huaweiaftersale.LoginActivity;
import cherish.cn.huaweiaftersale.MainActivity;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.AppContext;
import cherish.cn.huaweiaftersale.util.LogUtils;
import cherish.cn.huaweiaftersale.util.SecurityHelper;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("veryw","接收到消息");
        Bundle bundle = intent.getExtras();
        String content = new String();
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.d(TAG, "【MyReceiver】 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            String json = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            JSONObject jsonObject = JSON.parseObject(json);
            int type = jsonObject.getIntValue("type");
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtils.d(TAG, "【MyReceiver】 接收到推送下来的通知: " + jsonString);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //打开自定义的Activity
            Intent i;
            if (SecurityHelper.getInstance().findUserData().getUser() != null) {
                i = new Intent(context, MainActivity.class);
            } else {
                i = new Intent(context, LoginActivity.class);
            }
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);

        }

    }
}
