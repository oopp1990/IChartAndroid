package net.cxd.andimclient.service;

import net.cxd.andimclient.app.MyApplication;
import net.cxd.im.server.ImServer;
import net.cxd.im.server.ImServer.ImServerConfig;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {
	private static final String tag = "LocalService >>> ";
	private ImServer imServer;
	private Thread ImThread;
	private Activity ac;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(tag, " service staring ...... ");
		IntentFilter filter = new IntentFilter();
		filter.addAction("im.user.startImServer");
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(new NetBroadCastReceiver(), filter);
		ImServerConfig config = new ImServerConfig();
		config.ipHost = "192.168.0.159";
		config.port = 8000;
		config.KeepOnlive = true;
		imServer = new ImServer(config, new MessageListener());
		ImThread = new Thread(imServer);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	public interface MyBinder {
		Service getService();
	}

	public class LocalBinder extends Binder implements MyBinder {
		@Override
		public Service getService() {
			return LocalService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new LocalBinder();
	}
	public Activity getAc() {
		return ac;
	}

	public void setAc(Activity ac) {
		this.ac = ac;
	}
	private boolean isOnline = false;
	class NetBroadCastReceiver extends BroadcastReceiver {
		private ConnectivityManager connectivityManager;
		private NetworkInfo info;

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				connectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					Log.i(tag, "当前有可用网络！");
					isOnline = true;
					if (MyApplication.ctx.cache.get("user") != null) {
						if (imServer.getChannel() == null) {
							ImThread.interrupt();
							ImThread.start();
						}
					}
				} else {
					Log.i(tag, "当前没有可用网络！");
					isOnline = false;
					ImThread.interrupt();
				}
			} else if (action.equals("im.user.startImServer")) {
				ImThread.interrupt();
				ImThread.start();
			}
		}
	}
}
