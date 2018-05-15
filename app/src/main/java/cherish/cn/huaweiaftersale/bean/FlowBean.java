package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/3/29.
 */

public class FlowBean {
    private String title;
    private String imgUrl;
    private String onLine;

    public FlowBean() {}

    public FlowBean(String title, String imgUrl, String onLine) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.onLine = onLine;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOnLine() {
        return onLine;
    }

    public void setOnLine(String onLine) {
        this.onLine = onLine;
    }
}
