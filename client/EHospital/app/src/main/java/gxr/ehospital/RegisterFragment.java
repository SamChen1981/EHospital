package gxr.ehospital;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import net.NetWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.DoctorAdapter;
import bean.Doctor;
import tools.Values;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
//挂号
    View view;
    GridView gridView;
     final String GETDOCTORSRESULTSTR="getDoctorsResult",SENDNOTICESTR="notice";
    final int GETDOCTORSWHAT=0x201,SENDNOTICEWHAT=0x202;
    List<Doctor> doctors;
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case GETDOCTORSWHAT:
                    String result=msg.getData().getString(GETDOCTORSRESULTSTR);
                    if(!result.equals("")){

                        try {
                            JSONObject jsonObject=new JSONObject(result);
                            JSONArray jsonArray=jsonObject.getJSONArray("doctors");
                            doctors=new ArrayList<Doctor>();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject doctorJson=jsonArray.getJSONObject(i);
                                Doctor doctor=new Doctor();
                                doctor.setId(doctorJson.getString("id"));
                                doctor.setPhone(doctorJson.getString("phone"));
                                doctor.setName(doctorJson.getString("name"));
                                doctor.setSex(doctorJson.getString("sex"));
                                doctor.setDepartment(doctorJson.getString("department"));
                                doctor.setHeadImg(Values.serverUrl + "/doctorhead/"+doctorJson.getString("headImg"));
                                doctor.setWorkTime(doctorJson.getString("workTime"));
                                doctor.setRegisterNum(doctorJson.getString("registerNum"));
                                doctors.add(doctor);
                            }
                            DoctorAdapter adapter=new DoctorAdapter(getActivity(),doctors);
                              gridView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        sendNotice("获取列表失败");
                    }
                    break;
                case SENDNOTICEWHAT:
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initDate();
    }
    public void initView(){
        gridView=(GridView)view.findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),DoctorInfoActivity.class);
                intent.putExtra("doctorId",doctors.get(position).getId());
                startActivity(intent);
            }
        });
    }
    public void initDate(){
        final NetWork netWork=new NetWork();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result=netWork.doGet(null,Values.getDoctors);
                if(result==null){
                    result="";
                }
                sendMessage(GETDOCTORSRESULTSTR,result,GETDOCTORSWHAT);
            }
        }).start();
    }
    public void sendMessage(String key,String content,int what){
        Message message=new Message();
        message.what=what;
        Bundle bundle=new Bundle();
        bundle.putString(key,content);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    public void sendNotice(String content){
        Message message=new Message();
        message.what=SENDNOTICEWHAT;
        Bundle bundle=new Bundle();
        bundle.putString(SENDNOTICESTR,content);
        message.setData(bundle);
        handler.sendMessage(message);
    }

}
