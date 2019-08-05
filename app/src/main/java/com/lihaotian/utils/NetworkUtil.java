package com.lihaotian.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {
    public NetworkUtil() {
    }

    public static boolean isNetworkAvailable(Context var0) {
        ConnectivityManager var2 = (ConnectivityManager)var0.getSystemService("connectivity");
        if (var2 != null) {
            try {
                if (var2.getActiveNetworkInfo().isAvailable()) {
                    System.out.println("isAvailable");
                    return true;
                }
            } catch (Exception var1) {
                System.out.println("" + var1);
            }
        }

        return false;
    }
}
