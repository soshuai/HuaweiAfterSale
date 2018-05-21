package cherish.cn.huaweiaftersale.util;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.AppContext;
import cherish.cn.huaweiaftersale.base.SecurityHolder;
import cherish.cn.huaweiaftersale.bean.SecurityEntity;
import cherish.cn.huaweiaftersale.bean.SecurityUserEntity;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.event.LogoutEvent;
import cherish.cn.huaweiaftersale.security.SecurityLoginHandler;
import cherish.cn.huaweiaftersale.security.model.LoginModel;

/**
 * Created by Veryxyf on 2016-5-17.
 */
public final class SecurityHelper {

    private static SecurityHelper sInstance;
    private SecurityUserEntity userData;


    private SecurityHelper() {
        super();
    }

    public static synchronized SecurityHelper getInstance() {
        if (sInstance == null) {
            sInstance = new SecurityHelper();
        }
        return sInstance;
    }

    public static void saveSecurityData(SecurityEntity securityData) {
        SecurityHolder.saveSecurityData(securityData);
    }

    public static synchronized SecurityUserEntity findUserData() {
        return SecurityHelper.getInstance().getUserData();
    }

    public static synchronized boolean isLogin() {
        SecurityUserEntity user = SecurityHelper.getInstance().getUserData();
        return (user != null && user.getUser() != null && user.getUser().getId() > 0);
    }

//    public static void registerVCode(final Context context, final DataCallback callback, String mobile) {
//        try {
//            new SecurityVCodeHandler(context, callback).doGetVCode(mobile);
//        } catch (AppException appe) {
//        }
//    }

//    public static void resetPasswordVCode(final Context context, final DataCallback callback, String mobile) {
//        try {
//            new SecurityResetPswdHandler(context, callback).doGetVCode(mobile);
//        } catch (AppException appe) {
//        }
//    }


    public static void login(final Context context, final DataCallback callback, LoginModel model) {
        try {
            new SecurityLoginHandler(context, callback).doLogin(model);
        } catch (AppException appe) {
        }
    }


    public static synchronized void logtimeout() {
        if (sInstance != null) {
            sInstance.userData = null;
            EventBus.getDefault().post(new LogoutEvent(false));
        }
    }

    public static synchronized void logout() {
        if (sInstance != null) {
            sInstance.userData = null;
            SpfUtils.saveString(AppContext.getInstance().getApplicationContext(), "loginCode", "");
            SpfUtils.saveString(AppContext.getInstance().getApplicationContext(), "password", "");
            EventBus.getDefault().post(new LogoutEvent(true));
            ApiHelper.load(AppContext.getInstance().getApplicationContext(), R.id.api_user_logout, null);
        }
    }

    private SecurityUserEntity getUserData() {
        if (userData == null) {
            userData = new SecurityUserEntity();
        }
        return userData;
    }


}

