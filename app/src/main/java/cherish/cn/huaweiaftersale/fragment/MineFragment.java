package cherish.cn.huaweiaftersale.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.BaseFragment;

public class MineFragment extends BaseFragment {
    @Override
    protected void init(View view) {
        List<String> list=new ArrayList<>();
        list.add("kjk");
        list.add("kjk");
        list.add("kjk");
        list.add("kjk");
        Log.i("AAAA",list.toString());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

}
