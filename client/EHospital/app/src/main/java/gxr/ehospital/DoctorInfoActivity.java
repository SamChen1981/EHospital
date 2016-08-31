package gxr.ehospital;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.NetWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bean.Doctor;
import tools.UserNum;
import tools.Values;

public class DoctorInfoActivity extends Activity {
    Doctor doctor=new Doctor();
    LinearLayout callLayout;
    ImageView doctorHead;
    TextView nameSex, department, phone, workTime, registerNum;
    Button preRegisteTable;
    final String PREREGISTERRESULT = "preRegisterResult", SENDTOASTSTR = "sendToast", GETDOCTORINFOSTR = "getDoctorInfoResult";
    final int PREREGISTERWHAT = 0x301, SENDTOASTWHAT = 0x302, GETDOCTORINFOWHAT = 0x303;
    String doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");
        initView();
        getDoctorInfo();
    }

    public void initView() {
        callLayout = (LinearLayout) findViewById(R.id.phone_layout);
        doctorHead = (ImageView) findViewById(R.id.doctor_head);
        nameSex = (TextView) findViewById(R.id.name_sex);
        department = (TextView) findViewById(R.id.department);
        workTime = (TextView) findViewById(R.id.work_time);
        phone = (TextView) findViewById(R.id.phone);
        registerNum = (TextView) findViewById(R.id.register_num);
        preRegisteTable = (Button) findViewById(R.id.register_table_bt);
        preRegisteTable.setOnClickListener(new BTClickListener());
       /* Glide.with(DoctorInfoActivity.this).load(doctor.getHeadImg()).into(doctorHead);
        nameSex.setText("姓名：" + doctor.getName() + "\t性别：" + doctor.getSex());
        department.setText("科室：" + doctor.getDepartment());
        final String phoneStr=doctor.getPhone();
        callLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneStr));
                startActivity(intent);
            }
        });
        if(!(phoneStr.equals("")||phoneStr==null)) {
            phone.setText("手机：" + doctor.getPhone());
            callLayout.setVisibility(View.VISIBLE);

        }
        else{
            callLayout.setVisibility(View.GONE);
        }
        workTime.setText("坐诊时间：" + doctor.getWorkTime());
        registerNum.setText("已挂号人数：" + doctor.getRegisterNum());*/
    }

    public void getDoctorInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetWork netWork = new NetWork();
                Map<String,String> map=new HashMap<String, String>();
                map.put("doctorId",doctorId);
                String getDoctorInfoResult = netWork.doGet(map,Values.getDoctorInfo);
                if (getDoctorInfoResult == null) {
                    getDoctorInfoResult = "";
                }
                Message message = new Message();
                message.what = GETDOCTORINFOWHAT;
                Bundle bundle = new Bundle();
                bundle.putString(GETDOCTORINFOSTR, getDoctorInfoResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }).start();

    }

    public void sendToast(String toast) {
        Message message = new Message();
        message.what = SENDTOASTWHAT;
        Bundle bundle = new Bundle();
        bundle.putString(SENDTOASTSTR, toast);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public class BTClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_table_bt:
                    preRegister();
                    break;
            }
        }
    }
    public void preRegister() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("doctorId", doctorId);
                map.put("userId", UserNum.userNum);
                NetWork netWork = new NetWork();
                String result = netWork.doPost(map, Values.userRegisterTable);
                if (result == null) {
                    result = "";
                }
                Message message = new Message();
                message.what = PREREGISTERWHAT;
                Bundle bundle = new Bundle();
                bundle.putString(PREREGISTERRESULT, result);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }).start();
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case PREREGISTERWHAT:
                    String resultJson = message.getData().getString(PREREGISTERRESULT);
                    if (!resultJson.equals("")) {
                        try {
                            JSONObject jsonObject = new JSONObject(resultJson);
                            String result = jsonObject.getString("result");
                            if (result.equals("success")) {
                                sendToast("申请成功,请等待医生回应");
                                preRegisteTable.setClickable(false);
                                getDoctorInfo();
                                preRegisteTable.setBackgroundResource(R.drawable.login_bt_pressed);
                            }
                            if(result.equals("had_register")){
                                sendToast("您已在平台挂号成功，请勿重复申请");
                                preRegisteTable.setBackgroundResource(R.drawable.login_bt_pressed);
                                preRegisteTable.setClickable(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        sendToast("挂号失败，请重试");
                    }
                    break;
                case SENDTOASTWHAT:
                    Toast.makeText(DoctorInfoActivity.this, message.getData().getString(SENDTOASTSTR), Toast.LENGTH_SHORT).show();
                    break;
                case GETDOCTORINFOWHAT:
                    String getInfoResult = message.getData().getString(GETDOCTORINFOSTR);
                    if (getInfoResult != "") {
                        try {
                            JSONObject jsonObject = new JSONObject(getInfoResult);
                            doctor.setId(jsonObject.getString("id"));
                            doctor.setPhone(jsonObject.getString("phone"));
                            doctor.setName(jsonObject.getString("name"));
                            doctor.setSex(jsonObject.getString("sex"));
                            doctor.setDepartment(jsonObject.getString("department"));
                            doctor.setHeadImg(jsonObject.getString("headImg"));
                            doctor.setWorkTime(jsonObject.getString("workTime"));
                            doctor.setRegisterNum(jsonObject.getString("registerNum"));

                            Glide.with(DoctorInfoActivity.this).load(Values.serverUrl + "/doctorhead/"+doctor.getHeadImg()).into(doctorHead);
                            nameSex.setText("姓名：" + doctor.getName() + "\t性别：" + doctor.getSex());
                            department.setText("科室：" + doctor.getDepartment());
                            final String phoneStr = doctor.getPhone();
                            callLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //用intent启动拨打电话
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneStr));
                                    startActivity(intent);
                                }
                            });
                            if (!(phoneStr.equals("") || phoneStr == null)) {
                                phone.setText("手机：" + doctor.getPhone());
                                callLayout.setVisibility(View.VISIBLE);
                            } else {
                                callLayout.setVisibility(View.GONE);
                            }
                            workTime.setText("坐诊时间：" + doctor.getWorkTime());
                            registerNum.setText("已挂号人数：" + doctor.getRegisterNum());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        sendToast("获取详细资料失败");
                    }
                    break;
            }
        }
    };

}
