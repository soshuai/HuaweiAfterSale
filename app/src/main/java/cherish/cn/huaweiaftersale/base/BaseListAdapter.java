package cherish.cn.huaweiaftersale.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于列表的适配器基类
 * 
 * @param <T>
 * @author zj_chongzi
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected String  TAG;
    protected Context mContext;
    private List<T>   mData;

    public BaseListAdapter(Context context) {
        TAG = this.getClass().getSimpleName();
        mContext = context;
        mData = new ArrayList<T>();
    }

    public BaseListAdapter(Context context, List<T> listData) {
        this(context);
        if (null != listData)
            mData.addAll(listData);
    }

    public List<T> getListData() {
        return mData;
    }

    /**
     * 添加分页列表数据
     * 
     * @param moreListData
     *            分页数据
     */
    public void addListData(List<T> moreListData) {
        addListData(mData.size(), moreListData);
    }

    public void addListData(int location, List<T> moreListData) {
        if (location > -1 && location <= mData.size() && null != moreListData && !moreListData.isEmpty()) {
            mData.addAll(location, moreListData);
            notifyDataSetChanged();
        }
    }

    public void addData(T newData) {
        if (null != newData) {
            mData.add(newData);
            notifyDataSetChanged();
        }
    }

    public void remove(T removeData) {
        if (mData.contains(removeData)) {
            mData.remove(removeData);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置刷新的列表数据
     * 
     * @param listData
     *            列表数据
     */
    public void setListData(List<T> listData) {
        mData.clear();
        if (null != listData && !listData.isEmpty()) {
            mData.addAll(listData);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return position > (getCount() - 1) ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
