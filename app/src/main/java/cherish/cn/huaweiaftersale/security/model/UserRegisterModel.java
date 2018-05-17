package cherish.cn.huaweiaftersale.security.model;


import cherish.cn.huaweiaftersale.bean.BaseEntity;

/**
 * Created by Veryxyf on 2016-5-17.
 */
public class UserRegisterModel extends BaseEntity {

    protected String name;
    protected String postId;
    protected String orgId;
    protected String mobile;
    protected String password;

    public  UserRegisterModel(String name, String mobile, String password, String postId, String orgId){
        this.name=name;
        this.mobile=mobile;
        this.password=password;
        this.postId=postId;
        this.orgId=orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
