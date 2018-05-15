package cherish.cn.huaweiaftersale.okhttp.entity;

public class GetTokenEntity extends BaseApiEntity {
    private String data;
    private boolean success;
//    private String  code;

    public GetTokenEntity() {
        super();
    }

    public GetTokenEntity(Integer code, String msg) {
        super(code, msg);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
