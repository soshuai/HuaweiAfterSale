package cherish.cn.huaweiaftersale.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import cherish.cn.huaweiaftersale.R;

public class BaseActivity extends AppCompatActivity {

    // 日志打印tag
    protected String TAG;
    // 页面Context
    protected String mPageName;
    protected BaseActivity mContext;
    protected AppContext        mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        TAG = this.getClass().getSimpleName();
        // 添加Activity到堆栈
        AppManager.getInstance().addActivity(this);
        mContext = this;
        mApp = AppContext.getInstance();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }

    public void androidToast(String ss) {
        Toast.makeText(this, "" + ss, Toast.LENGTH_SHORT).show();
    }

}
