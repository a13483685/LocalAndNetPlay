package xie.com.netmdeiaplayer.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xie.com.netmdeiaplayer.R;


/**
 * Created by MACHENIKE on 2018/4/25.
 */

public class VideoPlayAvtivity extends Activity {
    private VideoView mVideoView;
    @BindView(R.id.video_name)
    private TextView mTitleName;
    @BindView(R.id.tv_time)
    private TextView mTime;

    @BindView(R.id.iv_voice)
    private ImageView mVoice;

//    @BindView(R.id.)
//    private ImageView mInfo;


    private

    Uri uri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        if(uri!=null)
        {
            mVideoView.setVideoURI(uri);
        }
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        uri = getIntent().getData();
        mVideoView = findViewById(R.id.video_player);
        mVideoView.setOnPreparedListener(new PrepareLisener());
        mVideoView.setOnCompletionListener(new ComplateLisener());
        mVideoView.setMediaController(new MediaController(VideoPlayAvtivity.this,true));
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
