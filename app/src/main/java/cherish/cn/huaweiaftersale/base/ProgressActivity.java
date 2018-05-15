package cherish.cn.huaweiaftersale.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import cherish.cn.huaweiaftersale.R;


/**
 * 自动添加顶部菜单，空数据显示，progressbar加载动画的activity
 *
 * @author Veryxyf
 */
public abstract class ProgressActivity extends BaseActivity implements OnClickListener {

    private ViewGroup contentLayout;
    private ViewGroup noDataLayout;
    private TextView menuTitle;
    private View loadingView;
    private TextView noDataText;


    private int contestResId;
    protected View contentView;
    private View defPageTitle;

    private boolean needMenuFlag = true;
    private View rightView;



    protected void renderPage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    protected int getMenuTitleResId() {
        return 0;
    }

    protected abstract void initContentView(View contentView);
    protected abstract boolean showMenu();
    protected boolean needDefaultMenu() {
        // TODO Auto-generated method stub
        return true;
    }


    public final void setMenuTitle(String title) {
        if (needMenuFlag && this.menuTitle != null) {
            this.menuTitle.setText(title);
        }
    }

    protected void hideRightView() {
        if (rightView != null) {
            rightView.setVisibility(View.GONE);
        }
    }

    protected void showRightView() {
        if (rightView != null) {
            rightView.setVisibility(View.VISIBLE);
        }
    }

    public void setMenuTitle(int resId) {
        if (needMenuFlag && this.menuTitle != null) {
            this.menuTitle.setText(resId);
        }
    }

    public final void setContentView(int resId) {
        setContentView(resId, 0);
    }

    public final void setContentView(int resId, int rightResId) {
        View view = super.getLayoutInflater().inflate(R.layout.act_progress, null);

        defPageTitle = view.findViewById(R.id.page_default_title_rl);
        contentLayout = (ViewGroup) view.findViewById(R.id.ll_act_progress_content);
        noDataLayout = (ViewGroup) view.findViewById(R.id.nodata_layout);
        noDataText = (TextView) view.findViewById(R.id.nodata_text);
        loadingView = view.findViewById(R.id.loading_probar);

        needMenuFlag = needDefaultMenu();
        if (needMenuFlag) {
            defPageTitle.setVisibility(View.VISIBLE);

            menuTitle = (TextView) view.findViewById(R.id.tv_menuname);
            int menuResId = getMenuTitleResId();
            if (menuResId > 0) {
                mPageName = super.getString(menuResId);
                menuTitle.setText(menuResId);
            }
            view.findViewById(R.id.imageView).setOnClickListener(this);
        }

        if (rightResId > 0 && rightView == null) {
            rightView = super.getLayoutInflater().inflate(rightResId, null);
            ((ViewGroup) defPageTitle.findViewById(R.id.rl_right)).addView(rightView);
            initMenuRightView(rightView);
        }

        contestResId = resId;
        if (contentView == null) {
            contentView = super.getLayoutInflater().inflate(contestResId, null);
            // 显示后做的事
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            contentLayout.addView(contentView, params);
            initContentView(contentView);
        }

        super.setContentView(view);
    }

    protected void initMenuRightView(View rightView) {
    }

    public void showContent() {
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        noDataText.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
    }

    public void showEmpty(int resId) {
        showEmpty();
        noDataText.setText(resId);
    }

    public void showEmpty(String msg) {
        showEmpty();
        noDataText.setText(msg);
    }

    public final void showEmpty() {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        noDataText.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.VISIBLE);
    }

    public void showLoading(String loadingMsg) {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        noDataText.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        showLoading(null);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.imageView){
            finish();
        }
    }
}
