package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/3/29.
 */

public class TabInfoBean {
    private int storesCount;
    private int videoCount;
    private int videoonlineCount;
    private int collectCount;

    public TabInfoBean(int storesCount, int videoCount, int videoonlineCount, int collectCount) {
        this.storesCount = storesCount;
        this.videoCount = videoCount;
        this.videoonlineCount = videoonlineCount;
        this.collectCount = collectCount;
    }

    public int getStoresCount() {
        return storesCount;
    }

    public void setStoresCount(int storesCount) {
        this.storesCount = storesCount;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getVideoonlineCount() {
        return videoonlineCount;
    }

    public void setVideoonlineCount(int videoonlineCount) {
        this.videoonlineCount = videoonlineCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }
}
