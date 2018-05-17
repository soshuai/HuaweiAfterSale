package cherish.cn.huaweiaftersale.net.callback;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.net.ErrorHandler;

public class JsonObjectCallback extends BaseJsonCallback {

    public JsonObjectCallback(Class templateClass, int funcKey, Bundle pars, DataCallback dataCallback, ErrorHandler errorHandler) {
        super(templateClass, funcKey, pars, dataCallback, errorHandler);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Object parseRealData(String jsonResponse, JSONObject jsonObj) {
        // TODO Auto-generated method stub
        String dataJson = jsonObj.getString("data");
        if (dataJson == null) {
            return null;
        }
        if (TextUtils.isEmpty(dataJson)) {
            return JSON.parseObject(jsonResponse, mTemplateClass);
        }
        if (mTemplateClass.equals(String.class)) {
            return dataJson;
        }
        return JSON.parseObject(dataJson, mTemplateClass);
    }

}
