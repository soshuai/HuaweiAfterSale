package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/5/15.
 */

public class WorkBean {
    private String number;
    private int  time;
    private String  location;
    private String  customer;
    private String  phone;
    private String  states;
    private String  state;
    private String  recordId;

    public WorkBean(String number, int time, String location, String customer, String phone,String states,String recordId,String state) {
        this.number = number;
        this.time = time;
        this.location = location;
        this.customer = customer;
        this.phone = phone;
        this.states=states;
        this.recordId=recordId;
        this.state=state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getTime() {
        return time;
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

    public String getStates() {
        return states;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
