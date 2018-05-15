package cherish.cn.huaweiaftersale.fragment;

import android.os.Bundle;
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
import cherish.cn.huaweiaftersale.adapter.SearchAdapter;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.bean.SearchBean;
import cherish.cn.huaweiaftersale.bean.WorkBean;
import de.greenrobot.event.EventBus;

public class OrderSearchFragment extends BaseFragment {
    private Unbinder unBinder;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private SearchAdapter adapter;
    private List<SearchBean> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_search, container, false);
//        EventBus.getDefault().register(this);
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
        adapter=new SearchAdapter(mContext,list);
        listView.setAdapter(adapter);
    }

    private void loadList() {
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add(new SearchBean("",""));//工单号 进度
        }
        adapter.notifyDataSetChanged();
    }
}
