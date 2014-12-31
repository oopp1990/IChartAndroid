package net.cxd.andimclient.app;

import java.util.List;

import net.cxd.andimclient.api.UserService;
import net.cxd.andimclient.service.LocalService;
import net.cxd.im.service.UserHttpService;
import net.cxd.im.service.impl.UserHttpServiceImpl;
import net.cxd.util.HttpUri;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.nb82.bean.db.CFrameDb;
import com.nb82.core.AppContextControll;
import com.nb82.view.bitmap.CBitmap;

public class MyApplication extends AppContextControll {
	public static MyApplication ctx;
	static {
		java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
		java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		Intent intent = new Intent(this, LocalService.class);
		stopService(intent);

	}

	@Override
	public void onCreate() {
		super.onCreate();
		ctx = this;
		HttpUri.BASE_URL = "http://192.168.0.159:8080/WebImService/";
		cache.put("cbitmap", CBitmap.create(this));
		CFrameDb cFrameDb = CFrameDb.create(this, "ImClient.db", true);
		cache.put("cFrameDb", cFrameDb);
		//
		// UserHttpService httpService = new UserHttpServiceImpl();
		UserService userService = new UserService();
		cache.put("httpService", userService);

		Intent intent = new Intent(this, LocalService.class);
		startService(intent);

	}

	/**
	 * 判断当前应用程序处于前台还是后台
	 * 
	 * @param context
	 * 
	 * @return
	 */
	public static ComponentName isApplicationBroughtToBackground(
			final Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			return topActivity;
			// if
			// (!topActivity.getPackageName().equals(context.getPackageName()))
			// {
			// return true;
			// }
		}
		return null;

	}
}
