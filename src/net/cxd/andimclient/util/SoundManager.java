package net.cxd.andimclient.util;

import java.util.HashMap;

import net.cxd.andimclient.R;
import net.cxd.andimclient.app.MyApplication;
import net.cxd.andimclient.view.UserMsgAc;
import net.cxd.im.entity.UserMsg;
import net.cxd.util.MsgType;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

public class SoundManager {

	private static SoundManager soundManager;
	private Context context;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	public static final int SHOOT = 1;

	private SoundManager(Context context) {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(SHOOT, soundPool.load(context, R.raw.beep, 1));
		this.context = context;
	}

	public static SoundManager newInstance(Context context) {
		if (soundManager == null) {
			soundManager = new SoundManager(context);

		}
		return soundManager;
	}

	public void playSound(int type) {
		AudioManager mgr = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;

		/* 使用正确音量播放声音 */
		soundPool.play(soundPoolMap.get(type), volume, volume, 1, 0, 1f);
	}

	public void vibrate() {
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);
		vibrator.vibrate(new long[] { 100, 10, 10, 100 }, -1); // -1短震动
	}

	public void notifyAction(UserMsg msg) {
		vibrate();
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = msg.getNickName() + " \n " + msg.getContent();
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);

		// 定义下拉通知栏时要展现的内容信息
		CharSequence contentTitle = msg.getNickName();
		CharSequence contentText = "";
		if (msg.getMsgType() == MsgType.IMG.getType()) {
			contentText = "图片";
		} else if (msg.getMsgType() == MsgType.FILE.getType()) {
			contentText = "文件";
		} else {
			contentText = msg.getContent();
		}
		// CharSequence contentText = entity.getContent();
		Intent notificationIntent = new Intent(MyApplication.ctx, UserMsgAc.class);
		PendingIntent contentIntent = PendingIntent.getActivity(
				MyApplication.ctx, 0, notificationIntent, 0);
		notification.setLatestEventInfo(MyApplication.ctx, contentTitle,
				contentText, contentIntent);
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		// 叠加效果常量
		notification.defaults = Notification.DEFAULT_LIGHTS
				| Notification.DEFAULT_SOUND;

		notification.ledARGB = Color.BLUE;
		notification.ledOnMS = 5000;
		// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
		mNotificationManager.notify(1, notification);

	}

}
