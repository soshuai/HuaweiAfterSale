package Test;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by veryw on 2018/6/22.
 */

public class Test {

    public static void main(String[] args) {
//        int[] str={10,9,16,19,17};
//        //冒泡 从大到小
//        for (int i = 0; i <str.length-1 ; i++) {
//            for (int j = 0; j <str.length-1-i ; j++) {
//                if (str[j]<str[j+1]){
//                    int then=str[j];
//                    str[j]=str[j+1];
//                    str[j+1]=then;
//                }
//            }
//        }
//        System.out.print(Arrays.asList(str));

        float time= (float) 2511/3600;
        DecimalFormat df = new DecimalFormat("0.0");//格式化小数，.后跟几个零代表几位小数
        String s = df.format(time);
    }
}
