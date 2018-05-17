package cherish.cn.huaweiaftersale.okhttp.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import cherish.cn.huaweiaftersale.base.AppContext;
import cherish.cn.huaweiaftersale.bean.SecurityEntity;
import cherish.cn.huaweiaftersale.okhttp.BaseApi;
import cherish.cn.huaweiaftersale.okhttp.entity.BaseApiEntity;
import cherish.cn.huaweiaftersale.okhttp.entity.CheckLiveEntity;
import cherish.cn.huaweiaftersale.okhttp.entity.DepartListBean;
import cherish.cn.huaweiaftersale.okhttp.entity.LoginNVREntity;
import cherish.cn.huaweiaftersale.okhttp.entity.NVREntity;
import cherish.cn.huaweiaftersale.okhttp.entity.UserBeans;
import cherish.cn.huaweiaftersale.okhttp.entity.VideoCheckDictEntity;
import cherish.cn.huaweiaftersale.okhttp.utils.AESEncrypt;
import cherish.cn.huaweiaftersale.okhttp.utils.EncryptUtil;
import cherish.cn.huaweiaftersale.okhttp.utils.JsonUtils;
import cherish.cn.huaweiaftersale.okhttp.utils.RSAEncrypt;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.LogUtils;
import cherish.cn.huaweiaftersale.util.SecurityHelper;
import cherish.cn.huaweiaftersale.util.UploadUtil;

/**
 * 用户相关api合集
 *
 * @author Cz
 */
public class UserApi extends BaseApi {

    private static final String TAG = UserApi.class.getSimpleName();
    private static UserApi mInstance;
    private AESEncrypt mAesEncrypt;

    public static UserApi getInstance() {
        if (null == mInstance)
            mInstance = new UserApi();
        return mInstance;
    }

    private UserApi() {
        super();
    }

