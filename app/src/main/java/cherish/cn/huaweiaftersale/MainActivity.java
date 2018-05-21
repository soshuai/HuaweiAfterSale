package cherish.cn.huaweiaftersale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cherish.cn.huaweiaftersale.base.BaseActivity;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.base.BaseMultiFragsActivity;
import cherish.cn.huaweiaftersale.fragment.MineFragment;
import cherish.cn.huaweiaftersale.fragment.OrderHandleFragment;
import cherish.cn.huaweiaftersale.fragment.OrderSearchFragment;
import cherish.cn.huaweiaftersale.jpush.JPushHelper;
import cherish.cn.huaweiaftersale.util.DoubleClickExitHelper;

public class MainActivity extends BaseMultiFragsActivity {
    private MainPagerTabContext mTabContext[];
    private DoubleClickExitHelper mExitHelper;
    private int TAB_COUNT=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JPushHelper.resumePush();
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
}
