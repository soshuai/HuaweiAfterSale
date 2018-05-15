package cherish.cn.huaweiaftersale.event;


import cherish.cn.huaweiaftersale.bean.TabInfoBean;

public class TabInfoEvent {

    protected TabInfoBean bean;

    public TabInfoEvent(TabInfoBean bean) {
        this.bean = bean;
    }

    public TabInfoBean getBean() {
        return bean;
    }
}