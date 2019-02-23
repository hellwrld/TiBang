package ecnu.cs.tibang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class PhotoViewWrapper extends RelativeLayout {

	protected PhotoView photoView;

	protected Context mContext;
	
	public PhotoViewWrapper(Context ctx) {
		super(ctx);
		mContext = ctx;
		init();
	}

	public PhotoViewWrapper(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		mContext = ctx;
		init();
	}

	protected void init() {
		photoView = new PhotoView(mContext);
		photoView.enable();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		photoView.setLayoutParams(params);
		this.addView(photoView);
		photoView.setVisibility(GONE);
	}

	public void setUrl(String imageUrl) {
		photoView.setVisibility(VISIBLE);
		Glide.with(mContext).load(imageUrl).thumbnail(0.1f).into(photoView);
	}
}