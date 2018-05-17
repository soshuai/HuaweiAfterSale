package cherish.cn.huaweiaftersale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.bean.LoginMoreBean;

public class LoginMoreAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<LoginMoreBean> list;
    private LoginItemClick listener;

    public LoginMoreAdapter(Context context, LoginItemClick listener, List<LoginMoreBean> list) {
        this.context = context;
        this.list = list;
        this.listener=listener;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setListData(List<LoginMoreBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.login_more_item, null);
            holder = new ViewHolder();
            holder.name=convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name=list.get(position).getName();
        holder.name.setText(name);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "选中:"+list.get(position).getName(), Toast.LENGTH_SHORT).show();
                listener.onItemClick(position);
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView name;
    }

    public interface LoginItemClick{
        public void onItemClick(int position);
    }
}

