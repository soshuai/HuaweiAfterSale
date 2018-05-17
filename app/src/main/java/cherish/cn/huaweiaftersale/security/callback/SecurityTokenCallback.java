package cherish.cn.huaweiaftersale.security.callback;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import cherish.cn.huaweiaftersale.bean.SecurityEntity;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.okhttp.utils.AESEncrypt;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.SecurityHelper;


/**
 * Created by Veryxyf on 2016-5-17.
 */
public class SecurityTokenCallback implements DataCallback {

    private DataCallback mCallback;
    private AESEncrypt mAesEncrypt;

    public SecurityTokenCallback(DataCallback callback, AESEncrypt aesEncrypt){
        this.mCallback = callback;
        this.mAesEncrypt = aesEncrypt;
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {
        mCallback.onFailure(funcKey, bundle, appe);
    }

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        String jsonData = (String)data;
        try {
            jsonData = mAesEncrypt.decryptorFromString(jsonData, "utf8");
            SecurityEntity securityData = JSON.parseObject(jsonData, SecurityEntity.class);
            securityData.setAesEncrypt(mAesEncrypt);
            SecurityHelper.saveSecurityData(securityData);

            mCallback.onSuccess(funcKey, bundle, null);
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
