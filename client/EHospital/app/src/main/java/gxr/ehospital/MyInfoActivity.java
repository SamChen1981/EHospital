package gxr.ehospital;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.NetWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tools.UserNum;
import tools.Values;

public class MyInfoActivity extends Activity {

    EditText nameEdit, sexEdit, ageEdit, phoneEdit;
    final int getUserInfoWhat = 0x401, sendToastWhat = 0x402,changeResultWhat=0x403;
    final String getUserInfoKey = "getUserInfoKey", sendToastKey = "sendToastKey",chageResultKey="changeResultKey";
    String  name;
    Button submite;
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case getUserInfoWhat:
                    String result = message.getData().getString(getUserInfoKey);
                    if (result.equals("")) {
                        sendToast("获取个人信息失败");
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            nameEdit.setText(jsonObject.getString("name"));
                            ageEdit.setText(jsonObject.getString("age"));
                            phoneEdit.setText(jsonObject.getString("phone"));
                            sexEdit.setText(jsonObject.getString("sex"));

                        } catch (JSONException e) {
                            sendToast("数据错误");
                        }
                    }
                    break;
                case sendToastWhat:
                    String toastContent = message.getData().getString(sendToastKey);
                    Toast.makeText(MyInfoActivity.this, toastContent, Toast.LENGTH_SHORT).show();
                    break;
                case changeResultWhat:
                    String resultText=message.getData().getString(chageResultKey);
                    if (resultText.equals(""))sendToast("修改失败");
                    try {
                        JSONObject jsonObject=new JSONObject(resultText);
                        String code =jsonObject.getString("code");
                        if (code.equals("success")){
                            sendToast("修改成功");
                            getInfo();
                            UserNum.userName=name;
                        }
                        else{
                            sendToast("修改失败");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initView();
        getInfo();
    }

    public void initView() {
        submite=(Button)findViewById(R.id.submite);
        submite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               name=nameEdit.getText().toString().trim();
                String sex=sexEdit.getText().toString().trim();
                String age=ageEdit.getText().toString().trim();
                String phone=phoneEdit.getText().toString().trim();
                submiteInfo(sex, age, phone);
            }
        });
        nameEdit = (EditText) findViewById(R.id.name);
        sexEdit = (EditText) findViewById(R.id.sex);
        ageEdit = (EditText) findViewById(R.id.age);
        phoneEdit = (EditText) findViewById(R.id.phone);
    }

    public void submiteInfo(final String sex, final String age, final String phone){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetWork netWork=new NetWork();
                Map<String ,String > map=new HashMap<String ,String>();
                map.put("id",UserNum.userNum);
                map.put("name",name);
                map.put("sex",sex);
                map.put("age",age);
                map.put("phone",phone);
                String result=netWork.doPost(map, Values.changeInfo);
                if (result==null) result="";
                sendMessage(changeResultWhat,chageResultKey,result);
            }
        }).start();
    }
    public void getInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetWork netWork = new NetWork();
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", UserNum.userNum);
                String getInfoResult = netWork.doGet(map, Values.getInfoURL);
                if (getInfoResult == null) getInfoResult = "";
                sendMessage(getUserInfoWhat, getUserInfoKey, getInfoResult);
            }
        }).start();
    }

    public void sendMessage(int what, String key, String value) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void sendToast(String toast) {
        sendMessage(sendToastWhat, sendToastKey, toast);
    }
}
