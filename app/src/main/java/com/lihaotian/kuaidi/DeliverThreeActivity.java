package com.lihaotian.kuaidi;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.lihaotian.addtion.Status;
import com.lihaotian.config.CabinetConfirmInfo;
import com.lihaotian.config.DeliverConfirmInfo;
import com.lihaotian.config.DeliverSucceed;
import com.lihaotian.config.GlobalData;
import com.lihaotian.utils.MyHttp;
import com.lihaotian.utils.MyJsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class DeliverThreeActivity extends AppCompatActivity {

    public AlertDialog.Builder dialog = null;
    public int status_code=0;
    public TextView tv_delivery_confirm_expnum;
    public TextView tv_delivery_confirm_tel;
    public TextView tv_delivery_confirm_cabnum;
    public TextView tv_delivery_confirm_cellnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_three);
        tv_delivery_confirm_expnum = (TextView)findViewById (R.id.tv_delivery_confirm_expnum);
        tv_delivery_confirm_tel = (TextView)findViewById (R.id.tv_delivery_confirm_tel);
        tv_delivery_confirm_cabnum = (TextView)findViewById (R.id.tv_delivery_confirm_cabnum);
        tv_delivery_confirm_cellnum = (TextView)findViewById(R.id.tv_delivery_confirm_cellnum);

        tv_delivery_confirm_expnum.setText (DeliverTwoActivity.order_number);
        Log.i("DeliverThree中的订单号",tv_delivery_confirm_expnum.getText ().toString ());
        tv_delivery_confirm_tel.setText (DeliverTwoActivity.receiver_phone_number);
        Log.i("DeliverThree中的手机号",tv_delivery_confirm_tel.getText().toString ());
        tv_delivery_confirm_cabnum.setText (DeliverOneActivity.cabinet_code);
        Log.i ("DeliverThree中的柜体号",tv_delivery_confirm_cabnum.getText().toString());
        tv_delivery_confirm_cellnum.setText(String.valueOf(DeliverConfirmInfo.getCode()));
        Log.i ("DeliverThree中的箱子号",DeliverConfirmInfo.bin_code );

        //加载按钮视图并添加监听事件
    }

    //点击取消投递按钮回到MenuActivity
    public void onCancelDeliver(View view){
        dialog = new AlertDialog.Builder(DeliverThreeActivity.this);
        dialog.setTitle("警告！警告！");
        dialog.setMessage("你不投递了么");
        dialog.setCancelable(false);
        dialog.setPositiveButton("是",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DeliverThreeActivity.this, "已放弃投递", Toast.LENGTH_SHORT).show();
                status_code = -1;
                Log.i("DeliverThree的状态码1", String.valueOf(status_code));
            }
        });
        dialog.setNegativeButton("否",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DeliverThreeActivity.this, "你还想投递",Toast.LENGTH_SHORT).show();
                status_code = -2;
            }
        });
        dialog.show();
        Log.i("DeliverThree的状态码2", String.valueOf(status_code));

        if(status_code == -1){

            Intent intent = new Intent(this,DeliverTwoActivity.class);
            startActivity(intent);
        }
    }

    //点击完成投递按钮跳到DeliverTwoActivity
    public void onSucceedDeliver(View view){
        String resultString = DeliverSucceedApi();
        DeliverSucceed deliverSucceed = new DeliverSucceed();
        deliverSucceed = new MyJsonParser(resultString).parselDeliverSucceed();
        Log.i("DeliverThree状态码",String.valueOf(deliverSucceed.getCode()));
        switch (deliverSucceed.getCode()){
            case 0:
                Toast.makeText(DeliverThreeActivity.this,"成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,DeliverTwoActivity.class);
                startActivity(intent);
                break;
            case 15100:
                Toast.makeText(DeliverThreeActivity.this,"订单已存在", Toast.LENGTH_SHORT).show();
                break;
            case 10001:
                Toast.makeText(DeliverThreeActivity.this,"柜体无效", Toast.LENGTH_SHORT).show();
                break;
            case 10002:
                Toast.makeText(DeliverThreeActivity.this,"箱体无效", Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }


    }

    public String DeliverSucceedApi(){
        GlobalData globalData = new GlobalData();
        DeliverConfirmInfo deliverConfirmInfo = new DeliverConfirmInfo();
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("uid", globalData.getUid()));
        Log.i("DeliverThree中的uid",globalData.getUid());
        paramsList.add(new BasicNameValuePair("order_id", deliverConfirmInfo.getOrder_id()));
        Log.i("DeliverThree中的order_id",deliverConfirmInfo.getOrder_id());

        Log.i("Three中的paramList",paramsList.toString());
        MyHttp http = new MyHttp(GlobalData.BASE_URL_2 + "/capp/delivery/confirm", paramsList);
        return http.doPost();
    }
}
