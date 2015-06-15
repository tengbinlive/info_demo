package com.touyan.investment.helper;

public class ShareUtil {

	private static ShareUtil instance;

	public static ShareUtil getInstance() {
		if (instance == null) instance = new ShareUtil();
		return instance;
	}


}
