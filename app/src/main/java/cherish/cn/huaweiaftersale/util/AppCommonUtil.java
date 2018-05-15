//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cherish.cn.huaweiaftersale.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class AppCommonUtil {
    private static float densityDpi;

    public AppCommonUtil() {
    }

    public static int px2dip(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)(var1 / var2 + 0.5F);
    }

    public static int dip2px(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)(var1 * var2 + 0.5F);
    }

    public static int px2sp(float var0, float var1) {
        return (int)(var0 / var1 + 0.5F);
    }

    public static float sp2px(Context var0, float var1) {
        return getRawSize(var0, 2, var1);
    }

    public static float getDensityDpi(Activity var0) {
        if(densityDpi == 0.0F) {
            DisplayMetrics var1 = new DisplayMetrics();
            var0.getWindowManager().getDefaultDisplay().getMetrics(var1);
            densityDpi = (float)var1.densityDpi;
        }

        return densityDpi;
    }

    public static int getScreenWidth(Context var0) {
        return var0.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHight(Context var0) {
        return var0.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getRawSize(Context var0, int var1, float var2) {
        Resources var3;
        if(var0 == null) {
            var3 = Resources.getSystem();
        } else {
            var3 = var0.getResources();
        }

        return TypedValue.applyDimension(var1, var2, var3.getDisplayMetrics());
    }

    public static Boolean isNetworkAvailable(Context var0) {
        ConnectivityManager var1 = (ConnectivityManager)var0.getSystemService("connectivity");
        if(var1 == null) {
            return Boolean.valueOf(false);
        } else {
            NetworkInfo[] var2 = var1.getAllNetworkInfo();
            if(var2 == null) {
                return Boolean.valueOf(false);
            } else {
                for(int var3 = 0; var3 < var2.length; ++var3) {
                    if(var2[var3].getState() == State.CONNECTED) {
                        return Boolean.valueOf(true);
                    }
                }

                return Boolean.valueOf(false);
            }
        }
    }
}
