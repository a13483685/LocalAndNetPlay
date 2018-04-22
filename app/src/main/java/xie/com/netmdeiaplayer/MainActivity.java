package xie.com.netmdeiaplayer;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    RadioButton bt1 = null;
    RadioButton bt2 = null;
    RadioButton bt3 = null;
    RadioButton bt4 = null;

    ViewParent vp = null;
    ViewGroup vg = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();



    }

    private void initView() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        vg = findViewById(R.id.vg);
        bt1 = findViewById(R.id.rd1);
        bt2 = findViewById(R.id.rd2);
        bt3 = findViewById(R.id.rd3);
        bt4 = findViewById(R.id.rd4);
        vp = findViewById(R.id.vp);
    }



}
