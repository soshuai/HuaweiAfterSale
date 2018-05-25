package cherish.cn.huaweiaftersale.net.callback;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.DataManager;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.net.ErrorHandler;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.LogUtils;

public abstract class BaseJsonCallback implements Callback {
    protected Class mTemplateClass;
    protected DataCallback mDataCallback;
    protected int mFuncKey;
    protected Bundle mPars;
    protected ErrorHandler errorHandler;

    public BaseJsonCallback(Class templateClass, int funcKey, Bundle pars, DataCallback dataCallback, ErrorHandler errorHandler) {
        // TODO Auto-generated constructor stub
        if (templateClass == null) {
            throw new IllegalArgumentException();
        }
        this.mTemplateClass = templateClass;
        this.mFuncKey = funcKey;
        this.mPars = pars;
        this.mDataCallback = dataCallback;
        this.errorHandler = errorHandler;

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HttpSuccessEvent event) {
        if (event.getSenderHashCode() == this.hashCode() && event.getFuncKey() == mFuncKey) {
            unregisterMe();
            if (mDataCallback==null){
                Log.i("AAAA",event.getFuncKey()+" "+ event.getPars()+event.getData()+"");
            }
               mDataCallback.onSuccess(event.getFuncKey(), event.getPars(), event.getData());
            mDataCallback = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HttpFailureEvent event) {
        if (event.getSenderHashCode() == this.hashCode() && event.getFuncKey() == mFuncKey) {
            unregisterMe();
            if (errorHandler != null) {
                errorHandler.handleError(event.getFuncKey(), event.getResult(), event.getFailedMsg());
            }

               mDataCallback.onFailure(event.getFuncKey(), event.getPars(), event.getAppe());
            mDataCallback = null;
        }
    }

    @Override
    public void onFailure(Request request, IOException e) {
        // TODO Auto-generated method stub
        DataManager.getInstance().dataEnd(mFuncKey);
        EventBus.getDefault().post(new HttpFailureEvent(this.hashCode(), mFuncKey, mPars, AppException.io(e), Integer.MAX_VALUE, "网络连接异常"));
    }


    @Override
    public final void onResponse(Response response) throws IOException {
        // TODO Auto-generated method stub
        DataManager.getInstance().dataEnd(mFuncKey);
//        if (mFuncKey== R.id.api_order_list){
//            Log.i("ww","");
//        }
        int responseCode = response.code();
        if (responseCode == 200) {
            String jsonResponse = response.body().string();

            LogUtils.e("httpResponse", jsonResponse);//veryw
            if (TextUtils.isEmpty(jsonResponse)) {
                EventBus.getDefault().post(new HttpFailureEvent(this.hashCode(), mFuncKey, mPars, AppException.http(null), -1, "请检查您的网络连接"));
                return;
            }

            JSONObject jsonObj = JSON.parseObject(jsonResponse);
            if (jsonObj == null) {
                EventBus.getDefault().post(new HttpFailureEvent(this.hashCode(), mFuncKey, mPars, AppException.json(null), -1, "数据解析失败"));
                return;
            }

            doParse(jsonObj, jsonResponse);
        } else {
            EventBus.getDefault().post(new HttpFailureEvent(this.hashCode(), mFuncKey, mPars, AppException.server(responseCode), responseCode, "服务器出错啦"));
        }
    }

    protected void doParse(JSONObject jsonObj, String jsonResponse) {
        try {
            int result = jsonObj.getIntValue("result");
            if (result == 0) {
                parseSuccess(parseRealData(jsonResponse, jsonObj));
            } else {
                parseFailure(result, jsonObj.getString("message"));
            }
        } catch (Exception e) {
            EventBus.getDefault().post(new HttpFailureEvent(this.hashCode(), mFuncKey, mPars, AppException.json(null), -1, "数据解析失败"));
            return;
        }
    }

    protected void parseSuccess(Object obj) {
        EventBus.getDefault().post(new HttpSuccessEvent(this.hashCode(), mFuncKey, mPars, obj));
    }

    protected void parseFailure(int result, String message) {
        EventBus.getDefault().post(new HttpFailureEvent(this.hashCode(), mFuncKey, mPars, AppException.server(result), result, message));
    }


    protected abstract Object parseRealData(String jsonResponse, JSONObject jsonObj);

    protected abstract class HttpProgressEvent {
        protected int funcKey;
        protected Bundle pars;
        private int senderHashCode;

        protected HttpProgressEvent(int senderHashCode, int funcKey, Bundle pars) {
            // TODO Auto-generated constructor stub
            this.funcKey = funcKey;
            this.pars = pars;
            this.senderHashCode = senderHashCode;
        }

        public int getSenderHashCode() {
            return senderHashCode;
        }

        public int getFuncKey() {
            return funcKey;
        }

        public void setFuncKey(int funcKey) {
            this.funcKey = funcKey;
        }

        public Bundle getPars() {
            return pars;
        }

        public void setPars(Bundle pars) {
            this.pars = pars;
        }
    }

    private class HttpSuccessEvent extends HttpProgressEvent {
        private Object data;

        private HttpSuccessEvent(int senderHashCode, int funcKey, Bundle pars, Object data) {
            // TODO Auto-generated constructor stub
            super(senderHashCode, funcKey, pars);
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

    }

    public class HttpFailureEvent extends HttpProgressEvent {

        private AppException appe;
        private int result;
        private String failedMsg;

        private HttpFailureEvent(int senderHashCode, int funcKey, Bundle pars, AppException appe, int result, String failedMsg) {
            // TODO Auto-generated constructor stub
            super(senderHashCode, funcKey, pars);
            this.appe = appe;
            this.result = result;
            this.failedMsg = failedMsg;
        }

        public int getResult() {
            return result;
        }

        public String getFailedMsg() {
            return failedMsg;
        }

        public AppException getAppe() {
            return appe;
        }

        public void setAppe(AppException appe) {
            this.appe = appe;
        }

    }

    private void unregisterMe() {
        // TODO Auto-generated method stub
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void release() {
        unregisterMe();
        mDataCallback = null;
    }
}
