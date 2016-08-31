package gxr.ehospital;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    ImageView registerImg,interactImg,mineImg;
    LinearLayout registerBt,mineBt;//interactBt,
    FrameLayout fragment;
    RegisterFragment registerFragment;
//    InteractFragment interactFragment;
    MineFragment mineFragment;
    FragmentManager fm;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        registerImg=(ImageView)findViewById(R.id.register_img);
//        interactImg=(ImageView)findViewById(R.id.interact_img);
        mineImg=(ImageView)findViewById(R.id.mine_img);
        registerBt=(LinearLayout)findViewById(R.id.register_bt);
//        interactBt=(LinearLayout)findViewById(R.id.interact_bt);
        mineBt=(LinearLayout)findViewById(R.id.mine_bt);
        fragment=(FrameLayout)findViewById(R.id.frament_layout);
        registerBt.setOnClickListener(new LayoutOnClickListener());
//        interactBt.setOnClickListener(new LayoutOnClickListener());
        mineBt.setOnClickListener(new LayoutOnClickListener());
         fm = getFragmentManager();
         transaction = fm.beginTransaction();
        registerFragment = new RegisterFragment();
//        interactFragment=new InteractFragment();
        mineFragment=new MineFragment();

        transaction.replace(R.id.frament_layout,registerFragment);
        transaction.commit();
    }
    public class LayoutOnClickListener implements View.OnClickListener{
        // 开启Fragment事务

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = fm.beginTransaction();
            switch (v.getId()){
                case R.id.register_bt:
                    if (registerFragment == null)
                    {
                        registerFragment = new RegisterFragment();
                    }
                    // 使用当前Fragment的布局替代id_content的控件
                    transaction.replace(R.id.frament_layout, registerFragment);
                    break;
              /*  case R.id.interact_bt:
                    if(interactFragment==null){
                        interactFragment=new InteractFragment();
                    }
                    transaction.replace(R.id.frament_layout,interactFragment);
                    break;*/
                case R.id.mine_bt:
                    if(mineFragment==null){
                        mineFragment=new MineFragment();
                    }
                    transaction.replace(R.id.frament_layout, mineFragment);
                    break;
            }
            transaction.commit();
        }
    }

}
