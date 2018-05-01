package xie.com.netmdeiaplayer.utils;

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
}
