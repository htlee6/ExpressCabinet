package com.lihaotian.kuaidi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lihaotian.config.CabinetConfirmInfo;
import com.lihaotian.config.DeliverConfirmInfo;
import com.lihaotian.config.GlobalData;
import com.lihaotian.utils.MyHttp;
import com.lihaotian.utils.MyJsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class  DeliverTwoActivity extends AppCompatActivity {

    public TextView avail_cells_count;
    public EditText input_order_number;
    public EditText input_receiver_phone_number;
    public static String  order_number;
    public static String receiver_phone_number;
    public String cell_type;
    public Spinner box_size;
    public int big;
    public int middle;
    public int small;
    public int sup_small;
    public static String order_id_in_DeliverTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_two);

        //强制在主线程请求网络连接
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        input_order_number = (EditText)findViewById(R.id.input_order_number);
        input_receiver_phone_number = (EditText)findViewById(R.id.input_receiver_phone_number);

        avail_cells_count=(TextView) findViewById(R.id.avail_cells_count);
        CabinetConfirmInfo cabinetconfirminfo=new CabinetConfirmInfo ();
        big = cabinetconfirminfo.getBig_idle_count();
        middle = cabinetconfirminfo.getMiddle_idle_count();
        small = cabinetconfirminfo.getSmall_idle_count();
        sup_small = cabinetconfirminfo.getSupersmall_idle_count();

        Log.i("onCreate时大箱数",String.valueOf(CabinetConfirmInfo.big_idle_count));
        Log.i("onCreate时中箱数",String.valueOf(CabinetConfirmInfo.middle_idle_count));
        Log.i("onCreate时小箱数",String.valueOf(CabinetConfirmInfo.small_idle_count));
        Log.i("onCreate时超小箱数",String.valueOf(CabinetConfirmInfo.supersmall_idle_count));

        avail_cells_count.setText("大格口："+ big+"中格口："+middle+"小格口："+small+"超小格口"+sup_small);


    }
    public void onDeliverThree(View view) throws UnsupportedEncodingException {

        order_number = input_order_number.getText().toString();
        Log.i("输入的订单号",order_number);
        receiver_phone_number = input_receiver_phone_number.getText().toString();
        Log.i("收件人手机号",receiver_phone_number);
        box_size = (Spinner)findViewById(R.id.box_size);
        Log.i("box_size",String.valueOf(box_size.getSelectedItem()));
        if(String.valueOf (box_size.getSelectedItem ()).equals ("大箱")){
            cell_type="10901";
        }
        else if(String.valueOf (box_size.getSelectedItem ()).equals ("中箱")){
            cell_type="10902";
        }
        else if(String.valueOf (box_size.getSelectedItem ()).equals ("小箱")){
            cell_type="10903";
        }
        else if(String.valueOf (box_size.getSelectedItem ()).equals ("超小箱")){
            cell_type="10904";
        }
        Log.i("选择的柜子规格",cell_type);
        Log.i("判断有无该规格空箱：true表示有；false表示无",String.valueOf(judge_number(cell_type)));
        Log.i("大箱数",String.valueOf(big));
        Log.i("中箱数",String.valueOf(middle));
        Log.i("小箱数",String.valueOf(small));
        Log.i("超小箱数",String.valueOf(sup_small));


        if(judge_number(cell_type)) {
            String resultString = SendDeliverInfoApi();
            Log.i("Two中的ResultString",resultString);
            DeliverConfirmInfo deliverConfirmInfo = new MyJsonParser(resultString).parselDeliverConfirmInfo();
           // Log.i("id",DeliverConfirmInfo.getId ());
            //order_id_in_DeliverTwo = deliverConfirmInfo.getOrder_id();
            Log.i("DeliverTwo的状态码", String.valueOf(deliverConfirmInfo.getCode()));
            //Log.i("DeliverTwo的错误信息", deliverConfirmInfo.getMsg());
            switch (deliverConfirmInfo.getCode()) {
                case 0:
                    Toast.makeText(DeliverTwoActivity.this, "可以投递", Toast.LENGTH_SHORT).show();
                    //GlobalData.setOrder_id(order_number);
                    Intent intent = new Intent(this, DeliverThreeActivity.class);
                    startActivity(intent);
                    break;
                case 30002:
                    Toast.makeText(DeliverTwoActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
                    break;
                case 15100:
                    Toast.makeText(DeliverTwoActivity.this, "单号已存在", Toast.LENGTH_SHORT).show();
                    break;
                    default:break;
            }//这会跳两个Toast，有问题。 余额不足&单号已存在
        }
        else
        {Toast.makeText(DeliverTwoActivity.this,"所选择的箱子规格没有空的了",Toast.LENGTH_LONG).show();}

    }


    /*public String getCabInfo(){
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("phone", phone));
        paramsList.add(new BasicNameValuePair("password", password));

        MyHttp http = new MyHttp(GlobalData.BASE_URL_1 + "/capp/login/normal", paramsList);
        return http.doPost();
    }*/

    public String SendDeliverInfoApi() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("uid", GlobalData.getUid()));
        Log.i("DeliverTwo中传入的uid",GlobalData.getUid());
        paramsList.add(new BasicNameValuePair("cabinet_code", GlobalData.getCabinet_code()));
        Log.i("DeliverTwo中传入的cabinet_code",GlobalData.getCabinet_code());
        paramsList.add(new BasicNameValuePair("cell_type",cell_type));
        Log.i("DeliverTwo中传入的cell_type",cell_type);
        paramsList.add(new BasicNameValuePair("exp_code",order_number));
        Log.i("DeliverTwo中传入的order_number",order_number);
        paramsList.add(new BasicNameValuePair("consignee_phone",receiver_phone_number));
        Log.i("DeliverTwo中传入的收件人号码",receiver_phone_number);

        Log.i("paramList中的值",paramsList.toString());
        MyHttp http = new MyHttp( GlobalData.BASE_URL_2 + "/capp/delivery/allocate_cell", paramsList);
        return http.doPost();
    }

    public boolean judge_number(String size){
        if(size.equals ("10901") && big == 0){ return false; }
        if(size.equals ("10902") && middle == 0){ return false; }
        if(size.equals ("10903") && small == 0){ return false; }
        if(size.equals ("10904") && sup_small == 0){ return false; }
        if(size.equals ("10901") && big != 0){return true;}
        if(size.equals ("10902") && middle != 0) {return true;}
        if(size.equals ("10903") && small != 0) {return true;}
        if(size.equals ("10904") && sup_small != 0){return true;}
        else return true;
    }
}


/*{
        "code":状态码,int类型
        0: 成功
        20000: 柜体不存在
        "msg": 错误信息, String类型
        "body":{
        "name": 柜体名称, String类型
        "addr": 柜体地址, String类型
        "avail_cells":[
        {
        "type":格口类型,int类型
        10901: 大
        10902: 中
        10903: 小
        10904: 超小,
        "idle_count":空闲数,int类型
        },
        {
        "type":"格口类型",int类型
        10901: 大
        10902: 中
        10903: 小
        10904: 超小,
        "idle_count":"空闲数",int类型
        },
        {
        "type":"格口类型", int类型
        10901: 大
        10902: 中
        10903: 小
        10904: 超小,
        "idle_count":"空闲数", int类型
        }...
        ],
        }
        }*/
