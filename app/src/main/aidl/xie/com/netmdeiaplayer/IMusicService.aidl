// IMusicService.aidl
package xie.com.netmdeiaplayer;

// Declare any non-default types here with import statements

interface IMusicService {

  /**
     * 上一曲
     */
    void pre();
    /**
     * 下一曲
     */
    void next();

    int getDuration();

    /**
     * 获取位置
     *
     * @param pos
     */
    void setPosition(int pos);
    /**
     * 暂停
     */
    void pause();
    /**
     * 播放
     */
    void play();
    /***
     * 模式设置
     * @param mode
     */
    void setPlayMode(int mode);

    int getPlayMode();

    void stop();

    void open(int position);

    /**
     * 得到艺术家
     *
     * @return
     */
    String getAritist();

    String getAudioPath();

    String getName();

}
