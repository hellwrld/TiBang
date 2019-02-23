package ecnu.cs.tibang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import ecnu.cs.tibang.R;

public class HeaderLayout extends LinearLayout {
    private LayoutInflater mInflater;
    private View mHeader;
    private LinearLayout mLayoutLeftContainer;
    private TextView mHtvSubTitle;

    private LinearLayout mLayoutLeftImageButtonLayout;
    private ImageButton mLeftImageButton;
    private onLeftImageButtonClickListener mLeftImageButtonClickListener;

    public enum HeaderStyle {// 头部整体样式
        DEFAULT_TITLE, TITLE_LIFT_IMAGEBUTTON;
    }

    public HeaderLayout(Context context) {
        super(context);
        init(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeader = mInflater.inflate(R.layout.common_header, null);
        addView(mHeader);
        initViews();
    }

    public void initViews() {
        mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
        mHtvSubTitle = (TextView) findViewByHeaderId(R.id.header_htv_subtitle);

    }

    public View findViewByHeaderId(int id) {
        return mHeader.findViewById(id);
    }

    public void init(HeaderStyle hStyle) {
        switch (hStyle) {
            case DEFAULT_TITLE:
                defaultTitle();
                break;

            case TITLE_LIFT_IMAGEBUTTON:
                defaultTitle();
                titleLeftImageButton();
                break;
        }
    }

    // 默认文字标题
    private void defaultTitle() {
        mLayoutLeftContainer.removeAllViews();
    }

    // 左侧自定义按钮
    private void titleLeftImageButton() {
        View mleftImageButtonView = mInflater.inflate(
                R.layout.common_header_button, null);
        mLayoutLeftContainer.addView(mleftImageButtonView);
        mLayoutLeftImageButtonLayout = (LinearLayout) mleftImageButtonView
                .findViewById(R.id.header_layout_imagebuttonlayout);
        mLeftImageButton = (ImageButton) mleftImageButtonView
                .findViewById(R.id.header_ib_imagebutton);
        mLayoutLeftImageButtonLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mLeftImageButtonClickListener != null) {
                    mLeftImageButtonClickListener.onClick();
                }
            }
        });
    }

    public void setDefaultTitle(CharSequence title) {
        if (title != null) {
            mHtvSubTitle.setText(title);
        } else {
            mHtvSubTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleAndLeftImageButton(CharSequence title, int id,
                                           onLeftImageButtonClickListener listener) {
        setDefaultTitle(title);
        if (mLeftImageButton != null && id > 0) {
            mLeftImageButton.setImageResource(id);
            setOnLeftImageButtonClickListener(listener);
        }
    }

    public void setOnLeftImageButtonClickListener(
            onLeftImageButtonClickListener listener) {
        mLeftImageButtonClickListener = listener;
    }

    public interface onLeftImageButtonClickListener {
        void onClick();
    }

}
