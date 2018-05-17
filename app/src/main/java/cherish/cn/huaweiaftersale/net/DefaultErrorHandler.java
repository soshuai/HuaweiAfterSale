package cherish.cn.huaweiaftersale.net;

import android.widget.Toast;

import cherish.cn.huaweiaftersale.base.AppContext;
import cherish.cn.huaweiaftersale.base.AppManager;
import cherish.cn.huaweiaftersale.base.BaseActivity;
import cherish.cn.huaweiaftersale.util.ToastHolder;

/**
 * 数据加载工具
 *
 * @author Veryxyf
 */
public final class DefaultErrorHandler implements ErrorHandler {

    /**
     * 默认处理异常的方法
     * @param funcKey
     * @param result
     * @param errorMsg
     */
    @Override
    public void handleError(int funcKey, int result, String errorMsg) {
        if (result < 0) {
            if (result == -1) {
                // 弹出对话框
              //  SecurityHelper.logtimeout();
                final BaseActivity activity = AppManager.getInstance().currentActivity();
             //   DialogHelper.showLoginTimeoutDialog(activity, errorMsg);
            }
        } else {
            ToastHolder.showToast(AppContext.getInstance().getApplicationContext(), errorMsg, Toast.LENGTH_LONG);
        }
    }
}
