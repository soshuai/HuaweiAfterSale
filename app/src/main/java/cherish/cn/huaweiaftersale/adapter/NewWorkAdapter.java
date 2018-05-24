package cherish.cn.huaweiaftersale.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cherish.cn.huaweiaftersale.MainActivity;
import cherish.cn.huaweiaftersale.ManageActivity;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.bean.WorkBean;
import cherish.cn.huaweiaftersale.util.TimeUtil;

/**
 * Created by veryw on 2018/5/15.
 */

public class NewWorkAdapter extends BaseAdapter{
    private Context context;
    private List<WorkBean> list;
    private LayoutInflater layoutInflater;
    public NewWorkAdapter(Context context,List<WorkBean> list) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.new_work_item, null);
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.customer = (TextView) convertView.findViewById(R.id.tv_name);
            holder.location = (TextView) convertView.findViewById(R.id.tv_location);
            holder.phone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.states = (TextView) convertView.findViewById(R.id.tv_states);
            holder.accept = (TextView) convertView.findViewById(R.id.accept);
            holder.refuse = (TextView) convertView.findViewById(R.id.refuse);
            holder.manage = (TextView) convertView.findViewById(R.id.manage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final WorkBean bean=list.get(position);
        if (bean!=null){
            holder.customer.setText(bean.getCustomer());
            holder.phone.setText(bean.getPhone());
            holder.time.setText(TimeUtil.getTime(bean.getTime()));
            holder.location.setText(bean.getLocation());
            holder.states.setText(bean.getStates());
        }
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.accept.setVisibility(View.GONE);
                holder.refuse.setVisibility(View.GONE);
                holder.manage.setVisibility(View.VISIBLE);
            }
        });
        holder.manage.setOnClickListener(new View.OnClickListener() {
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
        public TextView customer;
        public TextView location;
        public TextView phone;
        public TextView states;

        public TextView accept;
        public TextView refuse;
        public TextView manage;
    }
}
