package cherish.cn.huaweiaftersale.bean;


/**
 * Created by Qianlp on 2017/2/15.
 */

public class SecurityUserEntity extends BaseEntity {

    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
