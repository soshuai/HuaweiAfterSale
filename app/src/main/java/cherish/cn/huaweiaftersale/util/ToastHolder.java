package cherish.cn.huaweiaftersale.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 全局应用程序类：用于数据初始化
 */
public final class ToastHolder {

    private static Toast sToast;

    /**
     * 显示提示，同一时间只能显示一个
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, String text, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, text, duration);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    /**
     * 显示提示
     * @param context
     * @param res
     * @param duration
     */
    public static void showToast(Context context, int res, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, res, duration);
        } else {
            sToast.setText(res);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

}
