package cherish.cn.huaweiaftersale.event;

/**
 * Created by Veryxyf on 2016-5-18.
 */
public class LogoutEvent {

    private boolean mPositive;

    public LogoutEvent(boolean positive) {
        super();
        this.mPositive = positive;
    }

    public boolean isPositive() {
        return this.mPositive;
    }
}
