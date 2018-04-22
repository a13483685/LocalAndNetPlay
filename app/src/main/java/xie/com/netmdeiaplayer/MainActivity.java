package xie.com.netmdeiaplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import xie.com.netmdeiaplayer.fragments.AudioFragment;
import xie.com.netmdeiaplayer.fragments.BaseFragment;
import xie.com.netmdeiaplayer.fragments.NetAudioFragment;
import xie.com.netmdeiaplayer.fragments.NetVideoFragment;
import xie.com.netmdeiaplayer.fragments.VideoFragment;

public class MainActivity extends AppCompatActivity {
    RadioButton bt1 = null;
    RadioButton bt2 = null;
    RadioButton bt3 = null;
    RadioButton bt4 = null;

    ViewPager vp = null;
    RadioGroup vg = null;
    List<BaseFragment> fragments = new ArrayList<>();
    private AudioFragment audioFragment;
    private NetAudioFragment netAudioFragment;
    private NetVideoFragment netVideoFragment;
    private VideoFragment videoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initFragment();
        initData();
    }

    private void initFragment() {
        audioFragment = new AudioFragment();
        netAudioFragment = new NetAudioFragment();
        netVideoFragment = new NetVideoFragment();
        videoFragment = new VideoFragment();
        fragments.add(audioFragment);
        fragments.add(netAudioFragment);
        fragments.add(netVideoFragment);
        fragments.add(videoFragment);
    }

    private void initData() {

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        vg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                vp.setCurrentItem(i);
            }
        });

    }

    private void initView() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        vg =  findViewById(R.id.vg);
        bt1 = findViewById(R.id.rd1);
        bt2 = findViewById(R.id.rd2);
        bt3 = findViewById(R.id.rd3);
        bt4 = findViewById(R.id.rd4);
        vp = findViewById(R.id.vp);
    }

}
