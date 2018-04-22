package xie.com.netmdeiaplayer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MACHENIKE on 2018/4/22.
 */

public abstract class BaseFragment extends Fragment {
    int res;
    Context ctx;

    public BaseFragment(){

    }

    public BaseFragment(Context ctx, int res) {
        this.res = res;
        this.ctx = ctx;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater,this.res);
        initData();
        return view;
    }

    protected abstract void initData();


    public abstract View initView(LayoutInflater inflater,int res) ;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
