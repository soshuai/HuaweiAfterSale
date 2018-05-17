package cherish.cn.huaweiaftersale.security;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.security.callback.SecurityUserCallback;
import cherish.cn.huaweiaftersale.security.model.LoginModel;
import cherish.cn.huaweiaftersale.util.AppException;


/**
 * Created by Veryxyf on 2016-5-17.
 */
public class SecurityLoginHandler extends BaseSecurityApiHandler {

    private LoginModel mLoginModel;

    public SecurityLoginHandler(final Context context, DataCallback callback) {
        super(context, callback);
    }

    public void doLogin(LoginModel loginModel) throws AppException {
        getAccessSign();
        getToken(this);
        mLoginModel = loginModel;
    }

    @Override
    protected void doSuccess(int funcKey, Bundle bundle) {
        Bundle pars = new Bundle();
        pars.putString("loginInfo", JSON.toJSONString(mLoginModel));
        ApiHelper.load(mContext, R.id.api_user_login, pars, new SecurityUserCallback(this.mCallback, this.mAesEncrypt, mLoginModel));
    }
}
