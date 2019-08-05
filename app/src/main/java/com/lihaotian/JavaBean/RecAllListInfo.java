//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lihaotian.JavaBean;

import java.util.ArrayList;
import java.util.HashMap;

public class RecAllListInfo {
    int code;
    int count;
    String msg;
    ArrayList<HashMap<String, Object>> order_list = new ArrayList();

    public RecAllListInfo() {
    }

    public int getCode() {
        return this.code;
    }

    public int getCount() {
        return this.count;
    }

    public String getMsg() {
        return this.msg;
    }

    public ArrayList<HashMap<String, Object>> getOrder_list() {
        return this.order_list;
    }

    public void setCode(int var1) {
        this.code = var1;
    }

    public void setCount(int var1) {
        this.count = var1;
    }

    public void setMsg(String var1) {
        this.msg = var1;
    }

    public void setOrder_list(ArrayList<HashMap<String, Object>> var1) {
        this.order_list = var1;
    }

    public String toString() {
        return "RecAllListInfo [code=" + this.code + ", msg=" + this.msg + ", count=" + this.count + ", order_list=" + this.order_list + "]";
    }
}
