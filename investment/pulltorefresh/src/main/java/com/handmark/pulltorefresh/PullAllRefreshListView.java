/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class PullAllRefreshListView extends PullToRefreshListView {

	private OnPullPathRefluseListener onPullPathRefluseListener;
	private final int PULL_SPACE_TO_REFLUSE = 300;

	private volatile boolean AnimationISRun = false;
	private volatile boolean AnimUpIsRun = false;

	public PullAllRefreshListView(Context context) {
		super(context);
	}

	public PullAllRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullAllRefreshListView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullAllRefreshListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	protected ListView createListView(Context context, AttributeSet attrs) {
		ListView lv = new PullPathListView(context, attrs);
		return lv;
	}

	public void setOnPullPathRefluseListener(OnPullPathRefluseListener onPullPathRefluseListener) {
		this.onPullPathRefluseListener = onPullPathRefluseListener;
	}

	public interface OnPullPathRefluseListener {
		public void onRefluse(PullPathListView refluseView);

		public void onPulling(int pullPrecent);

	}

	public class PullPathListView extends InternalListView {

		private Context mContext;
		private Scroller mScroller;
		private PullPathTouchTool tool;
		private int left, top, bgtop;
		private float startX, startY, currentX, currentY;
		private int bgViewH, iv1W;
		private int rootW, rootH;
		private View headView;
		private View bgView;
		boolean scrollerType;
		static final int len = 0xc8;
		private boolean isMovieDetail = false;
		private Handler mhandler;

		private boolean canPull = true;

		private float lastY;

		private float previousY = 0;
		private float recentY = 0;
		
		private int default_height;

		private int ScreenWidth,ScreenHeight;

		public void setDefault_height(int default_height) {
			this.default_height = default_height;
		}

		public PullPathListView(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.mContext = context;
			mScroller = new Scroller(mContext);
		}

		public PullPathListView(Context context) {
			this(context, null);
		}

		public void setCanPull(boolean state) {
			canPull = state;
		}

		public void SetIsMovie(boolean isMovie) {
			isMovieDetail = isMovie;
		}

		public void SetHandler(Handler handler) {
			mhandler = handler;
		}

		public boolean getAnimIsRun() {
			return AnimationISRun;
		}

		public void setAnimUpRun(boolean isRun) {
			AnimUpIsRun = isRun;
		}

		public boolean getAnimUpIsRun() {
			return AnimUpIsRun;
		}

		public void setPullPathView(View pullHeadView, View bgImageView) {
			headView = pullHeadView;
			bgView = bgImageView;
			int location[] = { 0, 0 };
			bgView.getLocationOnScreen(location);
			bgtop = location[1];
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent event) {
			if (!canPull)
				return super.dispatchTouchEvent(event);

			if (AnimationISRun || AnimUpIsRun) {
				return true;
			}
			int action = event.getAction();
			if (bgView == null || headView == null || !mScroller.isFinished()) {
				return super.onTouchEvent(event);
			}
			currentX = event.getX();
			currentY = event.getY();
//			headView.getTop();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				left = bgView.getLeft();
				top = bgView.getBottom();
				rootW = getWidth();
				rootH = getHeight();
				bgViewH = bgView.getHeight();
				startX = currentX;
				startY = currentY;
				tool = new PullPathTouchTool(bgView.getLeft(), bgView.getBottom(), bgView.getLeft(), bgView.getBottom()
						+ len);
				lastY = currentY;
				
				int locationBgView[] = { 0, 0 };
				bgView.getLocationOnScreen(locationBgView);
				bgtop = locationBgView[1];
				
				break;
			case MotionEvent.ACTION_MOVE:
				if (headView.isShown() && bgtop >= 0) {
					if (tool != null) {
						float pullSpace = currentY - startY;
						int t = tool.getScrollY(pullSpace);
						if (t >= top && t <= headView.getBottom() + len) {
							bgView.setLayoutParams(new RelativeLayout.LayoutParams(bgView.getWidth(), t));
							if (onPullPathRefluseListener != null) {
								onPullPathRefluseListener.onPulling((int) pullSpace);
							}
						}
					}
					scrollerType = false;
				}

				if (iMotionCallback != null) {
					float currY = iMotionCallback.onTouchScroll(lastY, currentY);
					if (currY != -1)
						lastY = currY;
				}

				if (recentY != 0) {
					previousY = recentY;
				}
				recentY = currentY;
				break;
			case MotionEvent.ACTION_UP:
				scrollerType = true;
				mScroller.startScroll(bgView.getLeft(), bgView.getBottom(), 0 - bgView.getLeft(),
						bgViewH - bgView.getBottom(), 200);

				if (headView.isShown() && currentY - startY >= PULL_SPACE_TO_REFLUSE) {
					if (onPullPathRefluseListener != null) {
						onPullPathRefluseListener.onRefluse(this);
					}
				}
				invalidate();
				if (isMovieDetail) {
					if (tool != null) {
						float pullSpace = currentY - startY;
						int location[] = { 0, 0 };
						bgView.getLocationOnScreen(location);
						int t = tool.getScrollY(pullSpace);
						if ((location[1] - bgtop) >= 0 && t >= top + ScreenWidth / 6) {
							animHide(location[1] + t, mhandler);
						}
					}
				}

				if (iMotionCallback != null)
					iMotionCallback.onTouchRelease(recentY - previousY);

				lastY = -1;

				previousY = 0;
				recentY = 0;

				break;
			}

			return super.dispatchTouchEvent(event);
		}

		public void animHide(int height, final Handler handler) {
			if (AnimUpIsRun) {
				return;
			}
			TranslateAnimation animation = new TranslateAnimation(0, 0, 0, ScreenHeight - height);
			animation.setInterpolator(new LinearInterpolator());
			animation.setDuration(500);
			animation.setStartOffset(0);
			AnimationISRun = true;
			animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
					setVisibility(GONE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					clearAnimation();
					setVisibility(View.INVISIBLE);
					bgView.setLayoutParams(new RelativeLayout.LayoutParams(bgView.getWidth(), default_height));
					AnimationISRun = false;
					Message msg = new Message();
					msg.what = 1001; // 动画结束发送消息,是否显示帮助
					handler.sendMessage(msg);
				}
			});
			startAnimation(animation);

		}

		public void computeScroll() {
			if (mScroller.computeScrollOffset()) {
				int x = mScroller.getCurrX();
				int y = mScroller.getCurrY();
				bgView.layout(0, 0, x + bgView.getWidth(), y);
				invalidate();
				if (!mScroller.isFinished() && scrollerType && y > 200) {
					bgView.setLayoutParams(new RelativeLayout.LayoutParams(bgView.getWidth(), y));
					if (onPullPathRefluseListener != null) {
						onPullPathRefluseListener.onPulling(y - top);
					}
				}
			}
		}

		public void setScreenHeight(int screenHeight) {
			ScreenHeight = screenHeight;
		}

		public void setScreenWidth(int screenWidth) {
			ScreenWidth = screenWidth;
		}
	}

	private IMotionEventCallback iMotionCallback;

	public interface IMotionEventCallback {
		public float onTouchScroll(float lastY, float nowY);

		public void onTouchRelease(float pullSpace);
	}

	public void setMotionEventCallback(IMotionEventCallback callback) {
		this.iMotionCallback = callback;
	}

}
