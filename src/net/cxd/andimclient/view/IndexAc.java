package net.cxd.andimclient.view;

import java.util.List;

import net.cxd.andimclient.R;
import net.cxd.andimclient.api.BaseApi;
import net.cxd.andimclient.app.MyApplication;
import net.cxd.andimclient.util.TaskId;
import net.cxd.im.entity.UserMsg;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

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
	private HandlerThread handlerThread;

	private KennerControll controll;
	private MyApplication app;

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
		pager.addView(msgAc);
		// TODO add other View

		handlerThread = new HandlerThread("main_looper");
		handlerThread.start();
		handler = new MsgHander(handlerThread.getLooper());

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

	static class MsgHander extends Handler {
		public MsgHander(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case TaskId.FIND_INDEX_MSG_LIST:
				if (msg.obj != null) {
					if (msgAc.getAdapter() == null) {
						MsgAdapter adapter = new MsgAdapter(MyApplication.ctx);
						adapter.setList((List<UserMsg>) msg.obj);
						msgAc.setAdapter(adapter);
						msgAc.getMsgList().setAdapter(adapter);
					} else {
						msgAc.getAdapter().setList((List<UserMsg>) msg.obj);
						msgAc.getAdapter().notifyDataSetChanged();
					}
				} else {
					msgAc.getMsg_nomsg().setVisibility(View.VISIBLE);
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onDestroy() {
		handlerThread.quit();
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
}
