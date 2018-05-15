package cherish.cn.huaweiaftersale.okhttp.entity;

import java.util.List;

public class LiveDataBean {
    private List<LiveCheckList> liveCheckList;

    public void setLiveCheckList(List<LiveCheckList> liveCheckList) {
        this.liveCheckList = liveCheckList;
    }

    public List<LiveCheckList> getLiveCheckList() {
        return this.liveCheckList;
    }

}