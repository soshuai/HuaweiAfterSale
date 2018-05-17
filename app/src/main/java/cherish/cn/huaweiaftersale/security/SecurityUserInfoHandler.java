package cherish.cn.huaweiaftersale.security;

import android.content.Context;
import android.os.Bundle;

import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.util.AppException;

/**
 * Created by Veryxyf on 2016-5-17.
 */
public class SecurityUserInfoHandler extends BaseSecurityApiHandler {

    public SecurityUserInfoHandler(final Context context, DataCallback callback) {
        super(context, callback);
    }

    public void doGetUserInfo() throws AppException {
       // ApiHelper.load(mContext, R.id.api_user_info, mCallback);
    }

    @Override
    protected void doSuccess(int funcKey, Bundle bundle) {

    }
}
