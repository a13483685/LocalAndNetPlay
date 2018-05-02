package xie.com.netmdeiaplayer.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.activities.VideoPlayAvtivity;
import xie.com.netmdeiaplayer.adapters.NetVideoAdapter;
import xie.com.netmdeiaplayer.domain.MediaItem;
import xie.com.netmdeiaplayer.utils.Constants;

/**
 * Created by MACHENIKE on 2018/4/22.
 */

public class NetVideoFragment extends BaseFragment {

    @BindView(R.id.lv_netvideo)
    public ListView lv_netvideo;

    @BindView(R.id.net_pb)
    public ProgressBar net_pb;

    @BindView(R.id.tv_net_no_content)
    public TextView tv_net_no_content;

    private ArrayList<MediaItem> mediaItems;

    private Unbinder unbinder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        final RequestParams params = new RequestParams(Constants.NET_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String s) {
                LogUtil.e("load success !================"+s);
                mediaItems = parseJson(s);
//                lv_netvideo.setAdapter(new NetVideoAdapter(getContext(),mediaItems));
                if(mediaItems!=null && mediaItems.size()>0)
                {
                    net_pb.setVisibility(View.GONE);
                    tv_net_no_content.setVisibility(View.GONE);
                    lv_netvideo.setAdapter(new NetVideoAdapter(getContext(),mediaItems));
                }else {
                    net_pb.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                LogUtil.e("load error !================"+throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private ArrayList<MediaItem> parseJson(String s) {
        ArrayList<MediaItem> mediaItems = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(s);
            JSONArray trailers = object.optJSONArray("trailers");
            for (int i =0;i<trailers.length();i++)
            {
              JSONObject jsonObjectItem = (JSONObject) trailers.get(i);
              if(jsonObjectItem!=null)
              {
                  MediaItem mediaItem = new MediaItem();
                  String movieName = jsonObjectItem.getString("movieName");
                  mediaItem.setName(movieName);


                  String videoTitle = jsonObjectItem.getString("videoTitle");
                  mediaItem.setDesc(videoTitle);

                  String coverImg = jsonObjectItem.getString("coverImg");
                  mediaItem.setImageUrl(coverImg);

                  String hightUrl = jsonObjectItem.getString("hightUrl");
                  mediaItem.setData(hightUrl);

                  mediaItems.add(mediaItem);
              }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mediaItems;
    }


    @Override
    public View initView(LayoutInflater inflater, int res) {
        LogUtil.e("网络视频的数据被初始化了。。。");

        View view = inflater.inflate(R.layout.layout_fragment_netvideo, null);
        unbinder = ButterKnife.bind(this,view);

        lv_netvideo.setOnItemClickListener(new ItemClickLisener());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class ItemClickLisener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MediaItem item = mediaItems.get(i);
            //调取手机上的播放器
//            Intent intent = new Intent();
//            intent.setDataAndType(Uri.parse(item.getData()),"video/*");
//            getContext().startActivity(intent);

            //调用系统的音乐播放器
            Intent intent = new Intent(getContext(), VideoPlayAvtivity.class);
            intent.setDataAndType(Uri.parse(item.getData()),"video/*");
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("videoList", (Serializable) mediaItems);
            intent.putExtras(mBundle);
            intent.putExtra("position",i);
            getContext().startActivity(intent);
        }
    }
}
