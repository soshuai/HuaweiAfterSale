package cherish.cn.huaweiaftersale.okhttp.listener;


import cherish.cn.huaweiaftersale.util.AppException;

/**
 * 任务结束的回调
 * @author Veryxyf
 *
 */
public interface TaskProcessListener<T> {

    public void onComplete(Object... params)throws AppException;

    public void onFailed(Object... params)throws AppException;

    public void onSuccess(Object... params)throws AppException;
}
