package cherish.cn.huaweiaftersale.okhttp.entity;

/**
 * Created by veryw on 2018/5/9.
 */

public class VideoCheckDictEntity {
    private String message;
    private DataBean data;
    private String respCode;
    private boolean ok;

//    public VideoCheckDictEntity(Integer result, String msg) {
//        super(result, msg);
//    }


    public VideoCheckDictEntity(String message, DataBean data, String respCode, boolean ok) {
        this.message = message;
        this.data = data;
        this.respCode = respCode;
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
