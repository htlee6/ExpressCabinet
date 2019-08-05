package com.lihaotian.kuaidi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.lihaotian.utils.MyHttp;
import com.lihaotian.config.GlobalData;
import com.lihaotian.config.LoginInfo;
import com.lihaotian.utils.MyJsonParser;
import com.lihaotian.config.GlobalData;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private String phone;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //强制在主线程请求网络连接
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        et_username = (EditText) findViewById(R.id.input_phone);
        et_password = (EditText) findViewById(R.id.input_password);

    }

    //点击注册按钮，跳转到RegisterActivity
    public void onRegister(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    //点击忘记密码按钮，跳转到ForgetPasswordActivity
    public void onForgetPassword(View view){
        Intent intent = new Intent(this,ForgetPasswordActivity.class);
        startActivity(intent);
    }

    //点击登录按钮，跳转到MenuActivity
    public void onLogin(View view){

        phone = et_username.getText().toString();
        password = et_password.getText().toString();

        String resultString = loginApi();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo = new MyJsonParser(resultString).parseLoginInfo();
        switch (loginInfo.getCode()){
            case 0:
                Toast.makeText(LoginActivity.this,"登陆成功", Toast.LENGTH_SHORT).show();
                GlobalData.setUid(loginInfo.getId());
                GlobalData.setSid(loginInfo.getSid());
                Intent intent = new Intent(this,MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                et_username.setText("");
                et_password.setText("");
                startActivity(intent);
                break;
            case 20000:
                Toast.makeText(LoginActivity.this,"失败次数过多，您的账号被锁定", Toast.LENGTH_SHORT).show();
                break;
            case 20001:
                Toast.makeText(LoginActivity.this,"该账户不存在", Toast.LENGTH_SHORT).show();
                break;
            case 20009:
                Toast.makeText(LoginActivity.this,"密码错误", Toast.LENGTH_SHORT).show();
                break;
            case 20016:
                Toast.makeText(LoginActivity.this,"您的账户已被禁用", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }


    }

    public String loginApi() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("phone", phone));
        paramsList.add(new BasicNameValuePair("password", password));

        MyHttp http = new MyHttp(GlobalData.BASE_URL_1 + "/capp/login/normal", paramsList);
        return http.doPost();
    }
}
