package cherish.cn.huaweiaftersale.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cherish.cn.huaweiaftersale.ManageActivity;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.bean.SearchBean;
import cherish.cn.huaweiaftersale.bean.WorkBean;

/**
 * Created by veryw on 2018/5/15.
 */

public class SearchAdapter extends BaseAdapter{
    private Context context;
    private List<SearchBean> list;
    private LayoutInflater layoutInflater;
    public SearchAdapter(Context context, List<SearchBean> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.search_item, null);
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.time = (TextView) convertView.findViewById(R.id.tv_progress);
            holder.relativelayout=convertView.findViewById(R.id.relativelayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SearchBean bean=list.get(position);
        holder.number.setText("工单号："+list.get(position).getNumber());
        holder.time.setText(list.get(position).getProgress());
        holder.relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ManageActivity.class);
                intent.putExtra("recordId",bean.getRecordId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView number;
        public TextView time;
        public RelativeLayout relativelayout;
    }
}
