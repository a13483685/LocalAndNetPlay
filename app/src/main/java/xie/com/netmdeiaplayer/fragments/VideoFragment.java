package xie.com.netmdeiaplayer.fragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import xie.com.netmdeiaplayer.R;
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
                lv_video.setAdapter(new VideoAdapter());
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
        return view;
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
                        MediaStore.Video.Media.DATA,//时间
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
    class VideoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mediaItems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder  ;
            if (view == null){
                view = View.inflate(getContext(),R.layout.item_video,null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) view.findViewById(R.id.im_play);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
                holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
                view.setTag(holder);
            }else {
                holder = (ViewHolder) view.getTag();
            }

            MediaItem item = mediaItems.get(i);
            holder.tv_name.setText(item.getName());
            holder.tv_time.setText(String.valueOf(item.getDuration()));
            holder.tv_size.setText(String.valueOf(item.getSize()));
            return view;
        }
    }


    static class ViewHolder
    {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }

}
