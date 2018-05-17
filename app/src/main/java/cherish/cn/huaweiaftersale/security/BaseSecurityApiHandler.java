package cherish.cn.huaweiaftersale.security;

import android.content.Context;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.net.HexTransfer;
import cherish.cn.huaweiaftersale.okhttp.utils.AESEncrypt;
import cherish.cn.huaweiaftersale.okhttp.utils.RSAEncrypt;
import cherish.cn.huaweiaftersale.security.callback.SecurityTokenCallback;
import cherish.cn.huaweiaftersale.security.model.LoginModel;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.LogUtils;


/**
 * Created by Veryxyf on 2016-5-17.
 */
public abstract class BaseSecurityApiHandler implements DataCallback {

    protected Context mContext;
    protected DataCallback mCallback;
    protected String mAesKey;
    protected String mAccessSign;
    protected AESEncrypt mAesEncrypt;

    private LoginModel mLoginParameter;

    protected BaseSecurityApiHandler(final Context context, DataCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    protected final void getToken(DataCallback callback) {
        // 上传AES秘钥,获取token
        Bundle pars = new Bundle();
        pars.putString("accessSign", this.mAccessSign);
        ApiHelper.load(mContext, R.id.api_user_token, pars, new SecurityTokenCallback(callback, mAesEncrypt));
    }

    protected final void getAccessSign() throws AppException {
        RSAEncrypt rsaEncrypt = new RSAEncrypt();
        InputStream is;
        try {
            is = mContext.getAssets().open("pubkey.pem");
            rsaEncrypt.loadPublicKey(is);
            is.close();
        } catch (IOException e) {
            throw AppException.io(e);
        } catch (Exception e) {
            throw AppException.encrypt(e);
        }
        // 随机生成AES秘钥，并用RSA公钥加密
        this.mAesKey = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
        mAesEncrypt = new AESEncrypt(mAesKey);
        byte[] cipher;
        try {
            cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), mAesKey.getBytes());
        } catch (Exception e) {
            throw AppException.encrypt(e);
        }
        this.mAccessSign = HexTransfer.byteArr2HexStr(cipher);
        LogUtils.d("securityApi", this.mAccessSign);
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {
        mCallback.onFailure(funcKey, bundle, appe);
    }

    protected abstract void doSuccess(int funcKey, Bundle bundle);

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        doSuccess(funcKey, bundle);
    }
}
