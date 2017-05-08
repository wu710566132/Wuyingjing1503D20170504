package com.bw.wuyingjing1503d20170504;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 2016 on 2017/5/4.
 */

public class Utils {

    public static String inputstreamToString(InputStream is){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[]buff = new byte[1024];
        int len = 0;
        try {
            while ((len=is.read(buff))!= -1){
                baos.write(buff,0,len);


            }
        return baos.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

    return null;
    }


    public static boolean isWifi(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info!=null && info.getType() == manager.TYPE_WIFI){
            return true;
        }
        return false;
    }



}


