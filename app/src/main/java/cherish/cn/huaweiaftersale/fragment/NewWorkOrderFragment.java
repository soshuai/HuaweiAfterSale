package cherish.cn.huaweiaftersale.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.adapter.NewWorkAdapter;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.bean.WorkBean;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.entity.OrderListDataEntity;
import cherish.cn.huaweiaftersale.event.CuntEvent;
import cherish.cn.huaweiaftersale.okhttp.api.GetTokenApi;
import cherish.cn.huaweiaftersale.okhttp.listener.TaskProcessListener;
import cherish.cn.huaweiaftersale.util.AppException;

public class NewWorkOrderFragment extends BaseFragment implements DataCallback,NewWorkAdapter.AcceptOrNotListener{
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.no_order)
    LinearLayout noorder;
    private NewWorkAdapter adapter;
    private List<WorkBean> list;
    private NewWorkOrderFragment context;

    @Override
    protected void init(View view) {
        EventBus.getDefault().register(this);
        context = this;
        list = new ArrayList<>();
        refreshLayout.setEnableAutoLoadmore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                noorder.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                bundle.putInt("status", 1);
                bundle.putString("orderCode","");
                ApiHelper.load(mContext, R.id.api_order_list, bundle, context);
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
        adapter = new NewWorkAdapter(mContext, list,context);
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_work_order;
    }

//    private void loadList() {
//        list.clear();
//        for (int i = 0; i < 1; i++) {
//            list.add(new WorkBean("", "", "", "", ""));
//        }
//        adapter.notifyDataSetChanged();
//        if (list.size() <= 0) {
//            noorder.setVisibility(View.VISIBLE);
//            listView.setVisibility(View.GONE);
//        }
//    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {
        refreshLayout.finishRefresh();
        refreshLayout.resetNoMoreData();
    }

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        if (funcKey == R.id.api_order_list) {
            list.clear();
            List<OrderListDataEntity> entityList = (List<OrderListDataEntity>) data;
            for (int i = 0; i < entityList.size(); i++) {
                OrderListDataEntity entity = entityList.get(i);
                list.add(new WorkBean("", entity.getMinute(), entity.getMeetingName(), entity.getUserName(), entity.getUserMobile(),entity.getStatus(),entity.getRecordId(),entity.getState()));
            }
            adapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
            refreshLayout.resetNoMoreData();
            if (list.size()<=0){
                noorder.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
            EventBus.getDefault().post(new CuntEvent(list.size(),true));
        }else if (funcKey == R.id.api_order_update){
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void update(String state,String recordId) {
        if (state.equals("4")){
            showTip("是否取消订单",state, recordId);
        }else if (state.equals("2")){
            showTip("是否接单",state, recordId);
        }else{
            Bundle bundle=new Bundle();
            bundle.putString("status", state);
            bundle.putString("recordId", recordId);
            ApiHelper.load(mContext, R.id.api_order_update, bundle, context);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CuntEvent event) {
        refreshLayout.autoRefresh();
    }

    public void showTip(String message, final String state, final String recordId){
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setMessage(message);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle=new Bundle();
                bundle.putString("status", state);
                bundle.putString("recordId", recordId);
                ApiHelper.load(mContext, R.id.api_order_update, bundle, context);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
