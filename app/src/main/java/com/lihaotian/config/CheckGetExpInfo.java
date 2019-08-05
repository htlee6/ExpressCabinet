package com.lihaotian.config;

public class CheckGetExpInfo {
    private int code;
    private String msg;
    private Boolean is_retrieve;

    public void setCode(int code) {
        this.code = code;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public void setIs_retrieve(Boolean is_retrieve){
        this.is_retrieve=is_retrieve;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getIs_retrieve() {
        return is_retrieve;
    }
}
