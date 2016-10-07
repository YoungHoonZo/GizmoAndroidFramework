package kr.pe.gizmo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.pe.gizmo.R;
import kr.pe.gizmo.vo.ActorVo;

/**
 * Created by yhzo on 16. 10. 7.
 */
public class ListMapAdapter extends BaseAdapter {

    Context context;
    ArrayList items;

    public ListMapAdapter(Context context, ArrayList<Map> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        Map<String, Object> item = (Map<String, Object>) items.get(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(item.get("name").toString());

        return convertView;
    }

    class ViewHolder{

        @BindView(R.id.tvName) TextView tvName;

        public ViewHolder(View v){
            ButterKnife.bind(this, v);
        }
    }
}
