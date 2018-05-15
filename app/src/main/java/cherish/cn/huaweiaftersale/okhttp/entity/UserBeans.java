package cherish.cn.huaweiaftersale.okhttp.entity;

/**
 * Created by veryw on 2018/5/9.
 */

public class UserBeans {
    private String id;
    private String userName;
    private boolean checkFlag;//是否可以检核

    public UserBeans(String id, String userName,boolean checkFlag) {
        this.id = id;
        this.userName = userName;
        this.checkFlag=checkFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }
}
