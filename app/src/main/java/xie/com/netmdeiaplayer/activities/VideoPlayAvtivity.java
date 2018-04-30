package xie.com.netmdeiaplayer.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.domain.MediaItem;
import xie.com.netmdeiaplayer.view.VideoView;


/**
 * Created by MACHENIKE on 2018/4/25.
 */

public class VideoPlayAvtivity extends Activity implements View.OnClickListener {
    private static final int PRORESS = 1;
    private static final int HIDE_CONTROLLER_MSG = 2;

    public VideoView mVideoView;
    @BindView(R.id.buttry_level)
    ImageView buttryLevel;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private MyBroadCastRec myBroadCastRec;
    Uri uri;
    @BindView(R.id.video_name)
    public TextView mTitleName;

    @BindView(R.id.iv_mut)
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

    @BindView(R.id.full)
    public ImageView mFullScreen;

    @BindView(R.id.sb_voice)
    SeekBar sbVoice;
    @BindView(R.id.sb_play_percent)
    SeekBar sbPlayPercent;

    @BindView(R.id.control_laytout)
    public RelativeLayout mMediaContorllerLayout;

    private GestureDetector gestureDetector;

    private ArrayList<MediaItem> mVideoItems;

    private int position;
    private MediaItem item;
    private boolean isContorllerShown;
    private boolean isFullScreem = false;
    private int screemHeight;
    private int screemWidth;


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

                case HIDE_CONTROLLER_MSG:
                    hideMediaContorller();
            }
        }
    };
    private int mDefaultHeight;
    private int mDefaultWidth;
    private DisplayMetrics dm;
    private AudioManager am;
    private boolean isMut = false;
    private int currentVol;

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
    private void showMediaContorller(){
        mMediaContorllerLayout.setVisibility(View.VISIBLE);
        isContorllerShown = true;
    }

    private void hideMediaContorller() {
        mMediaContorllerLayout.setVisibility(View.GONE);
        isContorllerShown = false;
    }
    private void initData() {
        myBroadCastRec = new MyBroadCastRec();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myBroadCastRec, mIntentFilter);
        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
//                Toast.makeText(VideoPlayAvtivity.this,"我被单击了",Toast.LENGTH_LONG).show();
                if(!isContorllerShown)
                {
                    showMediaContorller();
                    handler.sendEmptyMessageDelayed(HIDE_CONTROLLER_MSG,4000);
                }else {
                    hideMediaContorller();
                    handler.removeMessages(HIDE_CONTROLLER_MSG);
                }

                return super.onSingleTapConfirmed(e);
//                stopAndPlay();
            }


            @Override
            public void onLongPress(MotionEvent e) {
                Toast.makeText(VideoPlayAvtivity.this,"我被长按了",Toast.LENGTH_LONG).show();
                super.onLongPress(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(VideoPlayAvtivity.this,"我被双击了",Toast.LENGTH_LONG).show();
                fullScrenAndDefault();
                return super.onDoubleTap(e);
            }

//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                Toast.makeText(VideoPlayAvtivity.this,"222",Toast.LENGTH_LONG).show();
//                return super.onDoubleTapEvent(e);
//            }
        });

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screemHeight = dm.heightPixels;
        screemWidth = dm.widthPixels;

    }

    private void fullScrenAndDefault() {
        if(!isFullScreem){
            isFullScreem = true;
            setFullScreem();
        }else {
            isFullScreem = false;
            setDefaulScreem();
        }
    }

    private void setFullScreem() {

//        DisplayMetrics dm = new DisplayMetrics();
        mVideoView.setSize(screemHeight,screemWidth);
    }

