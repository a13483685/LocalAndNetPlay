package xie.com.netmdeiaplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import xie.com.netmdeiaplayer.IMusicService;

public class MusicService extends Service {
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
}
