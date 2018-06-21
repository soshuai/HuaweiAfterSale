package cherish.cn.huaweiaftersale;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cherish.cn.huaweiaftersale.adapter.LoginMoreAdapter;
import cherish.cn.huaweiaftersale.base.BaseActivity;
import cherish.cn.huaweiaftersale.bean.LoginMoreBean;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.security.model.LoginModel;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.LoadingDialogUtils;
import cherish.cn.huaweiaftersale.util.SecurityHelper;
import cherish.cn.huaweiaftersale.util.StringUtils;

import static cherish.cn.huaweiaftersale.R.id.api_user_login;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginMoreAdapter.LoginItemClick, DataCallback {

    @BindView(R.id.login)
    Button login;
    @BindView(R.id.more_list)
    ImageView morelist;
    @BindView(R.id.more_listview)
    ListView listView;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_relative)
    RelativeLayout passwordrl;
    private List<LoginMoreBean> list = new ArrayList<>();
    final static int PERMISSIONS_REQUEST_CODE = 12;
    private boolean isSave = true;//是否保存账户密码
    private LoginMoreAdapter adapter;
//    private Handler mhandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            }
        }
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    private void init() {
//        mhandler=new NoLeakHandler();
//        HandlerThread thread=new HandlerThread("thread");
//        thread.start();
//        mhandler=new Handler(thread.getLooper()){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };
        morelist.setOnClickListener(this);
        login.setOnClickListener(this);
        list.add(new LoginMoreBean("13122626056", "123456"));
        list.add(new LoginMoreBean("15555714326", "123456"));
        list.add(new LoginMoreBean("13855714336", "123456"));
        adapter = new LoginMoreAdapter(mContext, this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                doLogin();
                break;
            case R.id.more_list:
                if (listView.getVisibility() == View.GONE) {
                    listView.setVisibility(View.VISIBLE);
                    passwordrl.setVisibility(View.GONE);
                    login.setVisibility(View.GONE);
                } else {
                    listView.setVisibility(View.GONE);
                    passwordrl.setVisibility(View.VISIBLE);
                    login.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private void doLogin() {
        String loginCode = account.getText().toString();
        String pwd = password.getText().toString();
        if (TextUtils.isEmpty(loginCode)) {
            androidToast("手机号不能为空");
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            androidToast("密码不能为空");
            return;
        } else if (!StringUtils.isMobile(loginCode)) {
            androidToast("手机号码格式不正确");
        } else {
            LoadingDialogUtils.createLoadingDialog(mContext, "正在登陆...");
            SecurityHelper.login(mContext, this, new LoginModel(loginCode, pwd));
        }
//        if (!(StringUtils.isEmpty(loginCode) || StringUtils.isEmpty(pwd))) {
//            LoadingDialogUtils.createLoadingDialog(mContext, "正在登陆...");
//            SecurityHelper.login(mContext, this, new LoginModel(loginCode, pwd));
//        } else {
//            androidToast("用户名和密码不能为空");
//        }
    }

    @Override
    public void onItemClick(int position) {
        listView.setVisibility(View.GONE);
        account.setText(list.get(position).getName());
        password.setText(list.get(position).getPassword());
        passwordrl.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {
        LoadingDialogUtils.closeDialog();
    }

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        if (funcKey == api_user_login) {
            androidToast("登录成功");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            LoadingDialogUtils.closeDialog();
            finish();
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mhandler.removeCallbacksAndMessages(null);
//    }

    private static class NoLeakHandler extends Handler{
        public NoLeakHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}

