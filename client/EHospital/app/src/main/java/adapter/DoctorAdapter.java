package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bean.Doctor;
import gxr.ehospital.R;

/**
 * Created by Administrator on 2016/1/10.
 */
public class DoctorAdapter extends BaseAdapter {
    List<Doctor> doctorList;
    Context context;
    LayoutInflater inflater;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return doctorList.size();
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
        view=inflater.inflate(R.layout.doctor_item,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.head_img);
        TextView nameSex=(TextView)view.findViewById(R.id.name_sex);
        TextView department=(TextView)view.findViewById(R.id.department);
        TextView workTime=(TextView)view.findViewById(R.id.work_time);

        Doctor doctor=doctorList.get(position);
        nameSex.setText(doctor.getName()+"  "+doctor.getSex());
        department.setText(doctor.getDepartment());
        workTime.setText(doctor.getWorkTime());
        Glide.with(context).load(doctor.getHeadImg()).into(imageView);

      /*  DownImgAsycnTask task=new DownImgAsycnTask(imageView);
        task.execute(doctor.getHeadImg());*/
        return view;
    }
}
