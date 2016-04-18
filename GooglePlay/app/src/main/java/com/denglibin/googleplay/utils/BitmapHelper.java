package com.denglibin.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;



import com.lidroid.xutils.BitmapUtils;

import android.content.Context;

import com.denglibin.googleplay.utils.FileUtils;
import com.denglibin.googleplay.utils.UiUtils;
import com.lidroid.xutils.BitmapUtils;

public class BitmapHelper {
	private BitmapHelper() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils() {
		if (bitmapUtils == null) {
			// 第二个参数 缓存图片的路径 // 加载图片 最多消耗多少比例的内存 0.05-0.8f
			bitmapUtils = new BitmapUtils(UiUtils.getContext(), FileUtils
					.getIconDir().getAbsolutePath(), 0.3f);
		}
		return bitmapUtils;
	}
}
