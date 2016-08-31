package gxr.ehospital;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import tools.UserNum;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    View view;
    TextView nameText,infoText,myPreRegisteText,myRegisteText,myCaseText,registeHistoryText;
    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    public void initView(){
        nameText=(TextView)view.findViewById(R.id.name);
        nameText.setText(UserNum.userName);
        infoText=(TextView)view.findViewById(R.id.info);
        myPreRegisteText=(TextView)view.findViewById(R.id.my_pre_registe);
        myRegisteText=(TextView)view.findViewById(R.id.my_registe);
        myCaseText=(TextView)view.findViewById(R.id.my_case);
        registeHistoryText=(TextView)view.findViewById(R.id.registe_history);
        registeHistoryText.setOnClickListener(new BTClickListener());
        infoText.setOnClickListener(new BTClickListener());
        myPreRegisteText.setOnClickListener(new BTClickListener());
        myRegisteText.setOnClickListener(new BTClickListener());
        myCaseText.setOnClickListener(new BTClickListener());

    }
    public class BTClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            switch (v.getId()){
                case R.id.info:
                    intent.setClass(getActivity(), MyInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_pre_registe:
                    intent.setClass(getActivity(),MyRegisteActivity.class);
                    intent.putExtra("info","wait");
                    startActivity(intent);
                    break;
                case R.id.my_registe:
                    intent.setClass(getActivity(),MyRegisteActivity.class);
                    intent.putExtra("info","access");
                    startActivity(intent);
                    break;
                case R.id.registe_history:
                    intent.setClass(getActivity(),MyRegisteActivity.class);
                    intent.putExtra("info","finish");
                    startActivity(intent);
                    break;
                case R.id.my_case:
                    intent.setClass(getActivity(),MyCaseActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}

