package cherish.cn.huaweiaftersale;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE);
            }
        }
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    private void init() {
        morelist.setOnClickListener(this);
        login.setOnClickListener(this);
        list.add(new LoginMoreBean("13122626056", "123456"));
        list.add(new LoginMoreBean("安妮海瑟薇", "123"));
        list.add(new LoginMoreBean("15555714326", "123456"));
        list.add(new LoginMoreBean("尼尼", "123"));
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

        if (!(StringUtils.isEmpty(loginCode) || StringUtils.isEmpty(pwd))) {
            LoadingDialogUtils.createLoadingDialog(mContext, "正在登陆...");
            SecurityHelper.login(mContext, this, new LoginModel(loginCode, pwd));
        } else {
            androidToast("用户名和密码不能为空");
        }
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
}

