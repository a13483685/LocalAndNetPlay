package xie.com.netmdeiaplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicService extends Service {
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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

    private int getPosition() {
        return 0;
    }

    /**
     * 获取位置
     * @param pos
     */
    private void setPosition(int pos) {

    }

    /**
     * 暂停
     */
    private void pluse() {

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
    private void setMode(int mode) {

    }

    private int getMode() {
        return 0;
    }

    private void stop() {

    }

}
