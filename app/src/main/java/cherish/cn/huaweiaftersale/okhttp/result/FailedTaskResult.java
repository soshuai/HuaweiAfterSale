/**
 * 
 */
package cherish.cn.huaweiaftersale.okhttp.result;

import cherish.cn.huaweiaftersale.util.AppException;

/**
 * @author Veryxyf
 * @param <T>
 * 
 */
public class FailedTaskResult<T> extends TaskResult<T> {

    public FailedTaskResult(T result, AppException e, Object... params) {
        super(result, false, e, params);
    }

    public FailedTaskResult(T result, Object... params) {
        this(result, null, params);
    }

    public FailedTaskResult(AppException e, Object... params) {
        this(null, e, params);
    }

}
