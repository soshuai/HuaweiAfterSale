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
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.bean.WorkBean;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.entity.OrderListDataEntity;
import cherish.cn.huaweiaftersale.util.AppException;

public class RunningFragment extends BaseFragment implements DataCallback {
    private Unbinder unBinder;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.no_order)
    LinearLayout noorder;
    private NewWorkAdapter adapter;
    private List<WorkBean> list;
    private RunningFragment context;

    @Override
    protected void init(View view) {
        context = this;
        list = new ArrayList<>();
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
        adapter = new NewWorkAdapter(mContext, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_running;
    }

    private void loadList() {
        list.clear();
        for (int i = 0; i < 3; i++) {
            list.add(new WorkBean("", 3, "长江软件园", "小姜", "18888888888", "正在派送", "111"));
        }
        adapter.notifyDataSetChanged();
        if (list.size() <= 0) {
            noorder.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {

    }

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        if (funcKey == R.id.api_order_list) {
            list.clear();
            List<OrderListDataEntity> entityList = (List<OrderListDataEntity>) data;
            for (int i = 0; i < entityList.size(); i++) {
                OrderListDataEntity entity = entityList.get(i);
                list.add(new WorkBean("", entity.getMinute(), entity.getMeetingName(), entity.getUserName(), entity.getUserMobile(), entity.getStatus(), entity.getRecordId()));
            }
            adapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
            refreshLayout.resetNoMoreData();
            if (list.size() <= 0) {
                noorder.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        }
    }
}
