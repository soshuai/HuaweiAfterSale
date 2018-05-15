package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/3/29.
 */

public class SubmitCheckLiveBean {
    public String id;
    public String note;
    public boolean passFlag;

    public SubmitCheckLiveBean(String id, String note, boolean passFlag) {
        this.id = id;
        this.note = note;
        this.passFlag = passFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isPassFlag() {
        return passFlag;
    }

    public void setPassFlag(boolean passFlag) {
        this.passFlag = passFlag;
    }
}
