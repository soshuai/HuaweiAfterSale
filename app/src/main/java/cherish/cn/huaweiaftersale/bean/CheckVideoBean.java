package cherish.cn.huaweiaftersale.bean;

/**
 * Created by veryw on 2018/3/29.
 */

public class CheckVideoBean {
    private String time;
    private String nopass;
    private String name;
    private String id;

    public CheckVideoBean(String time, String nopass, String name,String id) {
        this.time = time;
        this.nopass = nopass;
        this.name = name;
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public String getNopass() {
        return nopass;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
