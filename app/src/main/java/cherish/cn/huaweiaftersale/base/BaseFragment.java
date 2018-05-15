package cherish.cn.huaweiaftersale.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import cherish.cn.huaweiaftersale.util.ToastHolder;

/**
 * fragment基类，app内所有fragment都应继承自本类
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG;
    protected BaseActivity mContext;
    protected String mPageName;

    protected boolean isChoosed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mContext = (BaseActivity) getActivity();

        Bundle pars = super.getArguments();
        try {
            mPageName = pars.getString("mPageName");
        } catch (Exception e) {
        }
    }

    // 选中当前界面
    public void onChoose() {
        isChoosed = true;
    }

    public void onUnchoose() {
        isChoosed = false;
    }

    protected void androidToast(String toastMsg) {
        androidToast(toastMsg, Toast.LENGTH_SHORT);
    }

    protected void androidToast(int res) {
        androidToast(res, Toast.LENGTH_SHORT);
    }

    protected void androidToast(int res, int duration) {
        ToastHolder.showToast(mContext, res, duration);
    }

    protected void androidToast(String toastMsg, int duration) {
        ToastHolder.showToast(mContext, toastMsg, duration);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!TextUtils.isEmpty(mPageName)) {
//            MobclickAgent.onPageStart(mPageName);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (!TextUtils.isEmpty(mPageName)) {
//            MobclickAgent.onPageEnd(mPageName);
//        }
    }
}
