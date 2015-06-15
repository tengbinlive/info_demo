package com.touyan.investment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.core.util.StringUtil;
import com.gitonway.lee.niftymodaldialogeffects.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.NiftyDialogBuilder;

public abstract class AbsFragment extends Fragment {

	protected final String TAG = AbsFragment.class.getSimpleName();

	private NiftyDialogBuilder dialogBuilder;

	public abstract boolean onBackPressed();

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}


	@Override
	public void onDestroy() {
		dialogDismiss();
		super.onDestroy();
	}

	public void dialogShow() {
		dialogDismiss();
		dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
		dialogBuilder.withDuration(700) // def
				.isCancelableOnTouchOutside(false) // def | isCancelable(true)
				.withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
				.setCustomView(R.layout.loading_view, getActivity()) // .setCustomView(View
				.show();

	}

	public void dialogShow(String title) {
		dialogDismiss();
		LinearLayout convertView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.loading_view, null);
		TextView dialog_confirm_content = (TextView) convertView.findViewById(R.id.dialog_confirm_content);
		dialog_confirm_content.setText(title);
		dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
		dialogBuilder.withDuration(700) // def
				.isCancelableOnTouchOutside(false) // def | isCancelable(true)
				.withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
				.setCustomView(convertView, getActivity()) // .setCustomView(View
				.show();

	}

	public void dialogDismiss() {
		if (null != dialogBuilder && dialogBuilder.isShowing()) {
			dialogBuilder.dismiss();
		}
	}

	public void showConfirmDialog(Activity activity, String title, String content, String leftText, View.OnClickListener leftEvent, String rightText, View.OnClickListener rightEvent) {
		dialogDismiss();
		LinearLayout linearLayout = new LinearLayout(activity);
		activity.getLayoutInflater().inflate(R.layout.dialog_confirm, linearLayout);

		TextView titleTv = (TextView) linearLayout.findViewById(R.id.dialog_confirm_title);
		if (StringUtil.isBlank(title)) {
			titleTv.setVisibility(View.GONE);
		} else {
			titleTv.setVisibility(View.VISIBLE);
			titleTv.setText(title);
		}

		TextView contentTv = (TextView) linearLayout.findViewById(R.id.dialog_confirm_content);
		if (StringUtil.isBlank(content)) {
			contentTv.setVisibility(View.GONE);
		} else {
			contentTv.setVisibility(View.VISIBLE);
			contentTv.setText(content);
		}

		TextView closeLeft = (TextView) linearLayout.findViewById(R.id.dialog_confirm_closeLeft);
		if (StringUtil.isBlank(leftText)) {
			closeLeft.setVisibility(View.GONE);
		} else {
			closeLeft.setVisibility(View.VISIBLE);
			closeLeft.setText(leftText);
		}

		TextView closeRight = (TextView) linearLayout.findViewById(R.id.dialog_confirm_closeRight);
		if (StringUtil.isBlank(rightText)) {
			closeRight.setVisibility(View.GONE);
		} else {
			closeRight.setVisibility(View.VISIBLE);
			closeRight.setText(rightText);
		}

		if (StringUtil.isNotBlank(leftText) && StringUtil.isNotBlank(rightText)) {
			linearLayout.findViewById(R.id.dialog_confirm_point).setVisibility(View.VISIBLE);
		} else {
			linearLayout.findViewById(R.id.dialog_confirm_point).setVisibility(View.GONE);
		}

		closeLeft.setOnClickListener(leftEvent);
		closeRight.setOnClickListener(rightEvent);
		dialogBuilder = NiftyDialogBuilder.getInstance(activity);
		dialogBuilder.withDuration(700) // def
				.isCancelableOnTouchOutside(false) // def | isCancelable(true)
				.withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
				.setCustomView(linearLayout, activity).show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	public abstract void scrollToTop();

}
