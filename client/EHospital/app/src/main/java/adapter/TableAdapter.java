package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bean.RegisteTable;
import gxr.ehospital.R;

/**
 * Created by Administrator on 2016/3/18.
 */
public class TableAdapter  extends BaseAdapter{
    List<RegisteTable> tables;
    Context context;
    LayoutInflater inflater;

    public TableAdapter(Context context, List<RegisteTable> tables) {
        this.context = context;
        this.tables = tables;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tables.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view=inflater.inflate(R.layout.registe_tables_item,null);
        TextView department=(TextView)view.findViewById(R.id.department);
        TextView workTime=(TextView)view.findViewById(R.id.work_time);
        TextView doctorName=(TextView)view.findViewById(R.id.doctor_name);
        TextView statusText=(TextView)view.findViewById(R.id.status);
        TextView time=(TextView)view.findViewById(R.id.time);

        department.setText(tables.get(position).getDoctor().getDepartment());
        workTime.setText(tables.get(position).getDoctor().getWorkTime());
        doctorName.setText(tables.get(position).getDoctor().getName());
        statusText.setText(tables.get(position).getStatus());
        String status=tables.get(position).getStatus();
        if(status.equals("access")){
            statusText.setText("等待就诊");
        }
        if(status.equals("wait")){
            statusText.setText("等待回应");
        }
        if(status.equals("finish")){
            statusText.setText("已就诊");
        }
        time.setText(tables.get(position).getTime());
        workTime.setText(tables.get(position).getWorkTime());
        return view;
    }
    //挂号适配器
}
