package cherish.cn.huaweiaftersale.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.util.LogUtils;

public abstract class BaseMultiFragsActivity extends ProgressActivity implements OnPageChangeListener {

    protected ViewPager mMainContainer;
    // tab适配器
    protected MyPagerFragmentAdapter mTabAdapter;
    // 当前显示tab
    protected int                    mCurrentTab = -1;

    protected BaseFragment           currentFragment;

    protected final void init(View view) {
        // 初始化pager
        initViewPager(view);
        // 创建context
        createPagerContext(view);
        // 读取第一页的数据
        loadFirstPageData();
    }

    protected void loadFirstPageData() {
        // TODO Auto-generated method stub
    }

    @Override
    protected boolean needDefaultMenu() {
        // TODO Auto-generated method stub
        return false;
    }

    protected abstract void createPagerContext(View view);

    private void initViewPager(View view) {
        // TODO Auto-generated method stub
        mMainContainer = (ViewPager) view.findViewById(R.id.main_container_vp);
        mTabAdapter = new MyPagerFragmentAdapter(getSupportFragmentManager());
        mMainContainer.setAdapter(mTabAdapter);
        mMainContainer.setOnPageChangeListener(this);
    }

    protected abstract BaseFragment createFragmentInstance(int position) throws Exception;

    protected abstract int getFragmentCount();

    protected abstract void changeFocus(int index, boolean isFocus);

    protected void putFragmentArg(int position, Bundle b) {
        // TODO Auto-generated method stub
    }

    protected boolean needScrollAnimation() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * 首页tab切换
     *
     * @param position
     *            tab下标
     */
    protected void switchTab(int position) {
        if (position == mCurrentTab)
            return;
        if (position >= this.getFragmentCount()) {
            LogUtils.e(TAG,
                    String.format("Unkown tab index %d, current tab size is %d", position, mTabAdapter.getCount()));
            return;
        }

        for (int i = 0; i < this.getRealCount(); i++) {
            changeFocus(i, (i == (position % this.getRealCount())));
        }

        mCurrentTab = position;
        mMainContainer.setCurrentItem(position, needScrollAnimation());

        BaseFragment fragment = (BaseFragment) mTabAdapter.getItem(position);
        if (fragment != null) {
            fragment.onChoose();
            if (currentFragment != null) {
                currentFragment.onUnchoose();
            }
            currentFragment = fragment;
        }
    }

    /** fragment适配器 */
    protected class MyPagerFragmentAdapter extends FragmentPagerAdapter {
        // 切换时状态保存：http://blog.csdn.net/guo807015563/article/details/42081799
        private SparseArray<BaseFragment> mFragments;
        private FragmentManager mFragmentManager;

        public MyPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragments = new SparseArray<BaseFragment>();
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = mFragments.get(position);
            if (null == fragment) {
                if (position >= mTabAdapter.getCount()) {
                    LogUtils.e(
                            TAG,
                            String.format("Unkown tab index %d, current tab size is %d", position,
                                    mTabAdapter.getCount()));
                } else {
                    try {
                        fragment = (BaseFragment) createFragmentInstance(position);
                        Bundle b = new Bundle();
                        putFragmentArg(position, b);
                        fragment.setArguments(b);
                    } catch (Exception e) {
                        LogUtils.e(TAG,
                                String.format("Failed to create object [%s], exception is ", position, e.getMessage()));
                    }
                }
                mFragments.put(position, fragment);
            }
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseFragment fragment = (BaseFragment) super.instantiateItem(container, position);
            mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            BaseFragment fragment = mFragments.get(position);
            if (needSaveState()) {
                mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
            } else {
                mFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                mFragments.remove(position);
            }
        }

        @Override
        public int getCount() {
            return getFragmentCount();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    protected boolean needSaveState() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        switchTab(position);
    }

    protected int getRealCount() {
        // TODO Auto-generated method stub
        return this.getFragmentCount();
    }

}
