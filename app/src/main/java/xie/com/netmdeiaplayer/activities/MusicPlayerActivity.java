package xie.com.netmdeiaplayer.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xie.com.netmdeiaplayer.IMusicService;
import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.service.MusicService;

/**
 * Created by xiezheng on 2018/5/2.
 */

public class MusicPlayerActivity extends Activity {
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

    int position ;

    IMusicService service = null;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            service = IMusicService.Stub.asInterface(iBinder);
            if (service!=null)
            {
                try {
                    service.open(position);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplayer);
        initView();
        getData();
        bindAndStartService();
    }

    private void bindAndStartService() {
        Intent intent = new Intent(MusicPlayerActivity.this,MusicService.class);
        intent.setAction("com.xie.service");
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    private void getData() {
        position = getIntent().getIntExtra("position",0);
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
                break;
            case R.id.btn_audio_pre:
                break;
            case R.id.btn_audio_start_pause:
                break;
            case R.id.btn_audio_next:
                break;
        }
    }
}
