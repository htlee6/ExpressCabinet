package com.lihaotian.kuaidi;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lihaotian.config.GlobalData;
import com.lihaotian.config.RegisterInfo;
import com.lihaotian.config.VercodeInfo;

import com.lihaotian.utils.MyHttp;
import com.lihaotian.utils.MyJsonParser;
import com.lihaotian.utils.TimeCount;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import android.content.Intent;

public class RegisterActivity extends AppCompatActivity {

    public TimeCount time;
    public Button btn_register_getvercode;
    public EditText et_register_tel;
    public String register_phone;
    public Button register_confirm;
    public EditText register_new_password;
    public String password;
    public EditText register_identifying_code;
    public String pcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_register_tel = (EditText)findViewById(R.id.register_phone_number);
        btn_register_getvercode = (Button)findViewById(R.id.get_register_identifying_code);
        register_confirm = (Button)findViewById(R.id.register_confirm);
    }

    public void getVercode(View view){
        time = new TimeCount(60*1000,1000,btn_register_getvercode);
        register_phone = et_register_tel.getText().toString().trim();

        String resultString = invokeRegisterSendVercodeApi();
        VercodeInfo vercodeInfo = new VercodeInfo();
       vercodeInfo = new MyJsonParser(resultString).parserForRegisterSendVercode();

       switch (vercodeInfo.getCode()){
           case 0:
               Toast.makeText(RegisterActivity.this,"发送成功",Toast.LENGTH_LONG).show();
               time.start();
               break;
           case 20002:
               Toast.makeText(RegisterActivity.this,"该手机号已是注册用户",Toast.LENGTH_LONG).show();
               break;
           case 20015:
               Toast.makeText(RegisterActivity.this,"该手机号已在快递员APP注册",Toast.LENGTH_LONG).show();
               break;
           default:break;
       }

    }

    public String invokeRegisterSendVercodeApi() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("phone",register_phone));

        MyHttp http = new MyHttp(GlobalData.BASE_URL_1 + "/capp/register/send_pcode", paramsList);
        return http.doPost();
    }

    public void onRegister(View view){
        register_new_password = (EditText)findViewById(R.id.register_new_password);
        password = register_new_password.getText().toString();
        register_identifying_code = (EditText)findViewById(R.id.register_identifying_code);
        pcode = register_identifying_code.getText().toString();

        String resultString = RegisterApi();
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo = new MyJsonParser(resultString).parserForRegisterSucceed();
        switch (registerInfo.getCode()){
            case 0:
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
        }
    }
    public String RegisterApi(){
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("phone", register_phone));
        Log.i("RegisterApi()中的注册手机号",register_phone);
        paramsList.add(new BasicNameValuePair("password", password));
        Log.i("RegisterApi()中的密码",password);
        paramsList.add(new BasicNameValuePair("pcode",pcode));
        Log.i("RegisterApi()中的验证码",pcode);

        MyHttp http = new MyHttp(GlobalData.BASE_URL_1 + "/capp/register/phone", paramsList);
        return http.doPost();
    }
}


/*btn_register_getvercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_phone;
                str_phone = et_register_tel.getText().toString().trim();
                if(str_phone.equals("")){
                    Toast.makeText(RegisterActivity.this,"请输入手机号",Toast.LENGTH_LONG).show();
                }
                else {
                    String str_json1 = invokeRegisterSendVercodeApi();
                    VercodeInfo vercodeInfo = new MyJsonParser(str_json1).parserForRegisterSendVercode();
                    switch (vercodeInfo.getCode()){
                        case 0:
                            Toast.makeText(RegisterActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                            time.start();
                            break;
                        case 20002:
                            Toast.makeText(RegisterActivity.this,"该手机号已是注册用户",Toast.LENGTH_LONG).show();
                            break;
                        case 20015:
                            Toast.makeText(RegisterActivity.this,"该手机号已在快递员APP注册",Toast.LENGTH_LONG).show();
                            break;
                        default:break;
                    }
                }
            }
        });*/