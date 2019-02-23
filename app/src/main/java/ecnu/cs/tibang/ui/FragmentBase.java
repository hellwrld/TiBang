package ecnu.cs.tibang.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import ecnu.cs.tibang.R;
import ecnu.cs.tibang.view.HeaderLayout;
import ecnu.cs.tibang.view.HeaderLayout.HeaderStyle;
import ecnu.cs.tibang.view.HeaderLayout.onLeftImageButtonClickListener;

/** Fragmenet 基类
 * @ClassName: FragmentBase
 * @Description: TODO
 */
public abstract class FragmentBase extends Fragment {

    /**
     * 公用的Header布局
     */
    public HeaderLayout mHeaderLayout;

    protected View contentView;

    public LayoutInflater mInflater;

    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mInflater = LayoutInflater.from(getActivity());
    }


    public FragmentBase() {

    }

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    /**
     * 只有title initTopBarLayoutByTitle
     * @Title: initTopBarLayoutByTitle
     * @throws
     */
    public void initTopBarForOnlyTitle(String titleName) {
        mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
        mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
        mHeaderLayout.setDefaultTitle(titleName);
    }

    /**
     * 只有左边按钮和Title initTopBarLayout
     *
     * @throws
     */
    public void initTopBarForLeft(String titleName) {
        mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
        mHeaderLayout.init(HeaderStyle.TITLE_LIFT_IMAGEBUTTON);
        mHeaderLayout.setTitleAndLeftImageButton(titleName,
                R.drawable.action_bar_back_selector,
                new OnLeftButtonClickListener());
    }

    // 左边按钮的点击事件
    public class OnLeftButtonClickListener implements
            onLeftImageButtonClickListener {

        @Override
        public void onClick() {
            getActivity().finish();
        }
    }

    /**
     * 动画启动页面 startAnimActivity
     * @throws
     */
    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }

    public void startAnimActivity(Class<?> cla) {
        getActivity().startActivity(new Intent(getActivity(), cla));
    }

}
