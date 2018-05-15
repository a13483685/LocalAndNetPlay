package xie.com.netmdeiaplayer.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import xie.com.netmdeiaplayer.service.MusicService;

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

    /**
     * 得到播放模式
     * @param context
     * @return
     */
    public static int getPlayMode(Context context){
        SharedPreferences sp = context.getSharedPreferences("player",Context.MODE_PRIVATE);
        return sp.getInt("playMode", MusicService.REPLAY_NORMAL);
    }

    public static void putPlayMode(Context context,String key,int val){
        SharedPreferences sp = context.getSharedPreferences("player",Context.MODE_PRIVATE);
        sp.edit().putInt(key,val).commit();
    }

    public static void putString(Context context,String key,String val){
        SharedPreferences sp = context.getSharedPreferences("player",Context.MODE_PRIVATE);
        sp.edit().putString(key,val).commit();
    }
}
