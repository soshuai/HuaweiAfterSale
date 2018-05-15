package cherish.cn.huaweiaftersale.okhttp.task;

/**
 * 异常处理器，负责在主线程中处理异常信息
 * 可以直接操作ui
 * @author Veryxyf
 *
 */
public interface TaskExceptionHander {

    public void handle(Exception e);

}
