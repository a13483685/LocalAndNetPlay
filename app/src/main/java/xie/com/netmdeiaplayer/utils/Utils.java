package xie.com.netmdeiaplayer.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by xiezheng on 2018/5/1.
 */

public class Utils {
    public static boolean isNetResouce(String uri){
        if(uri!=null)
        {
            uri = uri.toLowerCase();
            if(uri.startsWith("http")||uri.startsWith("rtsp")||uri.startsWith("mms")){
                return true;
            }
        }
        return false;
    }


    public static String getMillSeconfToHHMMSS(int millSec){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(millSec);
        return hms;
    }
}
