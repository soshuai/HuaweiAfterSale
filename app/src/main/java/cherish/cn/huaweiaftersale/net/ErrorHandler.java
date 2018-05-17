package cherish.cn.huaweiaftersale.net;

public interface ErrorHandler {

    /**
     * 异常处理
     * @param funcKey
     * @param result
     * @param errorMsg
     */
    void handleError(int funcKey, int result, String errorMsg);
}