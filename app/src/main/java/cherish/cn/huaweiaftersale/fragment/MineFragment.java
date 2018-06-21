package cherish.cn.huaweiaftersale.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cherish.cn.huaweiaftersale.LoginActivity;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.UpdatePsdActivity;
import cherish.cn.huaweiaftersale.WebViewActivity;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.bean.UserEntity;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.jpush.JPushHelper;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.SecurityHelper;
import cherish.cn.huaweiaftersale.util.SpfUtils;

public class MineFragment extends BaseFragment implements View.OnClickListener,DataCallback{
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.sign)
    TextView sign;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.customer)
    TextView customer;
    @BindView(R.id.finish)
    TextView finish;
    @BindView(R.id.times)
    TextView times;
    @BindView(R.id.reset)
    TextView reset;
    @Override
    protected void init(View view) {
        reset.setOnClickListener(this);
        logout.setOnClickListener(this);
        sign.setOnClickListener(this);
        UserEntity user = SecurityHelper.findUserData().getUser();
        Log.i("httpResponse",user.toString());
        customer.setText(user.getName());
        location.setText(user.getAddress());
        times.setText(user.getOvertimes()+"s");
        finish.setText(user.getOver()+"");
        refreshLayout.setEnableAutoLoadmore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.finishRefresh();
                        refreshLayout.resetNoMoreData();
                    }
                },2000);
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {

    }

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        if (funcKey == R.id.api_user_logout) {
            JPushHelper.stopPush();
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
//            SpfUtils.saveString(mContext, "loginCode", "");
//            SpfUtils.saveString(mContext, "password", "");
//            // 退出登录清除保存的登录数据
//            SpfUtils.saveString(getActivity(), "username", "");
//            SpfUtils.saveString(getActivity(), "password", "");
            mContext.finish();
        }else{//(funcKey == R.id.api_sign){
            androidToast("打卡成功");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                ApiHelper.load(mContext, R.id.api_user_logout, this);
                break;
            case R.id.sign:
                ApiHelper.load(mContext, R.id.api_sign, this);
                sign.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_general_button_vv_black));
                break;
            case R.id.reset:
                Intent intent=new Intent(mContext, UpdatePsdActivity.class);
                startActivity(intent);
                break;
            default:
                 break;
        }

    }
}
