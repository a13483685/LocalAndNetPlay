package xie.com.netmdeiaplayer.adapters;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.domain.MediaItem;

/**
 * Created by MACHENIKE on 2018/4/25.
 */

public class VideoAdapter extends BaseAdapter{

    private List<MediaItem> mediaItems;
    private Context ctx;

    public VideoAdapter(Context ctx,List<MediaItem> mediaItems)
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
            view = View.inflate(ctx, R.layout.item_video,null);
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
        holder.tv_size.setText(Formatter.formatFileSize(ctx,item.getSize()));
        return view;
    }



    static class ViewHolder
    {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }

}
