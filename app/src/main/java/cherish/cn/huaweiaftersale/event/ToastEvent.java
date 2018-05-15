package cherish.cn.huaweiaftersale.event;

public class ToastEvent {

    protected String toast;

    public ToastEvent(String toast) {
        this.toast = toast;
    }

    public String getToast() {
        return toast;
    }
}