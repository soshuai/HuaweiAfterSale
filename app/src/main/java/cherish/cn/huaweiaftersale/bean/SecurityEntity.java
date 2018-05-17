package cherish.cn.huaweiaftersale.bean;


import com.alibaba.fastjson.annotation.JSONField;

import cherish.cn.huaweiaftersale.okhttp.utils.AESEncrypt;

/**
 * 标识类, 应用类所有数据实体都应继承自该类
 */
public class SecurityEntity extends BaseEntity {

    @JSONField
    protected String token;
    @JSONField
    protected String secretKey;

    private AESEncrypt mAesEncrypt;

    public SecurityEntity() {
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String mToken) {
        this.token = mToken;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String mSecretKey) {
        this.secretKey = mSecretKey;
    }

    public AESEncrypt getAesEncrypt() {
        return mAesEncrypt;
    }

    public void setAesEncrypt(AESEncrypt mAesEncrypt) {
        this.mAesEncrypt = mAesEncrypt;
    }
}
