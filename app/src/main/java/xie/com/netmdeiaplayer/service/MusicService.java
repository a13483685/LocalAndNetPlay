package xie.com.netmdeiaplayer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import xie.com.netmdeiaplayer.IMusicService;
import xie.com.netmdeiaplayer.R;
import xie.com.netmdeiaplayer.activities.MusicPlayerActivity;
import xie.com.netmdeiaplayer.domain.MediaItem;
import xie.com.netmdeiaplayer.utils.Utils;

public class MusicService extends Service {
    private ArrayList<MediaItem> mediaItems;
    private int position = 0;
    private MediaItem mediaItem;
    private MediaPlayer mediaPlayer = null;
    public static String open_music = "OPEN_MUSIC";
    ;
    private NotificationManager manager;
    public static int REPLAY_NORMAL = 0;
    public static int REPLAY_ALL = 1;
    public static int REPLAY_SINGLE = 2;
    private int mode = 0;


    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getLocalVideoData();
        mode = Utils.getPlayMode(this);
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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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

        @Override
        public boolean isPlaying() throws RemoteException {
            return mediaPlayer.isPlaying();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public void seekTo(int position) throws RemoteException {
            mediaPlayer.seekTo(position);
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
        int mode = getPlayMode();
        if (mode == MusicService.REPLAY_NORMAL) {
            position--;
            if (position < 0)
                position = mediaItems.size()-1;

        } else if (mode == MusicService.REPLAY_ALL) {
            position--;
            if (position < 0)
                position = mediaItems.size()-1;
        } else if (mode == MusicService.REPLAY_SINGLE) {
            position = position;
        }
        open(position);
    }

    /**
     * 下一曲
     */
    private void next() {
        int mode = getPlayMode();
        if (mode == MusicService.REPLAY_NORMAL) {
            position++;
            if (position < mediaItems.size()) {
            } else {
                position = 0;
            }
        } else if (mode == MusicService.REPLAY_ALL) {
            position++;
            if (position < mediaItems.size()) {
            } else {
                position = 0;
            }
        } else if (mode == MusicService.REPLAY_SINGLE) {
            position = position;
        }
        open(position);
    }

    private int getDuration() {
        return mediaPlayer.getDuration();
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
        mediaPlayer.pause();
    }

    /**
     * 播放
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void play() {
        mediaPlayer.start();
        //状态栏信息
        //这部分代码还不熟悉，关于pendingIntent相关的
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MusicPlayerActivity.class);
        intent.putExtra("Notification", true);//区分来至状态栏
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.notification_music_playing)
                .setContentTitle("小x播放器")
                .setContentIntent(pendingIntent)
                .setContentText("正在播放：" + getName())
                .build();
        manager.notify(1, notification);
    }

    /***
     * 模式设置
     * @param mode
     */
    private void setPlayMode(int mode) {
        this.mode = mode;
        Utils.putPlayMode(this, "playMode", this.mode);
        if (mode == MusicService.REPLAY_SINGLE) {
            mediaPlayer.setLooping(true);
        }else {
            mediaPlayer.setLooping(false);
        }
    }

    private int getPlayMode() {
        return Utils.getPlayMode(this);
    }

    private void stop() {
    }

    private void open(int position) {
        this.position = position;
        if (mediaItems != null && mediaItems.size() > 0) {
            mediaItem = mediaItems.get(position);
            if (mediaPlayer != null) {
//                mediaPlayer.release();
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

        } else {
            Toast.makeText(this, "还没有加载完成", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 得到艺术家
     *
     * @return
     */
    private String getAritist() {
        return mediaItem.getArist();
    }

    private String getAudioPath() {
        return "";
    }

    private String getName() {
        return mediaItem.getName();
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
                Cursor cursor = resolver.query(uri, obj, null, null, null);


                if (cursor != null) {
                    mediaItems = new ArrayList<>();
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(0);
                        MediaItem meidaItem = new MediaItem();
                        meidaItem.setName(name);

                        Long duration = cursor.getLong(1);
                        meidaItem.setDuration(duration);

                        Long size = cursor.getLong(2);
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

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                play();
            }

            Intent intent = new Intent();

            intent.setAction(open_music);
            sendBroadcast(intent);
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            next();
            return false;
        }
    }
}
