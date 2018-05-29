package cherish.cn.huaweiaftersale;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.BaseActivity;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.SpfUtils;
import cherish.cn.huaweiaftersale.util.StringUtils;

public class UpdatePsdActivity extends BaseActivity implements View.OnClickListener,DataCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_custom_title)
    TextView title;
    @BindView(R.id.tv_psd_manager_before)
    EditText mTvOldPsd;
    @BindView(R.id.et_psd_manager_new)
    EditText mEtNewPsd;
    @BindView(R.id.et_psd_manager_sure)
    EditText mEtSurePsd;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        title.setText("修改密码");
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_update_psd;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                String newPsd = mEtNewPsd.getText().toString();
                String surePsd = mEtSurePsd.getText().toString();
                if (mTvOldPsd.getText().toString().equals(SpfUtils.getString(mContext, "password"))) {
                    if (TextUtils.isEmpty(newPsd)) {
                        androidToast("新密码不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(surePsd)) {
                        androidToast("确认密码不能为空");
                        return;
                    }
                    if (!StringUtils.isPassword(newPsd)) {
                        androidToast("请输入正确格式密码，支持6-20位数字或字母");
                        return;
                    }
                    if (!TextUtils.equals(newPsd, surePsd)) {
                        androidToast("两次密码输入不一致");
                        return;
                    }
//                    JSONObject json = new JSONObject();
//                    json.put("oldpwd", SpfUtils.getString(mContext, "password"));
//                    json.put("newpwd", newPsd);
                    Bundle param = new Bundle();
//                    param.putString("password", json.toString());
                    param.putString("oldpwd", SpfUtils.getString(mContext, "password"));
                    param.putString("newpwd", newPsd);
                    ApiHelper.load(mContext, R.id.api_update_psd, param, this);
                } else {
                    Log.i("AAAA",SpfUtils.getString(mContext, "password"));
                    androidToast("密码不正确");
                }

                break;
        }
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {


    }

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        if (funcKey==R.id.api_update_psd){
            androidToast("密码修改成功");
            SpfUtils.saveString(mContext,"password",mEtNewPsd.getText().toString());
            finish();
        }
    }
}
