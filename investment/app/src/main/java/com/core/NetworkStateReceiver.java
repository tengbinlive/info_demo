package com.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.core.util.NetworkUtil;
import com.touyan.investment.App;

public class NetworkStateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		App.setCurrentNetworkStatus(NetworkUtil.getCurrentNextworkState(context));
	}

}
