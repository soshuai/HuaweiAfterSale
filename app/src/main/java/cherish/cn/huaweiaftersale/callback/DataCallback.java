package cherish.cn.huaweiaftersale.callback;

import android.os.Bundle;

import cherish.cn.huaweiaftersale.util.AppException;


public interface DataCallback {

    public void onFailure(int funcKey, Bundle bundle, AppException appe);

    public void onSuccess(int funcKey, Bundle bundle, Object data);

}
