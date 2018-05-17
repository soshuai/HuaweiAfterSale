package cherish.cn.huaweiaftersale.net;

public class UrlData {

    private int id;

    private String method;
    private String url;
    private String returnClass;
    private String version;
    private int countLimit;
    private boolean needToken;
    private boolean https;
    private String encrypt;

    private boolean fullUrl;

    public UrlData(int id, String url, String method, String returnClass,
                   String version, boolean needToken, boolean https, boolean fullUrl, String encrypt, int countLimit) {
        super();
        this.id = id;
        this.url = url;
        this.method = method;
        this.returnClass = returnClass;
        this.version = version;
        this.needToken = needToken;
        this.https = https;
        this.fullUrl = fullUrl;
        this.encrypt = encrypt;
        this.countLimit = countLimit;
    }

    public boolean isFullUrl() {
        return fullUrl;
    }

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getReturnClass() {
        return returnClass;
    }

    public String getVersion() {
        return version;
    }

    public boolean isNeedToken() {
        return needToken;
    }

    public boolean isHttps() {
        return https;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public int getCountLimit() {
        return countLimit;
    }
}
