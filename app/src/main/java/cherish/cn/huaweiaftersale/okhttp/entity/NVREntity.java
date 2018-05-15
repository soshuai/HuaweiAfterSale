package cherish.cn.huaweiaftersale.okhttp.entity;

import java.util.List;

/**
 * Created by veryw on 2018/5/9.
 */

public class NVREntity {
    private UserBeans tSUser;
    private List<DepartListBean> tSDepartList;

    public NVREntity() {
    }

    public NVREntity(UserBeans tSUser, List<DepartListBean> tSDepartList) {
        this.tSUser = tSUser;
        this.tSDepartList = tSDepartList;
    }

    public UserBeans gettSUser() {
        return tSUser;
    }

    public void settSUser(UserBeans tSUser) {
        this.tSUser = tSUser;
    }

    public List<DepartListBean> gettSDepartList() {
        return tSDepartList;
    }

    public void settSDepartList(List<DepartListBean> tSDepartList) {
        this.tSDepartList = tSDepartList;
    }
}
