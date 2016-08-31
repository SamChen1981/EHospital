package gxr.ehospital;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import net.NetWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tools.UserNum;
import tools.Values;

public class MyCaseActivity extends Activity {

    TextView contentText,createTimeText,changeTimeText;
    final int getCaseWhat=0x801,sendToastWhat=0x802;
    final String getCaseKey="getCaseKey",sendToastKey="sendToastKey";
    Handler handler=new Handler(){
      public void handleMessage(Message message){
          switch (message.what){
              case getCaseWhat:
                  String result=message.getData().getString(getCaseKey);
                  if (!result.equals("")){
                      try {
                          JSONObject jsonObject=new JSONObject(result);
                          String content=jsonObject.getString("content");
                          String changeTime=jsonObject.getString("changeTime");
                          String createTime=jsonObject.getString("createTime");
                          content=content.replace("/hh","\n");
                          changeTimeText.setText(changeTime);
                          createTimeText.setText(createTime);
                          contentText.setText(content);
                      } catch (JSONException e) {


                      }
                  }
                  else{
                      sendToast("获取病例失败");
                  }


                  break;
              case sendToastWhat:
                  String toast=message.getData().getString(sendToastKey);
                  Toast.makeText(MyCaseActivity.this,toast,Toast.LENGTH_SHORT).show();

                  break;
          }
      }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_case);
        initView();
        initData();
    }

    public void initView() {
        contentText = (TextView) findViewById(R.id.content);
        createTimeText=(TextView)findViewById(R.id.create_time);
        changeTimeText=(TextView)findViewById(R.id.change_time);
    }

    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetWork netWork = new NetWork();
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", UserNum.userNum);
                String result = netWork.doGet(map, Values.getCase);
                if (result==null) result="";
                sendMessage(getCaseWhat,getCaseKey,result);
            }
        }).start();

    }

    public void sendMessage(int what, String key, String value) {
        Message message=new Message();
        message.what=what;
        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    public void sendToast(String toast){
        sendMessage(getCaseWhat,getCaseKey,toast);
    }

}
