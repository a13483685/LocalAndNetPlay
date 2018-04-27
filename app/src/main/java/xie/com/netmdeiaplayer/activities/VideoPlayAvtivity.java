package xie.com.netmdeiaplayer.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;  
import butterknife.ButterKnife;
import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.domain.MediaItem;


/**
 * Created by MACHENIKE on 2018/4/25.
 */

public class VideoPlayAvtivity extends Activity implements View.OnClickListener {
    public VideoView mVideoView;
    @BindView(R.id.buttry_level)
    ImageView buttryLevel;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private MyBroadCastRec myBroadCastRec;
    Uri uri;
    private static final int PRORESS = 1;
    @BindView(R.id.video_name)
    public TextView mTitleName;

    @BindView(R.id.iv_voice)
    public ImageView mVoice;

    @BindView(R.id.iv_info)
    public ImageView mInfo;

    @BindView(R.id.pre)
    public ImageView pre;

    @BindView(R.id.next)
    public ImageView next;

    @BindView(R.id.iv_back)
    public ImageView mBack;

    @BindView(R.id.stop_pluse)
    public ImageView mStopPluse;

    @BindView(R.id.sb_voice)
    SeekBar sbVoice;
    @BindView(R.id.sb_play_percent)
    SeekBar sbPlayPercent;

    private ArrayList<MediaItem> mVideoItems;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PRORESS:
                    int currentPosition = mVideoView.getCurrentPosition();
                    sbPlayPercent.setProgress(currentPosition);

                    tvTime.setText(getSystemTime());

                    handler.removeMessages(PRORESS);
                    handler.sendEmptyMessageDelayed(PRORESS, 1000);
                    break;
            }
        }
    };
    private int position;
    private MediaItem item;

    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        return format.format(new Date());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        myBroadCastRec = new MyBroadCastRec();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myBroadCastRec, mIntentFilter);
    }

    public void setBattery(int battery) {
        if (battery <= 0) {
            buttryLevel.setImageResource(R.drawable.ic_battery_0);
        } else if (battery >= 0 && battery <= 10) {
            buttryLevel.setImageResource(R.drawable.ic_battery_10);
        } else if (battery > 10 && battery <= 20) {
            buttryLevel.setImageResource(R.drawable.ic_battery_20);
        } else if (battery > 20 && battery <= 40) {
            buttryLevel.setImageResource(R.drawable.ic_battery_40);
        } else if (battery > 40 && battery <= 60) {
            buttryLevel.setImageResource(R.drawable.ic_battery_60);
        } else if (battery > 60 && battery <= 80) {
            buttryLevel.setImageResource(R.drawable.ic_battery_80);
        } else if (battery > 80 && battery <= 100) {
            buttryLevel.setImageResource(R.drawable.ic_battery_100);
        }
    }

    class MyBroadCastRec extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            setBattery(level);
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

        getData();
        setListener();
    }

    private void getData() {
        mVideoItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videoList");
        position = getIntent().getIntExtra("position",0);
        if (mVideoItems!=null && mVideoItems.size()>0){
            item = mVideoItems.get(position);
            mTitleName.setText(item.getName());
            mVideoView.setVideoPath(item.getData());
        }else if (uri != null) {
            mTitleName.setText(item.getName());

            mVideoView.setVideoURI(uri);
        }else {
            Toast.makeText(VideoPlayAvtivity.this,"数据为空",Toast.LENGTH_SHORT).show();
        }
    }

    private void setListener() {
        mVideoView.setOnPreparedListener(new PrepareLisener());
        mVideoView.setOnCompletionListener(new ComplateLisener());
        mVoice.setOnClickListener(this);
        mInfo.setOnClickListener(this);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mStopPluse.setOnClickListener(this);
        sbPlayPercent.setOnSeekBarChangeListener(new SeekBarListener());
    }

    class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mVideoView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    class ComplateLisener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
        }
    }

    class PrepareLisener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mVideoView.start();
            //1.获得总时长
            int duration = mVideoView.getDuration();
            sbPlayPercent.setMax(duration);
            handler.sendEmptyMessage(PRORESS);
        }
    }

    @Override
    protected void onDestroy() {
        if (myBroadCastRec != null) {
            unregisterReceiver(myBroadCastRec);
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.video_name:
                break;
            case R.id.iv_voice:
                break;
            case R.id.iv_info:
                break;
            case R.id.pre:
                break;
            case R.id.next:
                break;
            case R.id.iv_back:
                break;
            case R.id.stop_pluse:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                } else {
                    mVideoView.start();
                }
                break;
        }
    }
}
