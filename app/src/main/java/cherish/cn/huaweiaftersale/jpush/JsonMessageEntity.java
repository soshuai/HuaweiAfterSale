package cherish.cn.huaweiaftersale.jpush;


import cherish.cn.huaweiaftersale.bean.BaseEntity;

/**
 * Created by veryx on 2016/9/12.
 */
public class JsonMessageEntity extends BaseEntity {
    private String content;
    private String title;
    private String image;
    private int type;

    public JsonMessageEntity(){
        super();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
