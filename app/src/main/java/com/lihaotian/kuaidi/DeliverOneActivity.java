package com.lihaotian.kuaidi;

import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.lihaotian.config.CabinetConfirmInfo;
import com.lihaotian.config.GlobalData;
import com.lihaotian.utils.MyHttp;
import com.lihaotian.utils.MyJsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class DeliverOneActivity extends AppCompatActivity {

    public EditText input_box_number;
    public EditText input_phone;
    public String uid;
    public static String cabinet_code;
    public final static String EXTRA_MESSAGE = "message";
    public String transfer2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_one);

        //强制在主线程请求网络连接
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        input_box_number = (EditText) findViewById(R.id.input_box_number);
        input_phone = (EditText) findViewById(R.id.input_phone);


        /*Log.i("手机号",transfer2);
        Log.i("柜体号",input_box_number.getText().toString());*/
    }

    //点击按钮跳到DeliverTwoActivity
    public void onDeliverTwo(View view){

        LoginActivity loginActivity = new LoginActivity();
        uid = GlobalData.getUid ();
        cabinet_code = input_box_number.getText().toString();

        Log.i("根据柜体号查询时的Uid",uid);
        Log.i("柜体号",cabinet_code);

        String resultString = getCabConfirmApi();
        CabinetConfirmInfo cabinetConfirmInfo = new CabinetConfirmInfo();
        cabinetConfirmInfo = new MyJsonParser(resultString).parselCabinetConfirmInfo();
        switch (cabinetConfirmInfo.getCode()){
            case 0:
                Log.i("准备成功","1");
                Log.i("状态码1",String.valueOf(cabinetConfirmInfo.getCode()));
                Toast.makeText(DeliverOneActivity.this,"存在此柜体,成功！", Toast.LENGTH_SHORT).show();
                GlobalData.setCabinet_code(cabinet_code);
                Intent intent = new Intent(this,DeliverTwoActivity.class);
                startActivity(intent);
                //GlobalData.setCabinet_code (cabinet_code);

                break;

            case 20000:
                Log.i("准备不存在","1");
                Log.i("状态码2",String.valueOf(cabinetConfirmInfo.getCode()));
                Toast.makeText(DeliverOneActivity.this,"不存在此柜体！", Toast.LENGTH_SHORT).show();
            default:
                Log.i("准备不知所措","1");
                Log.i("状态码3",String.valueOf(cabinetConfirmInfo.getCode()));
                break;   //TODO：为什么给我返回了状态码20001？
        }

    }

    public String getCabConfirmApi(){
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("uid", uid));
        paramsList.add(new BasicNameValuePair("cabinet_code", cabinet_code));

        MyHttp http = new MyHttp(GlobalData.BASE_URL_2 + "/capp/cabinet/info", paramsList);
        return http.doPost();
    }
}

    /*Intent intent = new Intent(this,DeliverTwoActivity.class);
    startActivity(intent);*/