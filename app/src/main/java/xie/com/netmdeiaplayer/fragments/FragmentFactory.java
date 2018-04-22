package xie.com.netmdeiaplayer.fragments;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MACHENIKE on 2018/4/22.
 */

public class FragmentFactory {

    public static Map<Integer, Fragment> mFragments = new HashMap<>();

    public static Fragment createFragment(int pos) {
        Fragment mFragment = null;
        mFragment = mFragments.get(pos);
        if (mFragment != null) {
            return mFragment;
        } else {
            switch (pos) {
                case 0:
                    mFragment = new AudioFragment();
                    break;
                case 1:
                    mFragment = new NetAudioFragment();
                    break;
                case 2:
                    mFragment = new NetVideoFragment();
                    break;
                case 3:
                    mFragment = new VideoFragment();
                    break;
                default: {
                }
                mFragments.put(pos,mFragment);
//                return mFragment;
            }
        }
        return mFragment;
    }
}
