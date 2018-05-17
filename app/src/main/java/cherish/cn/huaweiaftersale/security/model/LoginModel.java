package cherish.cn.huaweiaftersale.security.model;

/**
 * Created by Veryxyf on 2016-5-17.
 */
public class LoginModel {

    protected String mobile;
    protected String password;


    public LoginModel(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }
}
