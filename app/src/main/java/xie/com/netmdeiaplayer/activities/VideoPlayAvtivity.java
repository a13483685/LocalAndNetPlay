package xie.com.netmdeiaplayer.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.VideoView;

import xie.com.netmdeiaplayer.R;


/**
 * Created by MACHENIKE on 2018/4/25.
 */

public class VideoPlayAvtivity extends Activity {
    private VideoView mVideoView;
    Uri uri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        uri = getIntent().getData();

        mVideoView = findViewById(R.id.video_player);
        mVideoView.setOnPreparedListener(new PrepareLisener());
        mVideoView.setOnCompletionListener(new ComplateLisener());
        mVideoView.setMediaController(new MediaController(VideoPlayAvtivity.this,true));
        if(uri!=null)
        {
            mVideoView.setVideoURI(uri);
        }
    }

    class ComplateLisener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

        }
    }

    class PrepareLisener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mVideoView.start();
        }
    }
}
