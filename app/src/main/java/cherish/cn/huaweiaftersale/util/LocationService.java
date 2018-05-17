package cherish.cn.huaweiaftersale.util;
import android.app.IntentService;
import android.content.Intent;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;

public class LocationService extends IntentService {
    private static final String TAG = "LocationService";
    private static final String SERVICE_NAME = "LocationService";
    private AMapLocationClient locationClient;

    public LocationService() {
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationClient = new AMapLocationClient(this.getApplicationContext());
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        AMapLocation location = locationClient.getLastKnownLocation();
        if (location != null) {
            String latitude = location.getLatitude() + "";
            String longitude = location.getLongitude() + "";
            SpfUtils.saveString(this, "latitude", latitude + "");
            SpfUtils.saveString(this, "longitude", longitude + "");
            //EventBus.getDefault().post("第一个：latitude="+latitude+"  longitude="+longitude);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
        }
    }
}
