package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/3/29.
 */

public class DayCheckBean {
    private String time;
    private boolean select;

    public DayCheckBean(String time, boolean select) {
        this.time = time;
        this.select = select;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
