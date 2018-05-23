package cherish.cn.huaweiaftersale;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.base.BaseMultiFragsActivity;
import cherish.cn.huaweiaftersale.bean.WorkBean;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.entity.OrderListDataEntity;
import cherish.cn.huaweiaftersale.fragment.MineFragment;
import cherish.cn.huaweiaftersale.fragment.OrderHandleFragment;
import cherish.cn.huaweiaftersale.fragment.OrderSearchFragment;
import cherish.cn.huaweiaftersale.jpush.JPushHelper;
import cherish.cn.huaweiaftersale.util.AndroidGpsLocation;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.DoubleClickExitHelper;
import cherish.cn.huaweiaftersale.util.LogUtils;
import cherish.cn.huaweiaftersale.util.Utils;

public class MainActivity extends BaseMultiFragsActivity implements DataCallback{
    private MainPagerTabContext mTabContext[];
    private DoubleClickExitHelper mExitHelper;
    private int TAB_COUNT = 3;
    private LocationManager mLocationManager;//位置管理器

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JPushHelper.resumePush();
        //初始化定位
        initLocation();
        startLocation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView(View contentView) {
        init(contentView.findViewById(R.id.root));
    }

    @Override
    protected boolean showMenu() {
        return true;
    }

    @Override
    protected void putFragmentArg(int position, Bundle b) {
        b.putString("mPageName", mPageName + " - " + super.getString(mTabContext[position].mResTabName));
    }

    @Override
    protected void createPagerContext(View view) {
        mTabContext = new MainPagerTabContext[TAB_COUNT];
        // tab 1
        mTabContext[0] = new MainPagerTabContext(OrderHandleFragment.class, R.id.tab_dynamic, R.string.tab_main_orderhandle, R.drawable.orderhandle, R.drawable.orderhandle);
        mTabContext[1] = new MainPagerTabContext(OrderSearchFragment.class, R.id.tab_stores, R.string.tab_main_ordersearch, R.drawable.ordersearch, R.drawable.ordersearch);
        mTabContext[2] = new MainPagerTabContext(MineFragment.class, R.id.tab_mine, R.string.tab_main_mine, R.drawable.person, R.drawable.person);

        for (int i = 0; i < mTabContext.length; i++) {
            MainPagerTabContext tabContext = mTabContext[i];
            tabContext.mTab = (LinearLayout) view.findViewById(tabContext.mResTabId);
            tabContext.mTab.setTag(i);
            tabContext.mTab.setOnClickListener(this);

            tabContext.mTabImg = (ImageView) tabContext.mTab.findViewById(R.id.iv_main_tab);
            tabContext.mTabText = (TextView) tabContext.mTab.findViewById(R.id.tv_main_tab);
            tabContext.mTabImg.setImageResource(tabContext.mResTabImageNor);
            tabContext.mTabText.setText(tabContext.mResTabName);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected BaseFragment createFragmentInstance(int position) throws Exception {
        // TODO Auto-generated method stub
        return (BaseFragment) mTabContext[position].mClsFragment.newInstance();
    }

    @Override
    protected int getFragmentCount() {
        // TODO Auto-generated method stub
        return TAB_COUNT;
    }

    @Override
    protected void changeFocus(int index, boolean isFocus) {
        // TODO Auto-generated method stub
        this.mTabContext[index].mTabImg.setImageResource(isFocus ? this.mTabContext[index].mResTabImageOver
                : this.mTabContext[index].mResTabImageNor);
        this.mTabContext[index].mTabText.setTextColor(isFocus ? this.getResources().getColor(R.color.colorAccent) : this.getResources().getColor(R.color.titile2));
    }

    @Override
    protected void loadFirstPageData() {
        super.loadFirstPageData();
        switchTab(0);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getTag()==null) return;
        int pos = (Integer) v.getTag();
        switchTab(pos);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (null == mExitHelper)
            mExitHelper = new DoubleClickExitHelper(this);
        if (mExitHelper.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {

    }

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        if (funcKey == R.id.api_savePosition) {
            Log.i("AAAA",data.toString());
        }
    }


    @SuppressWarnings("rawtypes")
    class MainPagerTabContext {
        private LinearLayout mTab;
        private ImageView mTabImg;
        private TextView mTabText;

        private final Class mClsFragment;
        private final int mResTabId;
        private final int mResTabName;
        private final int mResTabImageNor;
        private final int mResTabImageOver;

        public MainPagerTabContext(Class mClsFragment, int mResTabId, int mResTabName, int mResTabImageOver,
                                   int mResTabImageNor) {
            super();
            this.mClsFragment = mClsFragment;
            this.mResTabId = mResTabId;
            this.mResTabName = mResTabName;
            this.mResTabImageOver = mResTabImageOver;
            this.mResTabImageNor = mResTabImageNor;
        }
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //开启Gps
        AndroidGpsLocation.getInstance(mContext);
        AndroidGpsLocation.isOpenGps();


        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }


    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(10000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间
                sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
//                Log.i("AAAA",result);
                Bundle bundle=new Bundle();
                bundle.putString("longitude",location.getLongitude()+"");
                bundle.putString("latitude",location.getLatitude()+"");
                ApiHelper.load(mContext, R.id.api_savePosition, bundle, MainActivity.this);
            } else {
                Log.i("AAAA","定位失败，loc is null");
            }

        }
    };

    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        //根据控件的选择，重新设置定位参数
//        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

}
