package com.core.util;

import android.os.Environment;
import com.touyan.investment.Constant;

import java.io.File;

public final class FileDataHelper {

	private static final String TAG = FileDataHelper.class.getSimpleName();

	private FileDataHelper() {

	}

	/**
	 * 初始化APP在存储器中的目录,如不存在则创建.
	 */
	public static boolean initDirectory() {
		boolean result = false;
		String root = null;
		File rootFile = null;

		try {
			// 获得APP根目录(SDCard或者内部存储器)
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				rootFile = Environment.getExternalStorageDirectory();
				if (rootFile.exists()) {
					root = rootFile.toString();
					Log.i(TAG, "sdcard is using!");
				} else {
					Log.i(TAG, "sdcard not use!");
				}
			}

			// 如果没有找到SDCard
			if (root == null) {
				rootFile = new File("/flash");
				if (rootFile.exists()) {
					root = rootFile.toString();
					Log.i(TAG, "/flase is using!");
				} else {
					Log.i(TAG, "/flase not use!");
				}
			}

			if (rootFile != null) {
				File rootDir = new File(rootFile + Constant.Dir.ROOT_DIR);
				File downloadDir = new File(rootFile + Constant.Dir.DOWNLOAD_DIR);
				File imageDir = new File(rootFile + Constant.Dir.IMAGE_DIR);
				File cacheDir = new File(rootFile + Constant.Dir.CACHE_DIR);

				if (!rootDir.exists()) {
					rootDir.mkdirs();
					Log.i(TAG, rootDir + " is not exist, created succeed!");
				}
				if (!downloadDir.exists()) {
					downloadDir.mkdirs();
					Log.i(TAG, downloadDir + " is not exist, created succeed!");
				}
				if (!imageDir.exists()) {
					imageDir.mkdirs();
					Log.i(TAG, imageDir + " is not exist, created succeed!");
				}
				if (!cacheDir.exists()) {
					cacheDir.mkdirs();
					Log.i(TAG, cacheDir + " is not exist, created succeed!");
				}
				result = true;
			} else {
				Log.e(TAG, "rootFile is null, created failed");
			}
		} catch (Exception e) {
			Log.e(TAG, "create directory Exception, " + e);
		}
		return result;
	}
}
