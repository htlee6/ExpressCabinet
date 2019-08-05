package com.lihaotian.config;

public class DeliverConfirmInfo {

    public static String msg;
    public static String id;
    public static int code;
    public static String type;
    public static String desc;
    public static String order_id;
    public static String bin_code;





    public static void setMsg(String msg) {
        DeliverConfirmInfo.msg = msg;
    }

    public static void setId(String id) {
        DeliverConfirmInfo.id = id;
    }

    public static void setCode(int code) {
        DeliverConfirmInfo.code = code;
    }

    public static void setType(String type) {
        DeliverConfirmInfo.type = type;
    }

    public static void  setDesc(String desc) {
        DeliverConfirmInfo.desc = desc;
    }

    public static void setOrder_id(String order_id) {
        DeliverConfirmInfo.order_id = order_id;
    }

    public static void setBin_code(String bin_code){ DeliverConfirmInfo.bin_code=bin_code; }

    public static String getBin_code() { return bin_code; }

    public static String getMsg() {
        return msg;
    }

    public static String getId() {
        return id;
    }

    public static int getCode() {
        return code;
    }

    public static String getType() {
        return type;
    }

    public static String getDesc() {
        return desc;
    }

    public static String getOrder_id() {
        return order_id;
    }

}
