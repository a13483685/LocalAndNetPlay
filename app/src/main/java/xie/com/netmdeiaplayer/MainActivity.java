package xie.com.netmdeiaplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import xie.com.netmdeiaplayer.fragments.FragmentFactory;
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
    private static final int PERMISSION_REQUESTCODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
//        initFragment();
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

    private void permission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有授权
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUESTCODE);
        }
    }


    private void initData() {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            permission();//动态权限认证
        }



        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.createFragment(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

        vg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rd1:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.rd2:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.rd3:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.rd4:
                        vp.setCurrentItem(3);
                        break;
                }

//                vp.setCurrentItem(i);
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
