package cherish.cn.huaweiaftersale.base;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

    private static Stack<BaseActivity> sActivityStack;
    private static AppManager sInstance;

    private AppManager() {
        sActivityStack = new Stack<BaseActivity>();
    }

    /**
     * 单一实例
     */
    public synchronized static AppManager getInstance() {
        if (sInstance == null) {
            sInstance = new AppManager();
        }
        return sInstance;
    }

    /**
     * 添加Activity到堆栈
     */
    public synchronized void addActivity(BaseActivity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<BaseActivity>();
        }
        sActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public BaseActivity currentActivity() {
        try {
            return sActivityStack.peek();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public BaseActivity getActivityBackCurrent() {
        try {
            if (sActivityStack.size() > 1) {
                return sActivityStack.get(sActivityStack.size() - 2);
            }
            return currentActivity();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        try {
            finishActivity(sActivityStack.peek());
        } catch (EmptyStackException e) {
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(BaseActivity activity) {
        if (null != activity) {
            sActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<? extends BaseActivity> cls) {
        int stackSize = sActivityStack.size();
        for (int i = stackSize - 1; i >= 0; i--) {
            BaseActivity activity = sActivityStack.get(i);
            if (null == activity) {
                sActivityStack.remove(i);
            } else if (activity.getClass().equals(cls)) {
                sActivityStack.remove(i);
                activity.finish();
                activity = null;
            }
        }
    }

    public void finishActivityExcept(Class<? extends BaseActivity> cls) {
        // TODO Auto-generated method stub
        int stackSize = sActivityStack.size();
        for (int i = stackSize - 1; i >= 0; i--) {
            BaseActivity activity = sActivityStack.get(i);
            if (null == activity) {
                sActivityStack.remove(i);
            } else if (!activity.getClass().equals(cls)) {
                sActivityStack.remove(i);
                activity.finish();
                activity = null;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        while (true) {
            try {
                BaseActivity activity = sActivityStack.pop();
                if (null != activity)
                    activity.finish();
            } catch (EmptyStackException e) {
                break;
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
//            MobclickAgent.onKillProcess(context);
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}