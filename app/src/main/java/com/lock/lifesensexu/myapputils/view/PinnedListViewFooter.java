/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.lock.lifesensexu.myapputils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lock.lifesensexu.myapputils.R;

public class PinnedListViewFooter extends LinearLayout {
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private AVLoadingIndicatorView mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private final int ROTATE_ANIM_DURATION = 180;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	private RelativeLayout xlistview_header_indicator;

	public PinnedListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PinnedListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	public void setBottomMargin(int height) {
		if (height < 0) return ;
		LayoutParams lp = (LayoutParams)mContainer.getLayoutParams();
		lp.bottomMargin = height;
		mContainer.setLayoutParams(lp);
	}
	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView)findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView)findViewById(R.id.xlistview_header_hint_textview);
		xlistview_header_indicator = (RelativeLayout)findViewById(R.id.xlistview_header_indicator);
		mHintTextView.setText("上拉加载");
		mProgressBar = (AVLoadingIndicatorView)findViewById(R.id.xlistview_header_progressbar);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}
	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams)mContainer.getLayoutParams();
		return lp.bottomMargin;
	}
	public void setState(int state) {
		if (state == mState) return ;

		if (state == STATE_REFRESHING) {	// 显示进度
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else {	// 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}

		switch(state){
			case STATE_NORMAL:
				if (mState == STATE_READY) {
					mArrowImageView.startAnimation(mRotateDownAnim);
				}
				if (mState == STATE_REFRESHING) {
					mArrowImageView.clearAnimation();
				}
				mHintTextView.setText("上拉加载");
				break;
			case STATE_READY:
				if (mState != STATE_READY) {
					mArrowImageView.clearAnimation();
					mArrowImageView.startAnimation(mRotateUpAnim);
					mHintTextView.setText("松开加载更多");
				}
				break;
			case STATE_REFRESHING:
				mHintTextView.setText("正在加载...");
				break;
			default:
		}

		mState = state;
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getLayoutParams().height;
	}

	/**
	 * 设置刷新结果标志
	 * @param result 文字提示
	 * @param flag 是否刷新成功
	 */
	public void setHintTextView(String result, boolean flag) {
		mProgressBar.setVisibility(View.GONE);
		mArrowImageView.setVisibility(View.VISIBLE);
		if(flag) {
			mArrowImageView.setImageResource(R.mipmap.main_refresh_success);
		} else {
			setHintTextViewColor(getResources().getColor(R.color.main_pull_wrong));
			mArrowImageView.setImageResource(R.mipmap.main_refresh_warnning);
		}
		mHintTextView.setText(result);
	}

	public void setHintTextViewAndHideHintImage(String result, boolean flag) {
		mProgressBar.setVisibility(View.GONE);
		mArrowImageView.setVisibility(View.GONE);
		xlistview_header_indicator.setVisibility(GONE);
		if(flag) {
			mArrowImageView.setImageResource(R.mipmap.main_refresh_success);
		} else {
			setHintTextViewColor(getResources().getColor(R.color.main_pull_wrong));
			mArrowImageView.setImageResource(R.mipmap.main_refresh_warnning);
		}
		mHintTextView.setText(result);
	}

	/**
	 * 设置刷新文字颜色
	 */
	public void setHintTextViewColor(int color) {
		mHintTextView.setTextColor(color);
	}

	/**
	 * 只修改下拉刷新文字
	 * @param resutl
	 */
	public void setHintTextView(String resutl) {
		mHintTextView.setText(resutl);
	}

	public void resetHintTextView() {
		mProgressBar.setVisibility(View.GONE);
		mArrowImageView.setVisibility(View.VISIBLE);
		mArrowImageView.setImageResource(R.mipmap.main_pulldown);
	}

}
