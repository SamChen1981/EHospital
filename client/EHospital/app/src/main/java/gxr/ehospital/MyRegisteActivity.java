package gxr.ehospital;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import net.NetWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.TableAdapter;
import bean.Doctor;
import bean.RegisteTable;
import bean.User;
import tools.UserNum;
import tools.Values;

public class MyRegisteActivity extends Activity {
    //我的挂号和我的申请页面

    ListView registeListView;
    String info;
    final int getRegisteWhat=0x501,sendToastWhat=0x502;
    final String getRegisteKey="getRegisteKey",sendToastKey="sendToastKey";
    List<RegisteTable> tables;

    Handler handler=new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case getRegisteWhat:
                    String result=message.getData().getString(getRegisteKey);
                    if (result.equals("")){
                        sendToast("获取失败");
                    }
                    else{
                        try {
                            JSONObject jsonObject=new JSONObject(result);
                            JSONArray jsonArray=jsonObject.getJSONArray("tables");
                            tables=new ArrayList<RegisteTable>();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject tableObject=jsonArray.getJSONObject(i);
                                RegisteTable table=new RegisteTable();
                                table.setId(tableObject.getInt("id") + "");
                                table.setTime(tableObject.getString("time"));
                                table.setStatus(tableObject.getString("status"));
                                table.setWorkTime(tableObject.getString("workTime"));
                                Doctor doctor=new Doctor();
                                doctor.setId(tableObject.getInt("doctorId")+"");
                                doctor.setName(tableObject.getString("doctorName"));
                                doctor.setDepartment(tableObject.getString("department"));
                                doctor.setPhone(tableObject.getString("phone"));
                                table.setDoctor(doctor);
                                tables.add(table);
                            }
                            TableAdapter adapter=new TableAdapter(MyRegisteActivity.this,tables);
                            registeListView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    break;
                case sendToastWhat:
                    String toast=message.getData().getString(sendToastKey);
                    Toast.makeText(MyRegisteActivity.this,toast,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_registe);
        info=getIntent().getStringExtra("info");
        initView();
        initData();
    }
    public void initView(){
        registeListView=(ListView)findViewById(R.id.regist_list);
    }
    public void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetWork netWork=new NetWork();
                Map<String,String> map=new HashMap<String,String>();
                map.put("userId", UserNum.userNum);
                map.put("info",info);
                String result=netWork.doGet(map, Values.getRegiste);
                if (result==null) result="";
                sendMessage(getRegisteWhat,getRegisteKey,result);
            }
        }).start();
    }
    public void sendMessage(int what,String key,String value){
        Message message=new Message();
        message.what=what;
        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void sendToast(String toast){
        sendMessage(sendToastWhat,sendToastKey,toast);
    }
}
