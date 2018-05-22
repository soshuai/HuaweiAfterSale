package cherish.cn.huaweiaftersale.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cherish.cn.huaweiaftersale.util.ToastHolder;

/**
 * fragment基类，app内所有fragment都应继承自本类
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG;
    protected BaseActivity mContext;
    protected String mPageName;

    protected boolean isChoosed = false;
    private Unbinder unBinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        unBinder = ButterKnife.bind(this, view);
        init(view);
        return view;
    }

    protected abstract void init(View view);
    protected abstract int getLayoutId();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null)
            unBinder.unbind();
    }


}
