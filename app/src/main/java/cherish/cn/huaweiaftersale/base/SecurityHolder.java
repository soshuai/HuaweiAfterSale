package cherish.cn.huaweiaftersale.base;


import cherish.cn.huaweiaftersale.bean.SecurityEntity;

/**
 * 全局应用程序类：用于数据初始化
 */
public final class SecurityHolder {

    private SecurityEntity mSecurityData;
    private static SecurityHolder sInstance;

    private SecurityHolder() {
        super();
    }

    private static SecurityHolder getInstance() {
        if (sInstance == null) {
            sInstance = new SecurityHolder();
        }
        return sInstance;
    }

    public synchronized static SecurityEntity findSecurityData() {
        SecurityEntity securityEntity =  SecurityHolder.getInstance().getSecurityData();
        if(securityEntity==null){
            securityEntity = new SecurityEntity();
        }
        return securityEntity;
    }

    public static synchronized void saveSecurityData(SecurityEntity securityData) {
        SecurityHolder.getInstance().setSecurityData(securityData);
    }

    private void setSecurityData(SecurityEntity securityData) {
        this.mSecurityData = securityData;
    }

    public SecurityEntity getSecurityData() {
        return mSecurityData;
    }

}
