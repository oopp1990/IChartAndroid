package net.cxd.andimclient.view;

import net.cxd.andimclient.R;
import net.cxd.andimclient.api.UserService;
import net.cxd.andimclient.app.MyApplication;
import net.cxd.andimclient.util.TaskId;
import net.cxd.im.entity.ResultBean;
import net.cxd.im.entity.User;
import net.cxd.im.entity.UserInfo;
import net.cxd.im.entity.UserMsg;
import net.cxd.util.HttpUri;
import net.cxd.util.MsgType;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nb82.bean.db.CFrameDb;
import com.nb82.bean.db.sqlite.DbModel;
import com.nb82.core.KennerControll;
import com.nb82.entity.BaseActivity;
import com.nb82.entity.Task;
import com.nb82.util.DbException;

public class StartAc extends BaseActivity {
	KennerControll kennerControll;
	MyApplication app;
	String name;
	String password;
	private boolean isLogin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_start);
		HttpUri.BASE_URL = "http://192.168.0.161:8080/WebImService/";
		handler = new MyHandler();
	}

	@Override
	protected void onResume() {
		super.onResume();
		init(null);
	}

	@Override
	public void init(Object object) {
		app = ((MyApplication) getApplication());
		kennerControll = (KennerControll) app.cache.get("kennerControll");
		try {
			DbModel model = ((CFrameDb) app.cache.get("cFrameDb"))
					.findDbModelBySQL(" select * from User where lastLogin=(select max(lastLogin) from User)");
			if (model != null) {
				name = model.get("name").toString();
				password = model.get("password").toString();
				setContentView(R.layout.im_start);
				isLogin = true;
			} else
				setContentView(R.layout.im_login);
		} catch (Exception e) {
			e.printStackTrace();
			setContentView(R.layout.im_login);
		}
		if (name != null && password != null)
			click(null);
		else
			setContentView(R.layout.im_login);
	}

	@Override
	public void freash(Object object) {
		ResultBean resultBean = (ResultBean) object;
		if (resultBean.isResult()) {
			UserInfo info = JSON.parseObject(resultBean.getMessage(),
					UserInfo.class);
			app.cache.put("userInfo", info);
			try {
				User user = new User(name, password);
				user.setLastLogin(System.currentTimeMillis());
				CFrameDb db = ((CFrameDb) app.cache.get("cFrameDb"));
				if (isLogin)
					db.update(user);
				else
					db.save(user);

				UserMsg msg = new UserMsg();
				msg.setUid(1001);
				msg.setOid(info.getUid());
				msg.setNickName("测试1");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);

				msg = new UserMsg();
				msg.setUid(1002);
				msg.setOid(info.getUid());
				msg.setNickName("测试2");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);

				msg = new UserMsg();
				msg.setUid(1003);
				msg.setOid(info.getUid());
				msg.setNickName("测试3");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);

				msg = new UserMsg();
				msg.setUid(1004);
				msg.setOid(info.getUid());
				msg.setNickName("测试4");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);

				msg = new UserMsg();
				msg.setUid(1005);
				msg.setOid(info.getUid());
				msg.setNickName("测试5");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);

				msg = new UserMsg();
				msg.setUid(1006);
				msg.setOid(info.getUid());
				msg.setNickName("测试6");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);

				msg = new UserMsg();
				msg.setUid(1006);
				msg.setOid(info.getUid());
				msg.setNickName("测试6");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);
				
				msg = new UserMsg();
				msg.setUid(1007);
				msg.setOid(info.getUid());
				msg.setNickName("测试7");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);
				
				msg = new UserMsg();
				msg.setUid(1008);
				msg.setOid(info.getUid());
				msg.setNickName("测试8");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);
				
				msg = new UserMsg();
				msg.setUid(1009);
				msg.setOid(info.getUid());
				msg.setNickName("测试9");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);
				
				msg = new UserMsg();
				msg.setUid(1010);
				msg.setOid(info.getUid());
				msg.setNickName("测试10");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);
				
				msg = new UserMsg();
				msg.setUid(1011);
				msg.setOid(info.getUid());
				msg.setNickName("测试11");
				msg.setMsgType(MsgType.USERMSG.getType());
				msg.setContentType(MsgType.TXT.getType());
				msg.setTime(System.currentTimeMillis());
				msg.setPhotoFile("http://img.iknow.bdimg.com/zhidaoribao2014/2015year/0104/jctj.jpg?t=1420329621");
				msg.setContent("这里是测试内容， 正在进行测试查看测试效果！......");
				db.save(msg);
			} catch (DbException e) {
				e.printStackTrace();
			}

			Intent intent = new Intent(this, IndexAc.class);
			startActivity(intent);
			sendBroadcast(new Intent("im.user.startImServer"));
			finish();
		} else {
			setContentView(R.layout.im_login);
		}
		Toast.makeText(this, resultBean.getMessage(), Toast.LENGTH_SHORT)
				.show();
	}

	public void back(View view) {
		setContentView(R.layout.im_login);
	}

	public void click(View view) {
		if (view == null || view.getId() == R.id.submit) {// login
			if (view != null) {
				name = ((EditText) findViewById(R.id.username)).getText()
						.toString();
				password = ((EditText) findViewById(R.id.password)).getText()
						.toString();
			}
			Task task = new Task(TaskId.login, UserService.class, "login",
					handler, null);
			task.params.put("name", name);
			task.params.put("password", password);
			task.obj = app.cache.get("userService");
			kennerControll.doTask(task);
		} else if (view.getId() == R.id.regnow) {
			setContentView(R.layout.regesit);
		} else if (view.getId() == R.id.resetpas) {// TODO reset password
		} else if (view.getId() == R.id.reg_submit) {// TODO regist
			EditText ename = (EditText) findViewById(R.id.ename);
			EditText epwd1 = (EditText) findViewById(R.id.pwd1);
			EditText epwd2 = (EditText) findViewById(R.id.pwd2);
			name = ename.getText().toString();
			String pwd1 = epwd1.getText().toString();
			String pwd2 = epwd2.getText().toString();
			if (name == null || name.length() <= 6) {
				Toast.makeText(this, "用户名长度必须大于等于6个字符！", Toast.LENGTH_SHORT)
						.show();
				return;
			} else if ((pwd1 == null || pwd1.length() <= 6)
					&& (pwd2 == null || pwd2.length() <= 6)) {
				Toast.makeText(this, "密码长度必须大于等于6个字符！", Toast.LENGTH_SHORT)
						.show();
				return;
			} else if (!pwd1.equals(pwd2)) {
				Toast.makeText(this, "两次输入的密码不一样！！", Toast.LENGTH_SHORT).show();
				return;
			}
			Log.i("login >>>>>>>>> ", "username:" + name + " password : "
					+ password);
			Task task = new Task(TaskId.regist, UserService.class, "regist",
					handler, null);
			task.params.put("name", name);
			task.params.put("password", (password = pwd1));
			task.obj = app.cache.get("userService");
			kennerControll.doTask(task);
		}

	}

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case TaskId.login:
				if (msg.obj != null) {
					freash(msg.obj);
				}
				break;
			case TaskId.regist:
				if (msg.obj != null) {
					freash(msg.obj);
				}
				break;
			default:
				break;
			}

		}
	}

	private Handler handler;
}