    public String  getToken(Context context) throws AppException {
        String url = hostUrl + "user/token?accessSign="+getAccessSign(context);
        String result=super.requestBase(context, "get",url,null,null, false);
        JSONObject obj = null;
        try {
            obj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String message = obj.optString("data");
        return message;
    }
    /**
     * 用户登录
     *
     * @param context
     * @param loginStr
     * @param pwdStr
     * @return
     */
    public LoginNVREntity doLogin(Context context, String loginStr, String pwdStr) throws AppException {
        String jsonData=getToken(context);
        String token = "";
        String sign = "";
        try {
            jsonData = mAesEncrypt.decryptorFromString(jsonData, "utf8");
            SecurityEntity securityData = JSON.parseObject(jsonData, SecurityEntity.class);
            securityData.setAesEncrypt(mAesEncrypt);
            token=securityData.getToken();
            sign=securityData.getSecretKey();
        } catch (InvalidKeyException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        } catch (IOException e) {
        }
        // 获取RSA公钥
        RSAEncrypt rsaEncrypt = new RSAEncrypt();
        InputStream is;
        try {
            is = context.getAssets().open("pubkey.pem");
            rsaEncrypt.loadPublicKey(is);
            is.close();
        } catch (IOException e) {
            return new LoginNVREntity(BaseApiEntity.ERROR_CODE_IO, "");
        } catch (Exception e) {
            return new LoginNVREntity(BaseApiEntity.ERROR_CODE_ENCRYPT, "");
        }

        // 发送登录请求
        try {
            JSONStringer jsonText = new JSONStringer();
            jsonText.object();
            jsonText.key("code");
            jsonText.value(loginStr);

            jsonText.key("password");
            jsonText.value(pwdStr);
            jsonText.endObject();

            byte[] cipher;
            try {
                cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), jsonText.toString().getBytes());
            } catch (Exception e) {
                throw AppException.encrypt(e);
            }
            String securityMsg = EncryptUtil.byteArr2HexStr(cipher);

            String loginUrl = null;
            try {
                loginUrl = hostUrl + "user/login?" + "loginInfo=" + securityMsg+"&token="+token+"&sign="+sign;
            } catch (Exception e) {
                e.printStackTrace();
            }

            String loginResult = requestBase(context, "get", loginUrl, null, null, false);
            JSONObject obj = new JSONObject(loginResult);
            boolean ok = obj.optBoolean("ok");
            String message = obj.optString("message");
            String respCode = obj.optString("respCode");
            JSONObject objData = obj.optJSONObject("data");
            JSONObject objUser = objData.optJSONObject("tSUser");
            String id = objUser.optString("id");
            String userName = objUser.optString("userName");
            boolean checkFlag = objUser.optBoolean("checkFlag");
            JSONArray jsonArray = objData.optJSONArray("tSDepartList");
            List<DepartListBean> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objList = jsonArray.getJSONObject(i);
                DepartListBean bean = new DepartListBean(objList.optString("id"), objList.optString("departname"), objList.optString("description")
                        , objList.optString("orgCode"), objList.optString("orgType"), objList.optString("mobile"), objList.optString("address"));
                list.add(bean);
            }
            NVREntity entity = new NVREntity();
            entity.settSDepartList(list);
            entity.settSUser(new UserBeans(id, userName, checkFlag));
//            return super.get(context, LoginNVREntity.class, loginUrl, null, null);
            return new LoginNVREntity(message, respCode, ok, entity);
        } catch (JSONException e) {
            return new LoginNVREntity(BaseApiEntity.ERROR_CODE_JSON, "");
        }
    }

    //现场检核 一级页面
    public CheckLiveEntity getLiveCheck(Context context, String userId, String deviceId) throws AppException {
        String url = hostUrl + "apiRestController/getLiveCheck.do";
        HashMap pars = new HashMap<String, String>();
        pars.put("userId", userId);
        pars.put("departId", deviceId);
        String result=super.requestBase(context, "get",url,pars,null, false);
        CheckLiveEntity entity= JsonUtils.parseGson(result,CheckLiveEntity.class);
        return entity;
    }
    //现场检核 二级页面
    public CheckLiveEntity getLiveCheckDetail(Context context, String id) throws AppException {
        String url = hostUrl + "apiRestController/getLiveCheckDetail.do";
        HashMap pars = new HashMap<String, String>();
        pars.put("id", id);
        String result=super.requestBase(context, "get",url,pars,null, false);
        CheckLiveEntity entity=JsonUtils.parseGson(result,CheckLiveEntity.class);
        return entity;
    }
    //现场检核 检核提交页面
    public VideoCheckDictEntity getLiveCheckDict(Context context) throws AppException {
        String url = hostUrl + "apiRestController/getLiveCheckDict.do";
        String result=super.requestBase(context, "get",url,null,null, false);
        VideoCheckDictEntity entity=JsonUtils.parseGson(result,VideoCheckDictEntity.class);
        return entity;
    }

    //现场检核 提交
    public VideoCheckDictEntity submitLiveCheck(Context context, Map<String, String> params, Map<String, File> files) throws AppException {
        String url = hostUrl + "apiRestController/submitLiveCheck.do";
        String result = "";
        VideoCheckDictEntity entity = null;
        Class<VideoCheckDictEntity> entityClass = VideoCheckDictEntity.class;
        try {
            result = UploadUtil.post(url, params, files);
            if (TextUtils.isEmpty(result)) {
                entity = entityClass.getDeclaredConstructor(Integer.class, String.class).newInstance(
                        BaseApiEntity.ERROR_CODE_NET, "网络异常");
            } else {
                entity = JsonUtils.parseObject(result, entityClass);
                if (null == entity) {
                    entity = entityClass.getDeclaredConstructor(Integer.class, String.class).newInstance(
                            BaseApiEntity.ERROR_CODE_JSON, "json格式不正确");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                entity = entityClass.getDeclaredConstructor(Integer.class, String.class).newInstance(
                        BaseApiEntity.ERROR_CODE_UNKOWN, "未知异常");
            } catch (Exception e1) {
            }
        }
        Log.i("http url", url);
        Log.i("http result", result);
        return entity;
    }


    /**
     * 修改登出方法，不需要知道服务端的相应结果，只要通知服务端即可， timeout时间设置的很短，避免app退出时卡住
     *
     * @throws AppException
     */
//    public void logout(Context context) throws AppException {
//        try {
//            URL url = new URL(hostUrl + "user/logout.action?token=" + AppContext.getInstance().getToken());
//            HttpURLConnection ret = (HttpURLConnection) url.openConnection();
//            ret.setConnectTimeout(3 * 1000);
//            ret.setReadTimeout(1 * 1000);
//            InputStream is = ret.getInputStream();
//            is.close();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            LogUtils.e("logout error", e.getLocalizedMessage());
//        }
//    }


    protected final String getAccessSign(Context mContext) throws AppException {
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
        String mAesKey = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
        mAesEncrypt = new AESEncrypt(mAesKey);
        byte[] cipher;
        try {
            cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), mAesKey.getBytes());
        } catch (Exception e) {
            throw AppException.encrypt(e);
        }
        String mAccessSign = EncryptUtil.byteArr2HexStr(cipher);
        LogUtils.d("securityApi", mAccessSign);
        return mAccessSign;
    }

//    protected static String createSign(Map<String, String> paras){
//        Map<String, Object> tempParas = new TreeMap<String, Object>(paras);
//
//        StringBuilder sb = new StringBuilder();
//        int index = 0;
//        for(Map.Entry<String, Object> entry : tempParas.entrySet()){
//            if (index > 0)
//                sb.append("&");
//            sb.append(entry.getKey()).append("=").append(entry.getValue());
//            index ++;
//        }
//        if (index > 0)
//            sb.append("&");
//        sb.append("key").append("=").append(secretKey);
//        System.out.println("queryString:" + sb.toString());
//
//        return MD5.calcMD5(sb.toString()).toUpperCase();
//    }
}
