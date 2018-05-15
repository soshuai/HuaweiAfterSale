package cherish.cn.huaweiaftersale.okhttp.result;

import cherish.cn.huaweiaftersale.util.AppException;

public abstract class TaskResult<T> {

    private T            result;
    private boolean      success;
    private AppException exception;
    private Object[]     params;

    protected TaskResult(T result, boolean success, AppException exception, Object[] params) {
        this.result = result;
        this.success = success;
        this.exception = exception;
        this.params = params;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AppException getException() {
        return exception;
    }

    public void setException(AppException exception) {
        this.exception = exception;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}