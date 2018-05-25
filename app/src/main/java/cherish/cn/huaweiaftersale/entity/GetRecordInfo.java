package cherish.cn.huaweiaftersale.entity;

public class GetRecordInfo {
    private String recordId;

    private String meetingName;

    private String userMobile;

    private String orderCode;

    private String userName;

    private int orderDate;

    private int minute;

    private String status;

    private String descp;
    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordId() {
        return this.recordId;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public String getDescp() {
        return this.descp;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingName() {
        return this.meetingName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

//    public void setOrderDate(int orderDate) {
//        this.orderDate = orderDate;
//    }
//
//    public int getOrderDate() {
//        return this.orderDate;
//    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserMobile() {
        return this.userMobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

}