//           if ( mVideoWidth * height  < width * mVideoHeight ) {
//        //Log.i("@@@", "image too wide, correcting");
//        width = height * mVideoWidth / mVideoHeight;
//    } else if ( mVideoWidth * height  > width * mVideoHeight ) {
//        //Log.i("@@@", "image too tall, correcting");
//        height = width * mVideoHeight / mVideoWidth;
 //   }

    private void setDefaulScreem() {
        // for compatibility, we adjust size based on aspect ratio
        if ( mDefaultWidth * screemHeight  < screemWidth * mDefaultHeight ) {
            //Log.i("@@@", "image too wide, correcting");
            screemWidth = screemHeight * mDefaultWidth / mDefaultHeight;
        } else if ( mDefaultWidth * screemHeight  > screemWidth * mDefaultHeight ) {
            //Log.i("@@@", "image too tall, correcting");
            screemHeight = screemWidth * mDefaultHeight / mDefaultWidth;
        }
        mVideoView.setSize(screemHeight,screemWidth);
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
        setListener();
        getData();
    }

    private void getData() {
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        sbVoice.setMax(maxVolume);
        currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        sbVoice.setProgress(currentVol);


        mVideoItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videoList");
        position = getIntent().getIntExtra("position",0);
        if (mVideoItems !=null && mVideoItems.size()>0){
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
        mFullScreen.setOnClickListener(this);
        sbPlayPercent.setOnSeekBarChangeListener(new SeekBarListener());
        sbVoice.setOnSeekBarChangeListener(new SeekBarVoiceListen());

    }

    class SeekBarVoiceListen implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if(fromUser){
                upDateVol(progress,isMut);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDE_CONTROLLER_MSG);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDE_CONTROLLER_MSG,4000);
        }
    }

    private void upDateVol(int progress,boolean isMut) {
        if (isMut)
        {
            am.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
            currentVol = 0;
//            sbVoice.setProgress(0);

        }else {
            am.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            currentVol = progress;
            sbVoice.setProgress(currentVol);
        }
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
            playNextVideo();
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
            mDefaultHeight = mediaPlayer.getVideoHeight();
            mDefaultWidth = mediaPlayer.getVideoWidth();
            mVideoView.setSize(mDefaultHeight,mDefaultWidth);
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
            case R.id.iv_mut:
                isMut = !isMut;
                if(isMut)
                {
                    upDateVol(0,isMut);
                }
                break;
            case R.id.iv_info:
                break;
            case R.id.pre:
                playPreVideo();
                break;
            case R.id.next:
                playNextVideo();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.stop_pluse:
                stopAndPlay();
                break;
            case R.id.full:
                fullScrenAndDefault();
                break;
        }
        handler.removeMessages(HIDE_CONTROLLER_MSG);
        handler.sendEmptyMessageDelayed(HIDE_CONTROLLER_MSG,4000);
    }


    private void stopAndPlay() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        } else {
            mVideoView.start();
        }
    }

    private void playPreVideo() {
        if(position>0)
        {
            position--;
        }
        if(mVideoItems!=null&&mVideoItems.size()>0)
        {
            MediaItem item = mVideoItems.get(position);
            mVideoView.setVideoPath(item.getData());
            mTitleName.setText(item.getName());
        }
    }

    private void playNextVideo() {
        if(position<mVideoItems.size())
        {
            position++;
        }
        if(mVideoItems!=null&&mVideoItems.size()>0)
        {
            MediaItem item = mVideoItems.get(position);
            mVideoView.setVideoPath(item.getData());
            mTitleName.setText(item.getName());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        int curVol = 0;
        int totolHigh = 0;
        int totolVol = 0;
        float startY = 0;
        switch (event.getAction())
        {
            // 按下的时候记录1总音量 2总高 3当前音量
            case MotionEvent.ACTION_DOWN:
                handler.removeMessages(HIDE_CONTROLLER_MSG);
                curVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                totolHigh = Math.min(screemHeight,screemWidth);
                totolVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //声音的改变
                int dalta = (int)((event.getY() -startY)/totolHigh)*totolVol;
                Log.e("xie",String.valueOf(event.getY()));
                Log.e("xie",String.valueOf(dalta));


                int lastVol = (int) Math.min(Math.max(curVol+dalta,0),totolVol);
                if(dalta!=0)
                {
                    isMut =false;
                    upDateVol(lastVol,isMut);
                }
                break;

            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageAtTime(HIDE_CONTROLLER_MSG,4000);
                break;
        }
        return super.onTouchEvent(event);
    }
}
