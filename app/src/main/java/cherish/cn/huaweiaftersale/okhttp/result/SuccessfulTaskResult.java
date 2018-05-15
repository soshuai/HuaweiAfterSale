/**
 * 
 */
package cherish.cn.huaweiaftersale.okhttp.result;

/**
 * @author Veryxyf
 * @param <T>
 * 
 */
public class SuccessfulTaskResult<T> extends TaskResult<T> {

    public SuccessfulTaskResult(T result, Object... params) {
        super(result, true, null, params);
    }

}
