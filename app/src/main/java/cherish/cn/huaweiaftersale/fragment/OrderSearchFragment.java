package cherish.cn.huaweiaftersale.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TimeUtils;
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
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.bean.SearchBean;
import cherish.cn.huaweiaftersale.bean.WorkBean;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.entity.OrderListDataEntity;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.TimeUtil;
import cherish.cn.huaweiaftersale.view.ClearEditText;

public class OrderSearchFragment extends BaseFragment implements DataCallback{
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.filter_edit)
    ClearEditText editText;
    private SearchAdapter adapter;
    private List<SearchBean> list;
    private OrderSearchFragment context;

    @Override
    protected void init(View view) {
        context=this;
        list=new ArrayList<>();
        refreshLayout.setEnableAutoLoadmore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadList();
                    }
                }, 1000);
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
        adapter=new SearchAdapter(mContext,list);
        listView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String search=s.toString().toLowerCase().trim();
                Bundle bundle = new Bundle();
                bundle.putString("orderCode",search);
                ApiHelper.load(mContext, R.id.api_order_list, bundle, context);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_search;
    }

    private void loadList() {
//        list.clear();
//        for (int i = 0; i < 10; i++) {
//            list.add(new SearchBean("","",""));//工单号 进度
//        }
//        adapter.notifyDataSetChanged();
        Bundle bundle = new Bundle();
        ApiHelper.load(mContext, R.id.api_order_list, bundle, context);
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
                list.add(new SearchBean(entity.getOrderCode(), TimeUtil.getTime(entity.getMinute()),entity.getRecordId()));//工单号 进度);
            }
            adapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
            refreshLayout.resetNoMoreData();
//            if (list.size()<=0){
//                noorder.setVisibility(View.VISIBLE);
//                listView.setVisibility(View.GONE);
//            }
        }
    }
}
