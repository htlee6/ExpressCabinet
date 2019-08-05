package com.lihaotian.config;

public class GlobalData {
    private static String uid;
    private static String sid;
    private static String cabinet_code;
    private static String order_id;
    public static final String BASE_URL_1 = "http://101.200.89.170:9000";
    public static final String BASE_URL_2 = "http://101.200.89.170:9002";
    public static final String BASE_URL_3 = "http://api.ec-logistics.cn/cabzoo/capp/retrieve";


    public static String getUid() {
        return uid;
    }

    public static String getSid(){
        return sid;
    }

    public static String getCabinet_code()  { return cabinet_code; }

    public static void setUid(String uid){
        GlobalData.uid = uid;
    }

    public static void setSid(String sid){
        GlobalData.sid = sid;
    }
    public static void setCabinet_code(String cabinet_code){
        GlobalData.cabinet_code=cabinet_code;
    }

    public static void setOrder_id(String order_id) {
        GlobalData.order_id = order_id;
    }

    public static String getOrder_id() {
        return order_id;
    }
}
