package com.lihaotian.config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpStatusInfo {

    private static int code;
    private static String msg;
    private static int count;

    public static ArrayList<HashMap<String, Object>> getOrder_list() {
        return order_list;
    }

    public static void setOrder_list(ArrayList<HashMap<String, Object>> order_list) {
        ExpStatusInfo.order_list = order_list;
    }

    public static ArrayList<HashMap<String, Object>> order_list = new ArrayList ();


    public int getCode() {
        return code;
    }

    public void setCode(int code) { this.code = code; }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


