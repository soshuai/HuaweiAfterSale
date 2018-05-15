package cherish.cn.huaweiaftersale.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import de.greenrobot.event.EventBus;

public class MineFragment extends BaseFragment {
    private Unbinder unBinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
//        EventBus.getDefault().register(this);
        unBinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

    }
}
