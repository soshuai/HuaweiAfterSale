package cherish.cn.huaweiaftersale.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.okhttp.AppConfig;
import cherish.cn.huaweiaftersale.okhttp.BaseApi;
import cherish.cn.huaweiaftersale.util.DynamicTimeFormat;

/**
 * 全局应用程序类：用于数据初始化
 */
public final class AppContext extends Application {

    private static AppContext mApp;
    public static String TOKEN;
    public static boolean success=false;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        // 配置信息初始化
        AppConfig.getAppConfig(this);
        // api 初始化
        BaseApi.initialize(this);

        //拍照需要
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        Fresco.initialize(mApp);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.bg_gray, R.color.refreshLayout_title);//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
    }
    public static AppContext getInstance(){
        return mApp;
    }

}
