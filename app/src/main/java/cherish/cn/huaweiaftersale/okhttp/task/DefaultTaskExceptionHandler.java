package cherish.cn.huaweiaftersale.okhttp.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;

import cherish.cn.huaweiaftersale.MainActivity;
import cherish.cn.huaweiaftersale.base.AppManager;
import cherish.cn.huaweiaftersale.okhttp.entity.BaseApiEntity;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.LogUtils;

/**
 * 简单的异常处理
 * 
 * @author Veryxyf
 * 
 */
public class DefaultTaskExceptionHandler implements TaskExceptionHander {

    private static final String TAG = DefaultTaskExceptionHandler.class.getSimpleName();

    private Activity            context;

    public DefaultTaskExceptionHandler(Activity context) {
        this.context = context;
    }

    @Override
    public void handle(Exception e) {
        // TODO Auto-generated method stub
        if (e != null) {
            LogUtils.e(TAG, e);
            if (e instanceof AppException) {
                AppException appe = (AppException) e;
                if (appe.getCode() == BaseApiEntity.FAILED_CODE_SESSION_TIMEOUT
                        || appe.getCode() == BaseApiEntity.FAILED_CODE_PARA_TOKEN_MISMATCH
                        || appe.getCode() == BaseApiEntity.FAILED_CODE_PARA_WANTEDTOKEN) {
                    // 会话超时，退回登录页面
//                    AppContext.getInstance().logout();
                    Builder dlg = new Builder(context);
                    dlg.setTitle("提示");
                    dlg.setMessage(BaseApiEntity.ERROR_DESCS.get(appe.getCode()));
                    dlg.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            autoLogout(context);
                        }
                    }).create();// 创建按钮
                    AlertDialog dialog = dlg.show();
                    // 用户点击回退取消后，也要退出登录
                    dialog.setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface arg0) {
                            // TODO Auto-generated method stub
                            autoLogout(context);
                        }
                    });
                } else {
                    if (!appe.isProcessed())
                        appe.makeToast(context);
                }
            } else {
                AppException.run(e).makeToast(context);
            }
        }

    }

    protected void autoLogout(Activity context) {
        // TODO Auto-generated method stub
//        AppManager.getInstance().finishActivityExcept(MainActivity.class);
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
        
//        EventBus.getDefault().post(LoginTimeoutEvent.instance);
    }

}
