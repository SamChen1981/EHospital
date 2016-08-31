package gxr.ehospital;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.NetWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import tools.Values;
import tools.UserNum;


public class LoginActivity extends Activity {

    EditText phoneEdit, passwordEdit;
    Button loginBt, registBt;
    final int LOGINRESULTWHAT = 0x1, SENDNOTICEWHAT = 0x2;
    final String LOGINRESULTSTR="loginResult",REGISTRESULTSTR="registResult",NOTICERESULTSTR="notice";
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGINRESULTWHAT:
                    String loginResultStr = msg.getData().getString(LOGINRESULTSTR);
                    if (!loginResultStr.equals("")) {
                        try {
                            JSONObject jsonObject = new JSONObject(loginResultStr);
                            String result = jsonObject.getString("code");
                            if (result.equals("success")) {
                                UserNum.userNum = jsonObject.getString("id");
                                UserNum.userName=jsonObject.getString("name");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                if(result.equals("no_phone")){
                                    sendNotice("密码或账号错误");
                                }
                                if(result.equals("fail")) {
                                    sendNotice("登录失败");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case SENDNOTICEWHAT:
                    String result=msg.getData().getString(NOTICERESULTSTR);
                    Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {
        phoneEdit = (EditText) findViewById(R.id.phone_edit);
        passwordEdit = (EditText) findViewById(R.id.password_edit);
        loginBt = (Button) findViewById(R.id.login_bt);
        registBt = (Button) findViewById(R.id.regist_bt);
        loginBt.setOnClickListener(new BtOnClickListener());
        registBt.setOnClickListener(new BtOnClickListener());
    }

    public void login(final String phone, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> map=new HashMap<String, String>();
                map.put("phone",phone);
                map.put("password",password);
                NetWork netWork=new NetWork();
                String result=netWork.doGet(map,Values.loginURL);
                sendMessage(LOGINRESULTSTR,result,LOGINRESULTWHAT);
            }
        }).start();
    }

    public void sendMessage(String key,String str,int messageWhat){
        Message message=new Message();
        message.what=messageWhat;
        Bundle bundle=new Bundle();
        bundle.putString(key,str);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    public void sendNotice(String notice) {
        Message message = new Message();
        message.what = SENDNOTICEWHAT;
        Bundle bundle = new Bundle();
        bundle.putString(NOTICERESULTSTR, notice);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public class BtOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_bt:
                    String phone = phoneEdit.getText().toString().trim();
                    String password = passwordEdit.getText().toString().trim();
                    login(phone, password);
                    break;
                case R.id.regist_bt:

                    //打开注册页面
                    RegisterPage registerPage = new RegisterPage();
                    registerPage.setRegisterCallback(new EventHandler() {
                        public void afterEvent(int event, int result, Object data) {
// 解析注册结果
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                @SuppressWarnings("unchecked")
                                Map<String,Object> phoneMap = (HashMap<String, Object>) data;
                                String country = (String) phoneMap.get("country");
                                String phone = (String)phoneMap.get("phone");
                                Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
                                intent.putExtra("phone",phone);
                                startActivity(intent);
                            }
                        }
                    });
                    registerPage.show(LoginActivity.this);
                    break;
            }
        }
    }
}
