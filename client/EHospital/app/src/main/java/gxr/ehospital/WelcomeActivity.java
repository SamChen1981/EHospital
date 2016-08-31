package gxr.ehospital;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import java.util.TimerTask;

import cn.smssdk.SMSSDK;
import tools.Values;

public class WelcomeActivity extends Activity {

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.initSDK(this, Values.appKey, Values.appSecret);
        setContentView(R.layout.activity_welcome);
        TimerTask task = new TimerTask(){
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        task.run();
    }
}
