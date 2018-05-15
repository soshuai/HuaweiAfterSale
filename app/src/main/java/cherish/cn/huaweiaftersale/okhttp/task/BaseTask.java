package cherish.cn.huaweiaftersale.okhttp.task;

/**
 * 所有与UI有交互的耗时操作需继承该类
 */
public abstract class BaseTask<Params, Progress, Result> extends LocalAsyncTask<Params, Progress, Result> {
    private boolean isRunning = false;
    private String  mTag;

    public BaseTask() {
        // 子类如果有带参数构造器，须在构造器方法内首先调用super();
        mTag = BaseTask.class.getSimpleName() + "_" + System.currentTimeMillis();
    }

    @Override
    protected void onPreExecute() {
        isRunning = true;
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Result result) {
        isRunning = false;
        super.onPostExecute(result);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getTag() {
        return mTag;
    }
}