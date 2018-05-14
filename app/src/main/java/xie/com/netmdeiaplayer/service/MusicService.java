package xie.com.netmdeiaplayer.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import xie.com.netmdeiaplayer.IMusicService;
import xie.com.netmdeiaplayer.domain.MediaItem;

public class MusicService extends Service {
    private ArrayList<MediaItem> mediaItems;
    private int position = 0;
    private MediaItem mediaItem;
    private MediaPlayer mediaPlayer = null;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getLocalVideoData();
    }

    private IMusicService.Stub stub = new IMusicService.Stub() {
        MusicService service = MusicService.this;


        @Override
        public void pre() throws RemoteException {
            service.pre();
        }

        @Override
        public void next() throws RemoteException {
            service.next();
        }

        @Override
        public int getDuration() throws RemoteException {
            return service.getDuration();
        }

        @Override
        public void setPosition(int pos) throws RemoteException {
            service.setPosition(pos);
        }

        @Override
        public void pause() throws RemoteException {
            service.pause();
        }

        @Override
        public void play() throws RemoteException {
            service.play();
        }

        @Override
        public void setPlayMode(int mode) throws RemoteException {
            service.setPlayMode(mode);
        }

        @Override
        public int getPlayMode() throws RemoteException {
            return service.getPlayMode();
        }

        @Override
        public void stop() throws RemoteException {
            service.stop();
        }

        @Override
        public void open(int position) throws RemoteException {
            service.open(position);
        }

        @Override
        public String getAritist() throws RemoteException {
            return service.getAritist();
        }

        @Override
        public String getAudioPath() throws RemoteException {
            return service.getAudioPath();
        }

        @Override
        public String getName() throws RemoteException {
            return service.getName();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return stub;
    }

    /**
     * 上一曲
     */
    private void pre() {

    }

    /**
     * 下一曲
     */
    private void next() {

    }

    private int getDuration() {
        return 0;
    }

    /**
     * 获取位置
     *
     * @param pos
     */
    private void setPosition(int pos) {

    }

    /**
     * 暂停
     */
    private void pause() {

    }

    /**
     * 播放
     */
    private void play() {
        mediaPlayer.start();
    }

    /***
     * 模式设置
     * @param mode
     */
    private void setPlayMode(int mode) {

    }

    private int getPlayMode() {
        return 0;
    }

    private void stop() {
    }

    private void open(int position) {
        this.position = position;
        if(mediaItems!=null && mediaItems.size()>0)
        {
            mediaItem = mediaItems.get(position);
            if(mediaPlayer!=null)
            {
                mediaPlayer.release();
                mediaPlayer.reset();
            }
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(mediaItem.getData());
                mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
                mediaPlayer.setOnCompletionListener(new MyOnCompletionListener());
                mediaPlayer.setOnErrorListener(new MyOnErrorListener());
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(this,"还没有加载完成",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 得到艺术家
     *
     * @return
     */
    private String getAritist() {
        return "";
    }

    private String getAudioPath() {
        return "";
    }

    private String getName() {
        return "";
    }


    public void getLocalVideoData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
//                MediaItem meidaItem = new MediaItem();
                ContentResolver resolver = getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
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
            }
        }.start();

    }
    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            play();
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            next();
            return false;
        }
    }
}
