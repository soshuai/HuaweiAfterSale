package cherish.cn.huaweiaftersale.okhttp.listener;

import cherish.cn.huaweiaftersale.util.AppException;

/**
 * 任务结束的回调
 * 
 * @author Veryxyf
 * 
 */
public class NullTaskProcessListener implements TaskProcessListener {

    public NullTaskProcessListener() {
        super();
    }

    @Override
    public void onComplete(Object... params) throws AppException {
        // TODO Auto-generated method stub
    }

    @Override
    public void onFailed(Object... params) throws AppException {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSuccess(Object... params) throws AppException {
        // TODO Auto-generated method stub
    }

}
