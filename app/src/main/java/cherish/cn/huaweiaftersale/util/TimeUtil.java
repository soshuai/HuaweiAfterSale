package cherish.cn.huaweiaftersale.util;

/**
 * Created by veryw on 2018/5/24.
 */

public class TimeUtil {
    public  static String getTime(int time) {
        //计算天 1天 = 24*60 1小时=60
        int day = time/(24*60);
        int hour = (time%(24*60))/60;
        int minute = (time%(24*60))%60;
        if (day>0){
            return day+"天"+hour+"小时前";//+minute+"分钟前";
        }else if (hour>0){
            return hour+"小时前";//+minute+"分钟前";
        }else{
            return minute+"分钟前";
        }
    }
}
