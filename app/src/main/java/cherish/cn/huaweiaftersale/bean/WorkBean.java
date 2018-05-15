package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/5/15.
 */

public class WorkBean {
    private String number;
    private String  time;
    private String  location;
    private String  customer;
    private String  phone;

    public WorkBean(String number, String time, String location, String customer, String phone) {
        this.number = number;
        this.time = time;
        this.location = location;
        this.customer = customer;
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
