package cherish.cn.huaweiaftersale.okhttp.entity;

import java.util.List;

/**
 * Created by veryw on 2018/5/11.
 */

public class DataBean {
    private List<LiveCheckDictListBean> liveCheckDictList;

    public DataBean(List<LiveCheckDictListBean> liveCheckDictList) {
        this.liveCheckDictList = liveCheckDictList;
    }

    public List<LiveCheckDictListBean> getLiveCheckDictList() {
        return liveCheckDictList;
    }

    public void setLiveCheckDictList(List<LiveCheckDictListBean> liveCheckDictList) {
        this.liveCheckDictList = liveCheckDictList;
    }
}

