package cherish.cn.huaweiaftersale.net.callback;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.net.ErrorHandler;

public class JsonArrayCallback extends BaseJsonCallback {

    public JsonArrayCallback(Class templateClass, int funcKey, Bundle pars, DataCallback dataCallback, ErrorHandler errorHandler) {
        super(templateClass, funcKey, pars, dataCallback, errorHandler);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Object parseRealData(String jsonResponse, JSONObject jsonObj) {
        // TODO Auto-generated method stub
        return JSON.parseArray(jsonObj.getString("dataList"), mTemplateClass);
    }

}
