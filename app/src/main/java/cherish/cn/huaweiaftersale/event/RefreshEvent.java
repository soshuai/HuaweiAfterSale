package cherish.cn.huaweiaftersale.event;

/**
 * Created by veryw on 2018/5/25.
 */

public class RefreshEvent {
    private boolean refresh;

    public RefreshEvent(boolean refresh) {
        this.refresh = refresh;
    }

    public boolean isRefresh() {
        return refresh;
    }
}
