package xie.com.netmdeiaplayer.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xie.com.netmdeiaplayer.IMusicService;
import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.service.MusicService;
import xie.com.netmdeiaplayer.utils.Utils;

/**
 * Created by xiezheng on 2018/5/2.
 */

public class MusicPlayerActivity extends Activity {
    private static final int CURRENT_TIME = 1;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.seekbar_audio)
    SeekBar seekbarAudio;
    @BindView(R.id.btn_audio_playmode)
    Button btnAudioPlaymode;
    @BindView(R.id.btn_audio_pre)
    Button btnAudioPre;
    @BindView(R.id.btn_audio_start_pause)
    Button btnAudioStartPause;
    @BindView(R.id.btn_audio_next)
    Button btnAudioNext;
    @BindView(R.id.btn_lyrc)
    Button btnLyrc;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;


    int position;

    IMusicService service = null;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            service = IMusicService.Stub.asInterface(iBinder);
            if (service != null) {
                try {
                    if (!notification) {
                        service.open(position);
                    } else {
                        showViewDatas();
                    }

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private MyBroadcastReceiver myBroadcastReceiver;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int msgNum = msg.what;
            switch (msgNum) {
                case CURRENT_TIME:
                    try {
                        int currentPosition = service.getCurrentPosition();
                        seekbarAudio.setProgress(currentPosition);
                        tvTime.setText(Utils.getMillSeconfToHHMMSS(currentPosition) + "/" + Utils.getMillSeconfToHHMMSS(service.getDuration()));
                        handler.removeMessages(CURRENT_TIME);
                        handler.sendEmptyMessageDelayed(CURRENT_TIME, 1000);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private boolean notification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplayer);
        initView();
        initData();
        getData();
        bindAndStartService();
    }

    private void setPlayMode() {
        try {
            int mode = service.getPlayMode();
            if (mode == MusicService.REPLAY_NORMAL) {
                mode = MusicService.REPLAY_ALL;
//                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
                Toast.makeText(MusicPlayerActivity.this,"全部循环",Toast.LENGTH_SHORT).show();
            } else if (mode == MusicService.REPLAY_ALL) {
                mode = MusicService.REPLAY_SINGLE;
//                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_all_selector);
                Toast.makeText(MusicPlayerActivity.this,"单曲循环",Toast.LENGTH_SHORT).show();
            } else if (mode == MusicService.REPLAY_SINGLE) {
                mode = MusicService.REPLAY_NORMAL;
//                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_single_selector);
                Toast.makeText(MusicPlayerActivity.this,"顺序播放",Toast.LENGTH_SHORT).show();
            } else {
                mode = MusicService.REPLAY_NORMAL;
//                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
                Toast.makeText(MusicPlayerActivity.this,"顺序播放",Toast.LENGTH_SHORT).show();
            }
            service.setPlayMode(mode);
            showPlayMode();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    private void showPlayMode() {
        try {
            int mode = service.getPlayMode();
            if (mode == MusicService.REPLAY_NORMAL) {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
                Toast.makeText(MusicPlayerActivity.this,"顺序播放",Toast.LENGTH_SHORT).show();
            } else if (mode == MusicService.REPLAY_ALL) {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_all_selector);
                Toast.makeText(MusicPlayerActivity.this,"全部播放",Toast.LENGTH_SHORT).show();
            } else if (mode == MusicService.REPLAY_SINGLE) {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_single_selector);
                Toast.makeText(MusicPlayerActivity.this,"单曲播放",Toast.LENGTH_SHORT).show();
            } else {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
                Toast.makeText(MusicPlayerActivity.this,"顺序播放",Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验状态
     */
    private void checkPlayMode() {
        try {
            int mode = service.getPlayMode();
            if (mode == MusicService.REPLAY_NORMAL) {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
//                Toast.makeText(MusicPlayerActivity.this,"顺序播放",Toast.LENGTH_SHORT).show();
            } else if (mode == MusicService.REPLAY_ALL) {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_all_selector);
//                Toast.makeText(MusicPlayerActivity.this,"全部播放",Toast.LENGTH_SHORT).show();
            } else if (mode == MusicService.REPLAY_SINGLE) {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_single_selector);
//                Toast.makeText(MusicPlayerActivity.this,"单曲播放",Toast.LENGTH_SHORT).show();
            } else {
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
//                Toast.makeText(MusicPlayerActivity.this,"顺序播放",Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.open_music);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            showViewDatas();
            checkPlayMode();
        }
    }


    private void showViewDatas() {
        try {
            tvArtist.setText(service.getAritist());
            tvName.setText(service.getName());
            seekbarAudio.setMax(service.getDuration());
            seekbarAudio.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
            handler.sendEmptyMessage(CURRENT_TIME);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                try {
                    service.seekTo(progress);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        if (myBroadcastReceiver != null) {
            unregisterReceiver(myBroadcastReceiver);
            myBroadcastReceiver = null;
        }
        super.onDestroy();
    }

    private void bindAndStartService() {
        Intent intent = new Intent(MusicPlayerActivity.this, MusicService.class);
        intent.setAction("com.xie.service");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    private void getData() {
        //判断是否来自状态栏的点击
        notification = getIntent().getBooleanExtra("Notification", false);
        if (!notification) {
            position = getIntent().getIntExtra("position", 0);
        }
    }

    private void initView() {
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_icon, R.id.btn_audio_playmode, R.id.btn_audio_pre, R.id.btn_audio_start_pause, R.id.btn_audio_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                break;
            case R.id.btn_audio_playmode:
                setPlayMode();
                break;
            case R.id.btn_audio_pre:
                if(service!=null) {
                    try {
                        service.pre();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_audio_start_pause:
                if (service != null) {
                    try {
                        if (service.isPlaying()) {
                            service.pause();
                            btnAudioStartPause.setBackgroundResource(R.drawable.btn_audio_start_selector);
                        } else {
                            service.play();
                            btnAudioStartPause.setBackgroundResource(R.drawable.btn_audio_pause_selector);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.btn_audio_next:
                if (service!=null){
                    try {
                        service.next();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
