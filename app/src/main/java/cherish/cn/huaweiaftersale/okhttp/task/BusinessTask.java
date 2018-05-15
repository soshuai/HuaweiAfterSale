/**
 *
 */
package cherish.cn.huaweiaftersale.okhttp.task;

import android.content.Context;

import cherish.cn.huaweiaftersale.base.BaseActivity;
import cherish.cn.huaweiaftersale.okhttp.listener.NullTaskProcessListener;
import cherish.cn.huaweiaftersale.okhttp.listener.TaskProcessListener;
import cherish.cn.huaweiaftersale.okhttp.result.FailedTaskResult;
import cherish.cn.huaweiaftersale.okhttp.result.TaskResult;
import cherish.cn.huaweiaftersale.util.AppException;
/**
 * @author Veryxyf
 *
 */
public abstract class BusinessTask extends BaseTask {

    protected static final String TAG = BusinessTask.class.getSimpleName();

    protected Context context;
    protected TaskProcessListener processListener;
    /**
     * 异常处理器
     */
    protected TaskExceptionHander exceptionHandler;

    protected BusinessTask(BaseActivity context, TaskProcessListener processListener,
                           TaskExceptionHander exceptionHandler) {
        this.context = context;
        if (processListener == null) {
            this.processListener = new NullTaskProcessListener();
        } else {
            this.processListener = processListener;
        }
        this.exceptionHandler = exceptionHandler;
    }

    protected BusinessTask(BaseActivity context, TaskProcessListener processListener) {
        this(context, processListener, new DefaultTaskExceptionHandler(context));
    }

    protected BusinessTask(BaseActivity context) {
        this(context, null);
    }

    public void publishTaskProgress(Object... values) {
        super.publishProgress(values);
    }

    @Override
    protected Object doInBackground(Object... params) {
        try {
            return doTask(params);
        } catch (AppException e) {
            // 往主线程发送一个消息
            return new FailedTaskResult(e, params);
        } catch (Exception e) {
            return new FailedTaskResult(AppException.run(e), params);
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        try {
            if (result != null) {
                if (result instanceof TaskResult) {
                    TaskResult taskResult = (TaskResult) result;
                    if (taskResult.isSuccess()) {
                        taskSuccess(taskResult);
                        this.processListener.onSuccess(taskResult.getResult(), taskResult.getParams());
                    } else {
                        taskFailed(taskResult);
                        this.processListener.onFailed(taskResult.getResult(), taskResult.getException(),
                                taskResult.getParams());
                        // 如果 包含异常信息则提示用户
                        if (taskResult.getException() != null) {
                            throw taskResult.getException();
                        }
                    }
                }
            }
        } catch (Exception e) {
            this.exceptionHandler.handle(e);
        } finally {
            try {
                this.processListener.onComplete();
            } catch (Exception e) {
                this.exceptionHandler.handle(e);
            }
        }
    }

    protected abstract TaskResult doTask(Object... params) throws AppException;

    protected void taskSuccess(TaskResult result) throws AppException {
    }

    protected void taskFailed(TaskResult result) throws AppException {
    }
}

