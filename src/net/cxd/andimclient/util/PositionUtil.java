package net.cxd.andimclient.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class PositionUtil {
	public static int getPosition(Activity ac) {
		DisplayMetrics metric = new DisplayMetrics();
		ac.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 宽度（PX） int height =
		int height = metric.heightPixels;// metric.heightPixels; // 高度（PX）
		System.err.println( "width:"+ width +"\n  height:"+ height);
		return width + height;
	}
	public static int getWidth(Activity ac){
		DisplayMetrics metric = new DisplayMetrics();
		ac.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 宽度（PX） int height =
		return width ;
	}
	public static int getHeight(Activity ac){
		DisplayMetrics metric = new DisplayMetrics();
		ac.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int height = metric.heightPixels;// metric.heightPixels; // 高度（PX）
		return height ;
	}
	public static int getGDSize(Activity ac){
		int a = getPosition(ac);
		if ( 0 < a && a  <= 1400) {
			return 1;
		}else if (1400 < a && a  <= 1600) {
			return 2 ;
		}else if (1600 < a && a  <= 2100) {
			return 3 ;
		}else if (2100 < a && a  <= 3100) {
			return 4 ;
		}else {
			return 4 ;
		}
	}
}
