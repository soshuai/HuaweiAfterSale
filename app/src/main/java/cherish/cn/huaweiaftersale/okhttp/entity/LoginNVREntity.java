package cherish.cn.huaweiaftersale.okhttp.entity;

public class LoginNVREntity extends BaseApiEntity {

    private String message;
    private String respCode;
    private boolean ok;
    private NVREntity data;

    public LoginNVREntity(){}

    public LoginNVREntity(Integer code, String msg) {
        super(code, msg);
    }

    public LoginNVREntity( String message, String respCode, boolean ok, NVREntity data) {
//        super(result, msg);
        this.message = message;
        this.respCode = respCode;
        this.ok = ok;
        this.data = data;
    }
//

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


    public boolean getOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public NVREntity getData() {
        return data;
    }

    public void setData(NVREntity data) {
        this.data = data;
    }
}
