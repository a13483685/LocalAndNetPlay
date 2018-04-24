package xie.com.netmdeiaplayer.domain;

/**
 * Created by MACHENIKE on 2018/4/23.
 */

public class MediaItem {
    String name ;
    Long duration ;
    Long size ;
    String data ;
    String arist ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArist() {
        return arist;
    }

    public void setArist(String arist) {
        this.arist = arist;
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", data='" + data + '\'' +
                ", arist='" + arist + '\'' +
                '}';
    }
}
