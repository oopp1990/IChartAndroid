package net.cxd.andimclient.view;

import java.util.ArrayList;
import java.util.List;

import net.cxd.andimclient.R;
import net.cxd.andimclient.api.BaseApi;
import net.cxd.andimclient.app.MyApplication;
import net.cxd.andimclient.view.adapter.UserMsgAdapter;
import net.cxd.im.entity.UserMsg;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.nb82.core.KennerControll;
import com.nb82.entity.BaseActivity;
import com.nb82.entity.Task;

public class UserMsgAc extends BaseActivity {
	private int oid;
	private ListView listView;
	private KennerControll controll;
	private UserMsgAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_chat);
		listView = (ListView) findViewById(R.id.chat_listview);
		// TODO 分页查询该用户的消息
		oid = getIntent().getIntExtra("oid", 0);
		init(null);
	}

	@Override
	public void init(Object object) {
		super.init(object);
		controll = (KennerControll) MyApplication.ctx.cache
				.get("kennerControll");
		Task task = new Task(BaseApi.class, "getUserMsgList", handler, null);
		task.params.put("oid", oid+"");
		task.params.put("pageNum", 0);
		controll.doTask(task);
	}

	@Override
	public void freash(Object object) {
		try {
			if (object != null) {
				List<UserMsg> msgs = (List<UserMsg>) object;
				Log.w("freash >>> ", JSON.toJSONString(msgs));
				adapter = new UserMsgAdapter(msgs);
				listView.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public void newMsg(UserMsg msg) {
		if (adapter == null) {
			adapter = new UserMsgAdapter(new ArrayList<UserMsg>());
			adapter.getMsgs().add(0, msg);
			listView.setAdapter(adapter);
		} else {
			adapter.getMsgs().add(0, msg);
			adapter.notifyDataSetChanged();
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			final Object obj = msg.obj;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					freash(obj);
				}
			});
		}
	};
	
	public void back(View v){
		finish();
	}
	
	
}
