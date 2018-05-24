package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/5/15.
 */

public class SearchBean {
    private String number;
    private String  progress;
    private String recordId;

    public SearchBean(String number, String progress, String recordId) {
        this.number = number;
        this.progress = progress;
        this.recordId = recordId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
