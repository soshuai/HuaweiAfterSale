package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/5/15.
 */

public class SearchBean {
    private String number;
    private String  progress;

    public SearchBean(String number, String progress) {
        this.number = number;
        this.progress = progress;
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
}
