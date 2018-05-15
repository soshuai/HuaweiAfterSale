package cherish.cn.huaweiaftersale.okhttp.entity;

public class CheckLiveEntity {
    private String message;

    private LiveDataBean data;

    private String respCode;

    private boolean ok;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(LiveDataBean data) {
        this.data = data;
    }

    public LiveDataBean getData() {
        return this.data;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespCode() {
        return this.respCode;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean getOk() {
        return this.ok;
    }

}