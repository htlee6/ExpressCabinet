package com.lihaotian.kuaidi;

import android.annotation.SuppressLint;
import android.net.Network;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lihaotian.config.CheckGetExpInfo;
import com.lihaotian.config.ExpStatusInfo;
import com.lihaotian.config.GlobalData;
import com.lihaotian.config.NetWorkUtil;
import com.lihaotian.config.RetrieveApplyInfo;
import com.lihaotian.utils.MyHttp;
import com.lihaotian.utils.MyJsonParser;
import com.lihaotian.config.NetWorkUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetExpActivity extends AppCompatActivity {

    private String uid;
    private String order_id;
    private String sid;
    private String status;

    private ListView lv;
    private ArrayList<HashMap<String,Object>> arrayList_getexp;

    ExpStatusInfo expStatusInfo = new ExpStatusInfo();
    myAdapter adapter;
    PullToRefreshListView lv_getexp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        uid = GlobalData.getUid();
        status = "1";
        sid = GlobalData.getSid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_exp);
        lv_getexp = (PullToRefreshListView) findViewById(R.id.lv_getexp);
        adapter = new myAdapter();
        lv_getexp.setAdapter(adapter);
        String resultString = getExpInfoApi();
        expStatusInfo = new MyJsonParser(resultString).parselExpStatusInfo();
        arrayList_getexp = ExpStatusInfo.getOrder_list();
        adapter.setList(arrayList_getexp);
        Log.i("count", String.valueOf(expStatusInfo.getCount()));

        //强制在主线程请求网络连接
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lv_getexp.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>>() {
                    protected ArrayList<HashMap<String, Object>> doInBackground(Void... param) {
                        try {
                            String resultString = getExpInfoApi();
                            expStatusInfo = new MyJsonParser(resultString).parselExpStatusInfo();
                            arrayList_getexp = ExpStatusInfo.getOrder_list();
                            Thread.sleep(1000L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return GetExpActivity.this.arrayList_getexp;
                    }

                    protected void onPostExecute(ArrayList<HashMap<String, Object>> paramAnonymous2ArrayList) {
                        GetExpActivity.this.adapter.setList(paramAnonymous2ArrayList);
                        GetExpActivity.this.lv_getexp.onRefreshComplete();
                        //super.onPostExecute(paramAnonymous2ArrayList);
                    }
                }.execute(new Void[0]);
            }
        });
        this.lv_getexp.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        this.lv_getexp.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        this.lv_getexp.getLoadingLayoutProxy(true, false).setReleaseLabel("放开刷新...");
    }


    public String getExpInfoApi () {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("uid", uid));
        paramsList.add(new BasicNameValuePair("status", status));
        paramsList.add(new BasicNameValuePair("sid", sid));
        MyHttp http = new MyHttp(GlobalData.BASE_URL_2 + "/sexp/order/all/list", paramsList);
        return http.doPost();
    }

    public String retrieveApplyApi () {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("uid", uid));
        paramsList.add(new BasicNameValuePair("order_id", order_id));
        MyHttp http = new MyHttp(GlobalData.BASE_URL_3 + "/apply", paramsList);
        return http.doPost();
    }

    public String checkgetexpApi(){
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("uid", uid));
        paramsList.add(new BasicNameValuePair("order_id", order_id));
        MyHttp http = new MyHttp(GlobalData.BASE_URL_3 + "/check", paramsList);
        return http.doPost();
    }


    class myAdapter extends BaseAdapter {

        private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        public ArrayList<HashMap<String, Object>> getList() {
            return list;
        }

        public void setList(ArrayList<HashMap<String, Object>> list) {
            this.list = list;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView=null;
            View view = View.inflate(GetExpActivity.this, R.layout.adapter_getexp, null);
            TextView tv_getExp_expnum = (TextView) view.findViewById(R.id.tv_getExp_expnum);
            TextView tv_getExp_address = (TextView) view.findViewById(R.id.tv_getExp_address);
            TextView tv_getExp_date = (TextView) view.findViewById(R.id.tv_getExp_date);
            TextView tv_getExp_pwd = (TextView) view.findViewById(R.id.tv_getExp_pwd);

            tv_getExp_expnum.setText(list.get(position).get("id").toString());
            tv_getExp_address.setText(list.get(position).get("addr").toString());
            tv_getExp_date.setText(list.get(position).get("in_time").toString());
            tv_getExp_pwd.setText(list.get(position).get("open_code").toString());

            Button btn_getexp_opencell = (Button) view.findViewById(R.id.btn_getexp_opencell);
            btn_getexp_opencell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uid= GlobalData.getUid ();
                    order_id=list.get(position).get("id").toString();
                    String resultString=retrieveApplyApi();
                    RetrieveApplyInfo retrieveApplyInfo= new RetrieveApplyInfo();
                    retrieveApplyInfo = new MyJsonParser(resultString).parselRetrieveApplyInfo();
                    switch (retrieveApplyInfo.getCode ()){
                        case 0:
                            resultString=checkgetexpApi();
                            CheckGetExpInfo checkGetExpInfo=new CheckGetExpInfo ();
                            checkGetExpInfo = new MyJsonParser(resultString).parselCheckGetExpInfo();
                            switch (checkGetExpInfo.getCode ()) {
                                case 0:
                                    Toast.makeText (GetExpActivity.this, "箱门已开，订单完成，请随手关好箱门", Toast.LENGTH_LONG).show ();
                                case 15101:
                                    Toast.makeText(GetExpActivity.this,"该订单不存在",Toast.LENGTH_LONG).show();
                                default :break;
                            }


                        case 20001:
                            Toast.makeText(GetExpActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                        case 10001:
                            Toast.makeText(GetExpActivity.this, "柜体无效", Toast.LENGTH_SHORT).show();
                        case 10002:
                            Toast.makeText(GetExpActivity.this, "格口无效", Toast.LENGTH_SHORT).show();
                        case 15101:
                            Toast.makeText(GetExpActivity.this, "订单不存在", Toast.LENGTH_SHORT).show();
                        default :break;
                    }


                }

            });
            return view;
        }
    }}
