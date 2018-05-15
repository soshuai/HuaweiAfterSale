package cherish.cn.huaweiaftersale.base;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.util.LogUtils;

/**
 * Created by veryw on 2018/5/14.
 */

public abstract class BaseMultiFragsFragment extends BaseFragment implements OnPageChangeListener {
    private int lastPosition;
    protected ViewPager mMainContainer;
    // tab适配器
    protected MyPagerFragmentAdapter mTabAdapter;
    // 当前显示tab
    private int mCurrentTab = -1;

    protected BaseFragment currentFragment;

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

    protected abstract void createPagerContext(View view);

    protected void initViewPager(View view) {
        // TODO Auto-generated method stub
        mMainContainer = (ViewPager) view.findViewById(R.id.frag_container_vp);
        mTabAdapter = new MyPagerFragmentAdapter(super.mContext.getSupportFragmentManager());
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
        return true;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        switchTab(position);
    }

    protected final void switchTab(int position) {
        if (position == mCurrentTab)
            return;
        if (position >= mTabAdapter.getCount()) {
            LogUtils.e(TAG,
                    String.format("Unkown tab index %d, current tab size is %d", position, mTabAdapter.getCount()));
            return;
        }

        for (int i = 0; i < this.getFragmentCount(); i++) {
            changeFocus(i, (i == position));
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

    class MyPagerFragmentAdapter extends FragmentPagerAdapter {
        private SparseArray<BaseFragment> mFragments;
        private FragmentManager mFragmentManager;

        public MyPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragments = new SparseArray<BaseFragment>();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getCustomPageTitle(position);
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
            mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }

        @Override
        public int getCount() {
            return getFragmentCount();
        }
    }

    protected CharSequence getCustomPageTitle(int position) {
        // TODO Auto-generated method stub
        return null;
    }

}