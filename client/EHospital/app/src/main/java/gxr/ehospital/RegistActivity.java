package gxr.ehospital;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.NetWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tools.Values;

public class RegistActivity extends Activity {

    EditText passwordEdit, passwordSecondEdit;
    Button submit;
    String phone;
    public final String REGISTRESULTSTR = "registResult", SENDNOTICESTR = "notice";
    public final int REGISTRESULTWHAT = 0x101, SENDNOTICEWHAT = 0x102;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGISTRESULTWHAT:
                    String result = msg.getData().getString(REGISTRESULTSTR);
                    if (!result.equals("")) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                sendNotice("注册成功");
                                finish();
                            } else {
                                if (code.equals("user_had")) {
                                    sendNotice("账号已存在");
                                } else {
                                    sendNotice("注册失败");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        sendNotice("失败");
                    }
                    break;
                case SENDNOTICEWHAT:
                    String result2 = msg.getData().getString(SENDNOTICESTR);
                    Toast.makeText(RegistActivity.this, result2, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        initView();
    }

    public void initView() {
        passwordEdit = (EditText) findViewById(R.id.password_edit);
        passwordSecondEdit = (EditText) findViewById(R.id.password_second_edit);
        submit = (Button) findViewById(R.id.submit_bt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordStr = passwordEdit.getText().toString().trim();
                String passwordSecondStr = passwordSecondEdit.getText().toString().trim();
                if (passwordSecondStr.equals(passwordStr)) {
                    regist(phone, passwordStr);
                }
            }
        });
    }

    public void regist(final String phone, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetWork netWork = new NetWork();
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", phone);
                map.put("password", password);
                String result = netWork.doPost(map, Values.registURL);
                sendMessage(REGISTRESULTSTR, result, REGISTRESULTWHAT);
            }
        }).start();
    }

    public void sendMessage(String key, String result, int what) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString(key, result);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void sendNotice(String notice) {
        Message message = new Message();
        message.what = SENDNOTICEWHAT;
        Bundle bundle = new Bundle();
        bundle.putString(SENDNOTICESTR, notice);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
