package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/3/29.
 */

public class CustomerBean {
    private String title;
    private String imgUrl;
    private String uuid;

    public CustomerBean() {}

    public CustomerBean(String title, String imgUrl,String uuid) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
