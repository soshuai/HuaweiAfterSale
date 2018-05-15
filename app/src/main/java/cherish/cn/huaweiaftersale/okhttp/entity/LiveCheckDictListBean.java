package cherish.cn.huaweiaftersale.okhttp.entity;

/**
 * Created by veryw on 2018/5/11.
 */

public class LiveCheckDictListBean {
    private String id;
    private String code;
    private String title;
    public LiveCheckDictListBean(String id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
