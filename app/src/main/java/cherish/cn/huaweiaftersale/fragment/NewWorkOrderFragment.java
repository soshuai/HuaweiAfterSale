package cherish.cn.huaweiaftersale.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.adapter.NewWorkAdapter;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.bean.WorkBean;

public class NewWorkOrderFragment extends BaseFragment {
    private Unbinder unBinder;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.no_order)
    LinearLayout noorder;
    private NewWorkAdapter adapter;
    private List<WorkBean> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_work_order, container, false);
        unBinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        list=new ArrayList<>();
        refreshLayout.setEnableAutoLoadmore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                noorder.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadList();
                        refreshlayout.finishRefresh();
                        refreshlayout.resetNoMoreData();
                    }
                }, 1000);
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
        adapter=new NewWorkAdapter(mContext,list);
        listView.setAdapter(adapter);
    }

    private void loadList() {
        list.clear();
        for (int i = 0; i < 1; i++) {
            list.add(new WorkBean("","","","",""));
        }
        adapter.notifyDataSetChanged();
        if (list.size()<=0){
         noorder.setVisibility(View.VISIBLE);
         listView.setVisibility(View.GONE);
        }
    }
}
