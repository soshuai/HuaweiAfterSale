package cherish.cn.huaweiaftersale.okhttp.api;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import cherish.cn.huaweiaftersale.okhttp.BaseApi;
import cherish.cn.huaweiaftersale.okhttp.entity.ApplyRecordEntity;
import cherish.cn.huaweiaftersale.okhttp.entity.DefaultApiEntity;
import cherish.cn.huaweiaftersale.okhttp.entity.TokenEntity;
import cherish.cn.huaweiaftersale.okhttp.utils.AESEncrypt;
import cherish.cn.huaweiaftersale.okhttp.utils.EncryptUtil;
import cherish.cn.huaweiaftersale.okhttp.utils.RSAEncrypt;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.LogUtils;

/**
 * 用户相关api合集
 * 
 * @author Cz
 */
public class CaseApi extends BaseApi {

    private static final String TAG = CaseApi.class.getSimpleName();
    private static CaseApi      mInstance;

    public static CaseApi getInstance() {
        if (null == mInstance)
            mInstance = new CaseApi();
        return mInstance;
    }

    private CaseApi() {
        super();
    }

    public DefaultApiEntity updatePlandate(Context context, int caseId, String dateString) {
        // TODO Auto-generated method stub
        String url = hostUrl + "case/updateCasePlanDate.action";
        HashMap pars = new HashMap<String, String>();
////        pars.put("token", AppContext.getInstance().getToken());
        pars.put("caseId", caseId);
        pars.put("planDate", dateString);
        LogUtils.d(TAG, "updatePlandate.pars=" + pars);
        return this.get(context, DefaultApiEntity.class, url, pars, null);
    }

    public DefaultApiEntity updatePlanperson(Context context, int caseId, String ids) {
        // TODO Auto-generated method stub
        String url = hostUrl + "case/updateCasePlanExecutors.action";
        HashMap pars = new HashMap<String, String>();
// //       pars.put("token", AppContext.getInstance().getToken());
        pars.put("caseId", caseId);
        pars.put("executorIds", ids);
        LogUtils.d(TAG, "updatePlanperson.pars=" + pars);
        return this.get(context, DefaultApiEntity.class, url, pars, null);
    }

    /**
     * 获取七牛token
     */
    public String getQiniuToken(Context context, final AssetManager manager) throws AppException {
        // 获取RSA公钥
        RSAEncrypt rsaEncrypt = new RSAEncrypt();
        InputStream is;
        try {
            is = manager.open("pubkey.pem");
            rsaEncrypt.loadPublicKey(is);
            is.close();
        } catch (IOException e) {
            throw AppException.io(e);
        } catch (Exception e) {
            throw AppException.encrypt(e);
        }

        // 随机生成AES秘钥，并用RSA公钥加密
        String aesKey = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
        byte[] cipher;
        try {
            cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), aesKey.getBytes());
        } catch (Exception e) {
            throw AppException.encrypt(e);
        }
        String securityMsg = EncryptUtil.byteArr2HexStr(cipher);

        // 上传AES秘钥,获取token
        String url = hostUrl + "user/getQiniuToken.action?accessSign=" + securityMsg;

        TokenEntity entity = this.get(context, TokenEntity.class, url);
        AESEncrypt aesEncrypt = new AESEncrypt(aesKey);
        String currentQiniuToken;
        try {
            currentQiniuToken = aesEncrypt.decryptorFromString(entity.getToken(), "utf8");
            return currentQiniuToken;
        } catch (InvalidKeyException e) {
            throw AppException.encrypt(e);
        } catch (IllegalBlockSizeException e) {
            throw AppException.encrypt(e);
        } catch (BadPaddingException e) {
            throw AppException.encrypt(e);
        } catch (IOException e) {
            throw AppException.io(e);
        }
    }

    public ApplyRecordEntity record(Context context, int caseId, int recordType, String beginTime, double longitude,
                                    double latitude, String executedName, int measureId, int subMeasureId) throws AppException {
        // TODO Auto-generated method stub
        String url = hostUrl + "case/record.action";
        HashMap pars = new HashMap<String, String>();
//        pars.put("token", AppContext.getInstance().getToken());
        pars.put("caseId", caseId);
        pars.put("recordType", recordType);
        pars.put("beginTime", beginTime);
        pars.put("longitude", longitude);
        pars.put("latitude", latitude);
        pars.put("executedName", executedName);
        pars.put("measureId", measureId);
        if (recordType==5){
//            pars.put("channelId", AppContext.getChannelId());
        }else{
            pars.put("channelId", "");
        }
        if (subMeasureId != 0) {
            pars.put("subMeasureId", subMeasureId);
        } else {
            pars.put("subMeasureId", "");
        }
        LogUtils.d(TAG, "record.pars=" + pars);
        ApplyRecordEntity apiResult = this.post(context, ApplyRecordEntity.class, url, pars, null);
        apiResult.checkServerResult();
        return apiResult;
    }

    public DefaultApiEntity deleteRecord(Context context, int recordId) {
        // TODO Auto-generated method stub
        String url = hostUrl + "case/deleteRecord.action";
        HashMap pars = new HashMap<String, String>();
//        pars.put("token", AppContext.getInstance().getToken());
        pars.put("recordId", recordId);
        LogUtils.d(TAG, "deleteRecord.pars=" + pars);
//        Log.i("ssss","token=="+ AppContext.getInstance().getToken()+" recordId=="+recordId);
        return this.get(context, DefaultApiEntity.class, url, pars, null);
    }

    public void endRecord(Context context, int recordId) {
        // TODO Auto-generated method stub
        String url = hostUrl + "case/endRecord.action";
        HashMap pars = new HashMap<String, String>();
//        pars.put("token", AppContext.getInstance().getToken());
        pars.put("recordId", recordId);
        LogUtils.d(TAG, "endRecord.pars=" + pars);
        this.get(context, DefaultApiEntity.class, url, pars, null);
    }

    public void stopPublish(Context context, String channelId, int publishFlag) {
        // TODO Auto-generated method stub
        String url = hostUrl + "case/endPublish.action";
        HashMap pars = new HashMap<String, String>();
        pars.put("channelId", channelId);
        pars.put("publishFlag", publishFlag);
//        LogUtils.d(TAG, "stopPublish.pars=" + pars);
        this.get(context, DefaultApiEntity.class, url, pars, null);
    }


    public void submitLocation(Context context, int recordId, double longitude, double latitude) {
        // TODO Auto-generated method stub
        String url = hostUrl + "case/submitPosition.action";
        HashMap pars = new HashMap<String, String>();
//        pars.put("token", AppContext.getInstance().getToken());
        pars.put("recordId", recordId);
        pars.put("longitude", longitude);
        pars.put("latitude", latitude);
        LogUtils.d(TAG, "submitPosition.pars=" + pars);
        this.get(context, DefaultApiEntity.class, url, pars, null);
    }

}
