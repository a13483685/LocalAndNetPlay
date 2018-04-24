package xie.com.netmdeiaplayer;

import android.app.Application;

/**
 * Created by MACHENIKE on 2018/4/24.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;
    public static MyApplication getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
