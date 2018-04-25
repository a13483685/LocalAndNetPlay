package xie.com.netmdeiaplayer.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.adapters.VideoAdapter;
import xie.com.netmdeiaplayer.domain.MediaItem;

/**
 * Created by MACHENIKE on 2018/4/22.
 */

public class VideoFragment extends BaseFragment {
    private ListView lv_video;
    private ProgressBar pb;
    private TextView tv_content;
    private Context context;
    private List<MediaItem> mediaItems = new ArrayList<>();
    private android.os.Handler mHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mediaItems!=null && mediaItems.size()>0)
            {
                //有数据设置适配器
                //隐藏文本
                lv_video.setAdapter(new VideoAdapter(getContext(),mediaItems));
                tv_content.setVisibility(View.GONE);

            }else {
                //没有数据 文本显示
            }
            pb.setVisibility(View.GONE);
        }
    };


//    public VideoFragment(Context ctx) {
//        this.context = ctx;
//    }

    @Override
    protected void initData() {
        getLocalVideoData();
    }

    @Override
    public View initView(LayoutInflater inflater, int res) {
        View view = inflater.inflate(R.layout.layout_fragment_video, null);
        lv_video = view.findViewById(R.id.lv_video);
        pb = view.findViewById(R.id.pb);
        tv_content = view.findViewById(R.id.tv_no_content);
        lv_video.setOnItemClickListener(new ItemClickLisener());
        return view;
    }

    class ItemClickLisener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MediaItem item = mediaItems.get(i);
            //调取播放器
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(item.getData()),"video/*");
            getContext().startActivity(intent);
        }
    }
    public void getLocalVideoData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
//                MediaItem meidaItem = new MediaItem();
                ContentResolver resolver = getContext().getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] obj = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sdcard中的名称
                        MediaStore.Video.Media.DURATION,//时长
                        MediaStore.Video.Media.SIZE,//大小
                        MediaStore.Video.Media.DATA,//数据
                        MediaStore.Video.Media.ARTIST//作者
                };
                Cursor cursor  = resolver.query(uri, obj, null, null, null);


                if (cursor != null) {
                    mediaItems = new ArrayList<>();
                    while (cursor.moveToNext()) {
                            String name = cursor.getString(0);
                            MediaItem meidaItem = new MediaItem();
                            meidaItem.setName(name);

                            Long duration = cursor.getLong(1);
                            meidaItem.setDuration(duration);

                            Long size =cursor.getLong(2);
                            meidaItem.setSize(size);

                            String data = cursor.getString(3);
                            meidaItem.setData(data);

                            String arist = cursor.getString(4);
                            meidaItem.setArist(arist);
                            mediaItems.add(meidaItem);

                    }
                    cursor.close();
                }
                    mHandler.sendEmptyMessage(0);
            }
        }.start();

    }




}
