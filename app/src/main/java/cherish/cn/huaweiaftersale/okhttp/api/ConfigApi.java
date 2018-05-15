package cherish.cn.huaweiaftersale.okhttp.api;

import android.content.Context;

import java.util.HashMap;

import cherish.cn.huaweiaftersale.okhttp.BaseApi;
import cherish.cn.huaweiaftersale.okhttp.entity.ConfigEntity;

public class ConfigApi extends BaseApi {

    public ConfigEntity getConfig(Context context) {
        // TODO Auto-generated method stub
        String url = "http://www.cloud-dahua.com:8088/gateway/auth/oauth/token";
        HashMap pars = new HashMap<String, String>();
        pars.put("grant_type", "client_credentials");
        pars.put("scope", "server");
        pars.put("client_id", "6d91e091-0527-4a0d-97b6-96e184670e79");
        pars.put("client_secret", "b57b0e1bf8f343b9996b37c9684ecc04");
        ConfigEntity result=super.post(context, ConfigEntity.class, url, pars, null);
        return result;
    }
}
