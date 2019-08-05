package com.lihaotian.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MyHttp {
    private String url;
    private List<NameValuePair> paramsList = new ArrayList<NameValuePair>();

    public MyHttp(String url, List<NameValuePair> paramsList){
        this.url = url;
        this.paramsList = paramsList;
    }

    public String doPost(){
        String result = "";
        try {
            HttpPost httpRequest = new HttpPost(url);
            httpRequest.setHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");
            httpRequest.setEntity(new UrlEncodedFormEntity(paramsList,HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
            Log.e("httpResponse",httpResponse.getEntity().toString());

            if(httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            result = e.getMessage().toString();
        }
        Log.i("MyHttp",result);
        return result;
    }
}
