package com.lihaotian.config;

public class CabinetConfirmInfo {

    private int code;
    private String msg;
    private String name;
    private String addr;
    public static int big_idle_count;
    public static int middle_idle_count;
    public static int small_idle_count;
    public static int supersmall_idle_count;





    public void setCode(int code) {
        this.code = code;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void setBig_idle_count(int big_idle_count){ CabinetConfirmInfo.big_idle_count=big_idle_count; }

    public static void setMiddle_idle_count(int middle_idle_count){ CabinetConfirmInfo.middle_idle_count=middle_idle_count; }

    public static void setSmall_idle_count(int small_idle_count){ CabinetConfirmInfo.small_idle_count=small_idle_count; }

    public static void setSupersmall_idle_count(int supersmall_idle_count){ CabinetConfirmInfo.supersmall_idle_count=supersmall_idle_count; }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    public static int getBig_idle_count() {
        return big_idle_count;
    }

    public static int getMiddle_idle_count() {
        return middle_idle_count;
    }

    public static int getSmall_idle_count() {
        return small_idle_count;
    }

    public static int getSupersmall_idle_count() {
        return supersmall_idle_count;
    }

}
