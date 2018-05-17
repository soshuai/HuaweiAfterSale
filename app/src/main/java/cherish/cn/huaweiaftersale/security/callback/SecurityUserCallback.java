package cherish.cn.huaweiaftersale.security.callback;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import cherish.cn.huaweiaftersale.base.AppContext;
import cherish.cn.huaweiaftersale.base.SecurityHolder;
import cherish.cn.huaweiaftersale.bean.UserEntity;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.event.LoginEvent;
import cherish.cn.huaweiaftersale.okhttp.utils.AESEncrypt;
import cherish.cn.huaweiaftersale.security.model.LoginModel;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.LogUtils;
import cherish.cn.huaweiaftersale.util.SecurityHelper;
import cherish.cn.huaweiaftersale.util.SpfUtils;


/**
 * Created by Veryxyf on 2016-5-17.
 */
public class SecurityUserCallback implements DataCallback {

    private DataCallback mCallback;
    private AESEncrypt mAesEncrypt;
    private LoginModel mLoginParameter;

    public SecurityUserCallback(DataCallback callback, AESEncrypt aesEncrypt, LoginModel loginParameter) {
        if (aesEncrypt == null) {
            throw new IllegalArgumentException();
        }
        this.mCallback = callback;
        this.mAesEncrypt = aesEncrypt;
        this.mLoginParameter = loginParameter;
    }

    public SecurityUserCallback(DataCallback callback, LoginModel loginParameter) {
        this(callback, SecurityHolder.findSecurityData().getAesEncrypt(), loginParameter);
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {
        mCallback.onFailure(funcKey, bundle, appe);
        EventBus.getDefault().post(new LoginEvent(false));

        SpfUtils.saveString(AppContext.getInstance().getApplicationContext(), "loginCode", "");
        SpfUtils.saveString(AppContext.getInstance().getApplicationContext(), "password", "");
    }

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        String jsonData = (String) data;
        try {
            jsonData = mAesEncrypt.decryptorFromString(jsonData, "utf8");
          //  Toast.makeText(AppContext.getInstance(), jsonData, Toast.LENGTH_LONG).show();
            UserEntity user = JSON.parseObject(jsonData, UserEntity.class);
            SecurityHelper.findUserData().setUser(user);
            LogUtils.d("loginCallback", user.getId() + "");
            mCallback.onSuccess(funcKey, bundle, null);
            //在这里保存账号密码

            SpfUtils.saveString(AppContext.getInstance().getApplicationContext(), "username", mLoginParameter.getMobile());
            SpfUtils.saveString(AppContext.getInstance().getApplicationContext(), "password", mLoginParameter.getPassword());
            EventBus.getDefault().post(new LoginEvent(true));

        } catch (InvalidKeyException e) {
            mCallback.onFailure(funcKey, bundle, AppException.encrypt(e));
        } catch (IllegalBlockSizeException e) {
            mCallback.onFailure(funcKey, bundle, AppException.encrypt(e));
        } catch (BadPaddingException e) {
            mCallback.onFailure(funcKey, bundle, AppException.encrypt(e));
        } catch (IOException e) {
            mCallback.onFailure(funcKey, bundle, AppException.io(e));
        }
    }
}
