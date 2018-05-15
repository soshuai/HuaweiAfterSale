package cherish.cn.huaweiaftersale.okhttp.entity;

/**
 * Created by veryw on 2018/5/9.
 */

public class DepartListBean {
    private String id;
    private String departname;
    private String description;
    private String orgCode;
    private String orgType;
    private String mobile;
    private String address;

    public DepartListBean() {}

    public DepartListBean(String id, String departname, String description, String orgCode, String orgType, String mobile, String address) {
        this.id = id;
        this.departname = departname;
        this.description = description;
        this.orgCode = orgCode;
        this.orgType = orgType;
        this.mobile = mobile;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
