package cherish.cn.huaweiaftersale.util;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liujun on 2018/1/23.
 * Android原生GPS信息
 */

public class AndroidGpsLocation  {
    private static final String TAG = "AndroidGpsLocation";

    private static AndroidGpsLocation instance;
    private static Context mContext;
    private static LocationManager locationManager;
    private static LocationListener locationListener;

    public static AndroidGpsLocation getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new AndroidGpsLocation();
        }
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return instance;
    }

    public static void isOpenGps() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setMessage("GPS未打开，是否打开?");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    // 设置完成后返回到原来的界面
                    mContext.startActivity(intent);
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public static void formListenerGetLocation() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, "纬度；" + location.getLatitude());
                Log.i(TAG, "经度：" + location.getLongitude());
                Log.i(TAG, "海拔：" + location.getAltitude());
                Log.i(TAG, "时间：" + location.getTime());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(TAG, "Gps 状态变化");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i(TAG, "Gps 状态不可用");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i(TAG, "Gps 状态可用");
            }
        };
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public static void getLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i(TAG, "纬度：" + location.getLatitude());
        Log.i(TAG, "经度：" + location.getLongitude());
        Log.i(TAG, "海拔：" + location.getAltitude());
        Log.i(TAG, "时间：" + location.getTime());
    }

    /**
     *  获取GPS卫星数
     *  @return
     */
    /*public static StringBuffer getStatusListener() {
        final StringBuffer satelliteInfo = new StringBuffer();
        GpsStatus.Listener listener = new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                try{
                if (event == GpsStatus.GPS_EVENT_FIRST_FIX) {
                    Log.i(TAG, "第一次定位");
                } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GpsStatus gpsStauts = locationManager.getGpsStatus(null);
                    int maxSatellites = gpsStauts.getMaxSatellites();
                    Iterator<GpsSatellite> it = gpsStauts.getSatellites().iterator();//创建一个迭代器保存所有卫星
                    int count = 0;
                    int countStrong = 0;
                    int countWeak = 0;
                    while (it.hasNext() && count <= maxSatellites) {
                        count++;
                        GpsSatellite s = it.next();
                        satelliteInfo.append(s.getSnr());
                        //R1 信号弱阈值  R2 无信号阈值
                        if((int)s.getSnr()<LocationCache.R1 && (int)s.getSnr()>LocationCache.R2){
                            countWeak++;
                        }

                        if((int)s.getSnr()>=LocationCache.R1){
                            countStrong++;
                        }
                    }
                    satelliteInfo.append(count);
                    if(countStrong+countWeak==0){
                        //TODO
                        MathFinalIndoorParkingPositionHelper.getInstance().match();
                    }
                    //将当前卫星数存入缓存
                    LocationCache.getInstance().setSatelliteStrong(countStrong);
                    LocationCache.getInstance().setSatelliteWeak(countWeak);
                    //将是否用于修正计算的数量存入缓存
                    LocationCache.getInstance().getDataCallBack().staeliteInfo(countStrong,countWeak);
//                    Toast.makeText(mContext,"卫星数："+count+"\n"+"修正计算数"+countAlmanac,Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "搜索到：" + count + "颗卫星");
                } else if (event == GpsStatus.GPS_EVENT_STARTED) {
                    Log.i(TAG, "定位启动");
                } else if (event == GpsStatus.GPS_EVENT_STOPPED) {
                    Log.i(TAG, "定位关闭");
                }
                }catch (Exception e){
                    ToastUtil.showHint(MyApplication.getAppContext(),"gps异常");
                }
            }
        };
        locationManager.addGpsStatusListener(listener);
        return satelliteInfo;
    }*/

    public static List<GpsSatellite> getGpsStatus() {
        List<GpsSatellite> result = new ArrayList<GpsSatellite>();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i(TAG,"Line 179 permission denied");
            return null;
        }
        GpsStatus gpsStatus = locationManager.getGpsStatus(null); // 取当前状态
        //获取默认最大卫星数
        int maxSatellites = gpsStatus.getMaxSatellites();
        //获取第一次定位时间（启动到第一次定位）
        int costTime=gpsStatus.getTimeToFirstFix();
        Log.i(TAG, "第一次定位时间:"+costTime);
        //获取卫星
        Iterable<GpsSatellite> iterable=gpsStatus.getSatellites();
        //一般再次转换成Iterator
        Iterator<GpsSatellite> itrator=iterable.iterator();
        int count = 0;
        while (itrator.hasNext() && count <= maxSatellites){
            count++;
            GpsSatellite s = itrator.next();
            result.add(s);
        }
        return result;
    }

    public static void getGpsStatelliteInfo(GpsSatellite gpssatellite){

        //卫星的方位角，浮点型数据
        Log.i(TAG, "卫星的方位角："+gpssatellite.getAzimuth());
        //卫星的高度，浮点型数据
        Log.i(TAG, "卫星的高度："+gpssatellite.getElevation());
        //卫星的伪随机噪声码，整形数据
        Log.i(TAG, "卫星的伪随机噪声码："+gpssatellite.getPrn());
        //卫星的信噪比，浮点型数据
        Log.i(TAG, "卫星的信噪比："+gpssatellite.getSnr());
        //卫星是否有年历表，布尔型数据
        Log.i(TAG, "卫星是否有年历表："+gpssatellite.hasAlmanac());
        //卫星是否有星历表，布尔型数据
        Log.i(TAG, "卫星是否有星历表："+gpssatellite.hasEphemeris());
        //卫星是否被用于近期的GPS修正计算
        Log.i(TAG, "卫星是否被用于近期的GPS修正计算："+gpssatellite.hasAlmanac());

    }

}
