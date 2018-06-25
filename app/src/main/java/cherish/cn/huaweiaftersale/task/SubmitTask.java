package cherish.cn.huaweiaftersale.task;

import java.io.File;
import java.util.Map;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.BaseActivity;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.okhttp.api.UserApi;
import cherish.cn.huaweiaftersale.okhttp.entity.VideoCheckDictEntity;
import cherish.cn.huaweiaftersale.okhttp.listener.TaskProcessListener;
import cherish.cn.huaweiaftersale.okhttp.result.SuccessfulTaskResult;
import cherish.cn.huaweiaftersale.okhttp.result.TaskResult;
import cherish.cn.huaweiaftersale.okhttp.task.BusinessTask;
import cherish.cn.huaweiaftersale.util.AppException;

public class SubmitTask extends BusinessTask {
    private Map<String, String> params;
    private Map<String, File> files;
    private DataCallback callback;
    public SubmitTask(BaseActivity context, TaskProcessListener processListener, DataCallback callback, Map<String, String> params, Map<String, File> files) {
        super(context, processListener);
        // TODO Auto-generated constructor stub
        this.params=params;
        this.files=files;
        this.callback=callback;
    }

    @Override
    protected TaskResult doTask(Object... params) throws AppException {
        // TODO Auto-generated method stub
//        VideoCheckDictEntity resultEntity= UserApi.getInstance().submitVideoCheck(context,this.params,this.files);
//        return new SuccessfulTaskResult(resultEntity, "submitVideoCheck");
        ApiHelper.submit(context, R.id.api_order_ajaxSaveFile,this.params,callback,files);
        return null;
    }

}
