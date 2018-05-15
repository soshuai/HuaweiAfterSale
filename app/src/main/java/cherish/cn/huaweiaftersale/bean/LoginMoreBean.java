package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/3/29.
 */

public class LoginMoreBean {
    private String name;
    private String password;

    public LoginMoreBean(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
