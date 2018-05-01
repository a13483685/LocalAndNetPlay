package xie.com.netmdeiaplayer.adapters;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.domain.MediaItem;

/**
 * Created by MACHENIKE on 2018/4/25.
 */

public class NetVideoAdapter extends BaseAdapter{

    private List<MediaItem> mediaItems;
    private Context ctx;

    public NetVideoAdapter(Context ctx, List<MediaItem> mediaItems)
    {
        this.mediaItems = mediaItems;
        this.ctx = ctx;
    }
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
            view = View.inflate(ctx, R.layout.item_net_video,null);
            holder = new ViewHolder();
            holder.iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            holder.net_video_name = (TextView) view.findViewById(R.id.net_video_name);
            holder.net_video_desc = (TextView) view.findViewById(R.id.net_video_desc);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        MediaItem item = mediaItems.get(i);

//        holder.iv_pic.setImageResource();
        holder.net_video_name.setText(item.getName());
        holder.net_video_desc.setText(item.getDesc());
        x.image().bind(holder.iv_pic,item.getImageUrl());
        return view;
    }



    static class ViewHolder
    {
        ImageView iv_pic;
        TextView net_video_name;
        TextView net_video_desc;
    }

}
