package xie.com.netmdeiaplayer.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import xie.com.netmdeiaplayer.R;

/**
 * Created by MACHENIKE on 2018/4/22.
 */

public class AudioFragment extends BaseFragment {

    @Override
    protected void initData() {

    }

    @Override
    public View initView(LayoutInflater inflater, int res) {
        return inflater.inflate(R.layout.layout_fragment_audio,null);
    }
}
