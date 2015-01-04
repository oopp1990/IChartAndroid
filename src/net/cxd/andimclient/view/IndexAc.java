package net.cxd.andimclient.view;

import java.util.ArrayList;
import java.util.List;

import net.cxd.andimclient.R;
import net.cxd.andimclient.api.BaseApi;
import net.cxd.andimclient.app.MyApplication;
import net.cxd.andimclient.util.TaskId;
import net.cxd.andimclient.view.adapter.IndexMsgAdapter;
import net.cxd.im.entity.UserMsg;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.nb82.core.KennerControll;
import com.nb82.entity.BaseActivity;
import com.nb82.entity.Task;

public class IndexAc extends BaseActivity {
	private ViewPager pager;
	private ImageButton index_msg;
	private ImageButton index_my;
	private ImageButton index_fabu;

	private static IndexMsgAc msgAc;

	private MsgHander handler;
//	private HandlerThread handlerThread;

	private KennerControll controll;
	private MyApplication app;
	private List<View> views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_index);
		pager = (ViewPager) findViewById(R.id.index_page);
		index_msg = (ImageButton) findViewById(R.id.index_msg);
		index_my = (ImageButton) findViewById(R.id.index_my);
		index_fabu = (ImageButton) findViewById(R.id.index_fabu);
		init(null);
	}

	@Override
	public void init(Object object) {
		msgAc = new IndexMsgAc(this);
		// pager.addView(msgAc);
		// TODO add other View
		views = new ArrayList<View>();
		views.add(msgAc);
		pager.setAdapter(new PageAdapter());

//		handlerThread = new HandlerThread("main_looper");
//		handlerThread.start();
		handler = new MsgHander();

		app = ((MyApplication) getApplication());
		controll = (KennerControll) app.cache.get("kennerControll");
		// TODO 初始化消息界面
		Task task = new Task(TaskId.FIND_INDEX_MSG_LIST, BaseApi.class,
				"getIndexMsgList", handler, null);
		controll.doTask(task);

	}

	public void changeLayout(View view) {
		switch (view.getId()) {
		case R.id.index_msg:
			// 更换到消息界面
			break;
		case R.id.index_my:
			// 更换到我的界面
			break;
		case R.id.index_fabu:
			// 更换到发布消息界面
			break;

		default:
			break;
		}
	}

	class MsgHander extends Handler {
//		public MsgHander(Looper looper) {
//			super(looper);
//		}

		@Override
		public void handleMessage(final Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case TaskId.FIND_INDEX_MSG_LIST:
				IndexAc.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (msg.obj != null) {
//							Log.w("msglist >>", JSON.toJSONString(msg.obj));
							if (msgAc.getAdapter() == null) {
								IndexMsgAdapter adapter = new IndexMsgAdapter(
										MyApplication.ctx);
								adapter.setList((List<UserMsg>) msg.obj);
								msgAc.setAdapter(adapter);
								msgAc.getMsgList().setAdapter(adapter);
							} else {
								msgAc.getAdapter().setList(
										(List<UserMsg>) msg.obj);
								msgAc.getAdapter().notifyDataSetChanged();
							}
						} else {
							msgAc.getMsg_nomsg().setVisibility(View.VISIBLE);
						}

					}
				});

				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onDestroy() {
//		handlerThread.quit();
		super.onDestroy();
	}

	public void newMsg(UserMsg msg) {
		List<UserMsg> list = msgAc.getAdapter().getList();
		if (list != null && list.size() > 0) {
			for (UserMsg userMsg : list) {
				if (msg.getUid() == userMsg.getUid()
						&& msg.getMsgType() == userMsg.getMsgType()) {
					list.remove(userMsg);
					break;
				}
			}
			list.add(0, msg);
			msgAc.getAdapter().setList(list);
			msgAc.getAdapter().notifyDataSetChanged();
		}
	}

	class PageAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		// 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			view.addView(views.get(position));
			return views.get(position);
		}

		// PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			view.removeView(views.get(position));
		}
	}

}
