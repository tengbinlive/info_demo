package com.gitonway.lee.niftymodaldialogeffects;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.gitonway.lee.niftymodaldialogeffects.effects.BaseEffects;

/*
 * Copyright 2014 litao
 * https://github.com/sd6352051/NiftyDialogEffects
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class NiftyDialogBuilder extends Dialog implements DialogInterface {

	private static Context tmpContext;

	private Effectstype type = null;

	private View mDialogView;

	private int mDuration = -1;

	private static int mOrientation = 1;

	private boolean isCancelable = true;

	private RelativeLayout mFrameLayoutCustomView;

	private static NiftyDialogBuilder instance;

	public NiftyDialogBuilder(Context context) {
		super(context);
		init(context);

	}

	public NiftyDialogBuilder(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes((WindowManager.LayoutParams) params);

	}

	public static NiftyDialogBuilder getInstance(Context context) {

		if (instance == null || !tmpContext.equals(context)) {
			synchronized (NiftyDialogBuilder.class) {
				if (instance == null || !tmpContext.equals(context)) {
					instance = new NiftyDialogBuilder(context, R.style.dialog_untran);
				}
			}
		}
		tmpContext = context;
		return instance;

	}

	private void init(Context context) {

		mDialogView = View.inflate(context, R.layout.dialog_layout, null);

		mFrameLayoutCustomView = (RelativeLayout) mDialogView.findViewById(R.id.customPanel);

		setContentView(mDialogView);

		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {
				if (type == null) {
					type = Effectstype.Slidetop;
				}
				start(type);

			}
		});
		mFrameLayoutCustomView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isCancelable)
					dismiss();
			}
		});
	}

	public NiftyDialogBuilder setCustomView(int resId, Context context) {
		View customView = View.inflate(context, resId, null);
		if (mFrameLayoutCustomView.getChildCount() > 0) {
			mFrameLayoutCustomView.removeAllViews();
		}
		mFrameLayoutCustomView.addView(customView);
		return this;
	}

	public NiftyDialogBuilder setCustomView(View view, Context context) {
		if (mFrameLayoutCustomView.getChildCount() > 0) {
			mFrameLayoutCustomView.removeAllViews();
		}
		mFrameLayoutCustomView.addView(view);

		return this;
	}

	public NiftyDialogBuilder withDuration(int duration) {
		this.mDuration = duration;
		return this;
	}

	public NiftyDialogBuilder withEffect(Effectstype type) {
		this.type = type;
		return this;
	}

	public NiftyDialogBuilder isCancelableOnTouchOutside(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCanceledOnTouchOutside(cancelable);
		return this;
	}

	public NiftyDialogBuilder isCancelable(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCancelable(cancelable);
		return this;
	}

	private void toggleView(View view, Object obj) {
		if (obj == null) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void show() {
		super.show();
	}

	private void start(Effectstype type) {
		BaseEffects animator = type.getAnimator();
		if (mDuration != -1) {
			animator.setDuration(Math.abs(mDuration));
		}
		animator.start(mFrameLayoutCustomView);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}
}
