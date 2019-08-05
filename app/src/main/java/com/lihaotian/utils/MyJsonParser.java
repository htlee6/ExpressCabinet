package com.lihaotian.utils;

import android.util.Log;

import com.lihaotian.JavaBean.GetExpApplyInfo;
import com.lihaotian.JavaBean.GetExpCheckInfo;
import com.lihaotian.config.DeliverConfirmInfo;
import com.lihaotian.config.DeliverSucceed;
import com.lihaotian.config.GlobalData;
import com.lihaotian.config.LoginInfo;
import com.lihaotian.config.CabinetConfirmInfo;
import com.lihaotian.config.ExpStatusInfo;
import com.lihaotian.config.RegisterInfo;
import com.lihaotian.config.RetrieveApplyInfo;
import com.lihaotian.config.VercodeInfo;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.lihaotian.config.CheckGetExpInfo;

public class MyJsonParser {
    private String jsonStr;

    public MyJsonParser(String str){
        this.jsonStr = str;
    }

    public LoginInfo parseLoginInfo(){
        LoginInfo loginInfo = new LoginInfo();
        try {
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            loginInfo.setCode(jsonObj.getInt("code"));
            JSONObject bodyobj = jsonObj.getJSONObject("body");
            if(bodyobj != null){
                loginInfo.setId(bodyobj.getString("id"));
                loginInfo.setSid(bodyobj.getJSONObject("session").getString("sid"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            loginInfo.setError(e.getMessage().toString());
        }
        return loginInfo;
    }

    public CabinetConfirmInfo parselCabinetConfirmInfo(){
        CabinetConfirmInfo cabinetConfirmInfo = new CabinetConfirmInfo();
        try{
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            cabinetConfirmInfo.setCode(jsonObj.getInt("code"));
            JSONObject bodyobj = jsonObj.getJSONObject("body");
            if(bodyobj != null){
                cabinetConfirmInfo.setName(bodyobj.getString("name"));
                cabinetConfirmInfo.setAddr(bodyobj.getString("addr"));
                //cabinetConfirmInfo.setBody_id(bodyobj.getString("id"));
                //cabinetConfirmInfo.setBody_code(bodyobj.getString("code"));
                JSONArray jsonArray_idlecount=new JSONArray(bodyobj.getString ("avail_cells"));
                for(int i=0;i<4;i++){
                    if( jsonArray_idlecount.getJSONObject (i).getInt ("type") == 10901){
                        CabinetConfirmInfo.setBig_idle_count( jsonArray_idlecount.getJSONObject (i).getInt ("idle_count"));
                    }
                    if( jsonArray_idlecount.getJSONObject (i).getInt ("type") == 10902){
                        CabinetConfirmInfo.setMiddle_idle_count( jsonArray_idlecount.getJSONObject (i).getInt ("idle_count"));
                    }
                    if( jsonArray_idlecount.getJSONObject (i).getInt ("type") == 10903){
                        CabinetConfirmInfo.setSmall_idle_count( jsonArray_idlecount.getJSONObject (i).getInt ("idle_count"));
                    }
                    if( jsonArray_idlecount.getJSONObject (i).getInt ("type") == 10904){
                        CabinetConfirmInfo.setSupersmall_idle_count( jsonArray_idlecount.getJSONObject (i).getInt ("idle_count"));
                    }

                }

                /*avail_cells没写进去，还没发现怎么用？*/
            }
        }   catch (Exception e){

            // TODO:handle exception
            e.printStackTrace();
        }
        return cabinetConfirmInfo;
    }

    public DeliverConfirmInfo parselDeliverConfirmInfo(){
        DeliverConfirmInfo deliverConfirmInfo = new DeliverConfirmInfo();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            System.out.println(jsonStr);
            deliverConfirmInfo.setCode (jsonObject.getInt("code"));
            JSONObject bodyobj = jsonObject.getJSONObject("body");
            if(bodyobj != null){
                deliverConfirmInfo.setId(bodyobj.getString("id"));
                deliverConfirmInfo.setBin_code(bodyobj.getString("code"));
                deliverConfirmInfo.setType(bodyobj.getString("type"));
                deliverConfirmInfo.setDesc(bodyobj.getString("desc"));
                deliverConfirmInfo.setOrder_id(bodyobj.getString("order_id"));



            }
        }catch (Exception e){
            e.printStackTrace();
            deliverConfirmInfo.setMsg(e.getMessage().toString());
        }
        return deliverConfirmInfo;
    }

    public ExpStatusInfo parselExpStatusInfo() {
        ExpStatusInfo localexpStatusInfo = new ExpStatusInfo();
        try
        {
            Object localObject = new JSONObject(this.jsonStr);
            localexpStatusInfo.setCode(((JSONObject)localObject).optInt("code"));
            localexpStatusInfo.setMsg(((JSONObject)localObject).optString("msg"));
            localObject = ((JSONObject)localObject).optJSONObject("body");
            if (localObject != null)
            {
                localexpStatusInfo.setCount(((JSONObject)localObject).optInt("count"));
                localObject = ((JSONObject)localObject).optJSONArray("order_list");
                if (localObject != null)
                {
                    ArrayList localArrayList = new ArrayList();
                    int i = 0;
                    for (;;)
                    {
                        if (i >= ((JSONArray)localObject).length())
                        {
                            localexpStatusInfo.setOrder_list(localArrayList);
                            return localexpStatusInfo;
                        }
                        HashMap localHashMap = new HashMap();
                        JSONObject localJSONObject1 = (JSONObject)((JSONArray)localObject).get(i);
                        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("addr_info");
                        localHashMap.put("exp_code", Integer.valueOf(localJSONObject1.optInt("exp_code")));
                        localHashMap.put("in_time", localJSONObject1.optString("in_time"));
                        localHashMap.put("addr", localJSONObject2.optString("addr"));
                        localHashMap.put("open_code", localJSONObject1.optString("open_code"));
                        localHashMap.put("id", localJSONObject1.optString("id"));
                        localArrayList.add(localHashMap);
                        i += 1;
                    }
                }
            }
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return localexpStatusInfo;}

    public DeliverSucceed parselDeliverSucceed(){
        DeliverSucceed deliverSucceed = new DeliverSucceed();
        try{
            JSONObject jsonObject = new JSONObject(this.jsonStr);
            deliverSucceed.setCode(jsonObject.getInt("code"));

        }catch (Exception e){
            e.printStackTrace();
            deliverSucceed.setMsg(e.getMessage().toString());
        }
        return deliverSucceed;
    }

    public RetrieveApplyInfo parselRetrieveApplyInfo(){
        RetrieveApplyInfo retrieveApplyInfo = new RetrieveApplyInfo();
        try {
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            retrieveApplyInfo.setCode(jsonObj.getInt("code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrieveApplyInfo;
    }

    public VercodeInfo parserForRegisterSendVercode() {
        VercodeInfo vercodeInfo = new VercodeInfo();
        try {
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            vercodeInfo.setCode(jsonObj.getInt("code"));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return vercodeInfo;

    }

    public RegisterInfo parserForRegisterSucceed() {
        RegisterInfo registerInfo = new RegisterInfo();
        try {
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            registerInfo.setCode(jsonObj.getInt("code"));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return registerInfo;

    }

    public GetExpCheckInfo parseForGetExpCheck() {
        GetExpCheckInfo getExpCheckInfo = new GetExpCheckInfo();
        try {
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            getExpCheckInfo.setCode(jsonObj.getInt("code"));
            getExpCheckInfo.setMsg (jsonObj.getString("msg"));
            JSONObject bodyobj = jsonObj.getJSONObject("body");
            getExpCheckInfo.setIs_retrieve (bodyobj.getBoolean ("is_retrieve"));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return getExpCheckInfo;
    }

    public GetExpApplyInfo parseForGetExpApply(){
        GetExpApplyInfo getExpApplyInfo = new GetExpApplyInfo();
        try {
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            getExpApplyInfo.setCode(jsonObj.getInt("code"));
            getExpApplyInfo.setMsg(jsonObj.getString("msg"));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return getExpApplyInfo;
    }

    public CheckGetExpInfo parselCheckGetExpInfo(){
        CheckGetExpInfo checkGetExpInfo=new CheckGetExpInfo ();
        try {
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            checkGetExpInfo.setCode (jsonObj.getInt("code"));
            checkGetExpInfo.setMsg (jsonObj.getString("msg"));
            JSONObject bodyobj = jsonObj.getJSONObject("body");
            checkGetExpInfo.setIs_retrieve (bodyobj.getBoolean ("is_retrieve"));
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        return checkGetExpInfo;

    }

}



               /* try {
                    JSONObject jsonObject = new JSONObject(responseUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                } */