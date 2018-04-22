package xie.com.netmdeiaplayer.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.AsyncLayoutInflater;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import xie.com.netmdeiaplayer.R;

/**
 * Created by MACHENIKE on 2018/4/22.
 */

public class titlebar extends LinearLayout implements View.OnClickListener {
    private Context context;
    private View tv_search;

    private View rl_game;

    private View iv_recode;

    public titlebar(Context context) {
        this(context,null);
    }

    public titlebar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public titlebar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_search = getChildAt(1);
        rl_game = getChildAt(2);
        iv_recode = getChildAt(3);

        tv_search.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        iv_recode.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_search:
//                Toast.makeText(context,"搜索",Toast.LENGTH_SHORT).show();
                Log.e("xz","搜索");
            break;
            case R.id.rl_game:
//                Toast.makeText(context,"游戏",Toast.LENGTH_SHORT).show();
                Log.e("xz","游戏");
                break;
            case R.id.iv_recode:
//                Toast.makeText(context,"记录",Toast.LENGTH_SHORT).show();
                Log.e("xz","记录");
                break;

        }
    }
}
