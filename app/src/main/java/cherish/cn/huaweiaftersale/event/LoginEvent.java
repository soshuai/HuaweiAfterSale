package cherish.cn.huaweiaftersale.event;

/**
 * Created by Veryxyf on 2016-5-18.
 */
public class LoginEvent {

    private boolean mSuccess; // true 表示登录成功，  false 表示登录失败

    public LoginEvent(boolean success) {
        this.mSuccess = success;
    }

    public boolean isSuccess() {
        return mSuccess;
    }
}
