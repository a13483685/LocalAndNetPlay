package xie.com.netmdeiaplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import xie.com.netmdeiaplayer.R;

/**
 * Created by xiezheng on 2018/5/2.
 */

public class MusicPlayerActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplayer);
    }
}
