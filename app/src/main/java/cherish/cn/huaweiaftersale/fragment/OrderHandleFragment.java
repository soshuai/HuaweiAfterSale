package cherish.cn.huaweiaftersale.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.base.BaseFragment;
import cherish.cn.huaweiaftersale.base.BaseMultiFragsFragment;

public class OrderHandleFragment extends BaseMultiFragsFragment implements View.OnClickListener{
    private MainPagerTabContext[] mTabContexts;
    private int mTabNum = 2;
    private View top;
    private int mInitIndex;

    @Override
    protected void init(View view) {
        inits(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_handle;
    }

    protected void putFragmentArg(int position, Bundle b) {
        // TODO Auto-generated method stub
        b.putInt("position", position);
        b.putInt("initPosition", mInitIndex);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        mInitIndex = super.getArguments().getInt("index", 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(View view) {
        top = view.findViewById(R.id.rl_top);
    }

    @Override
    protected void createPagerContext(View view) {
        mTabContexts = new MainPagerTabContext[mTabNum];
        // tab 1
        mTabContexts[0] = new MainPagerTabContext(NewWorkOrderFragment.class, R.id.item_main_tab1,
                R.string.tab_new_work, R.drawable.news, R.drawable.news);
        // tab 2
        mTabContexts[1] = new MainPagerTabContext(RunningFragment.class, R.id.item_main_tab2, R.string.tab_running,
                R.drawable.running, R.drawable.running);

        for (int i = 0; i < mTabContexts.length; i++) {
            MainPagerTabContext tabContext = mTabContexts[i];
            tabContext.mTab = (RelativeLayout) view.findViewById(tabContext.mResTabId);
            tabContext.mTab.setTag(i);
            tabContext.mTab.setOnClickListener(this);

            tabContext.mTabImg = (ImageView) tabContext.mTab.findViewById(R.id.iv_main_tab);
            tabContext.mTabText = (TextView) tabContext.mTab.findViewById(R.id.tv_main_tab);
            tabContext.mTabImg.setImageResource(tabContext.mResTabImageNor);
            tabContext.mTabText.setText(tabContext.mResTabName);
            tabContext.line=(View)tabContext.mTab.findViewById(R.id.line);
        }
    }

    @Override
    protected BaseFragment createFragmentInstance(int position) throws Exception {
        return (BaseFragment) mTabContexts[position].mClsFragment.newInstance();
    }

    @Override
    protected int getFragmentCount() {
        return mTabNum;
    }

    @Override
    protected void changeFocus(int index, boolean isFocus) {
        this.mTabContexts[index].mTabImg.setImageResource(isFocus ? this.mTabContexts[index].mResTabImageOver
                : this.mTabContexts[index].mResTabImageNor);
        this.mTabContexts[index].mTabText.setTextColor(isFocus ? this.getResources().getColor(R.color.colorAccent) : this.getResources().getColor(R.color.text_gray));
        this.mTabContexts[index].line.setVisibility(isFocus?View.VISIBLE:View.GONE);

    }

    @Override
    protected void loadFirstPageData() {
        // TODO Auto-generated method stub
        super.loadFirstPageData();
        super.switchTab(mInitIndex);
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switchTab(position);
    }

    @SuppressWarnings("rawtypes")
    class MainPagerTabContext {
        private RelativeLayout mTab;
        private ImageView mTabImg;
        private TextView mTabText;
        private View line;

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

    public void onPageSelected(int position) {
        super.onPageSelected(position);
    }

}
