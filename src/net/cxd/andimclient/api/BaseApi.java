package net.cxd.andimclient.api;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

import net.cxd.andimclient.app.MyApplication;
import net.cxd.im.entity.UserMsg;
import android.database.SQLException;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.nb82.bean.db.CFrameDb;
import com.nb82.entity.Task;
import com.nb82.util.DbException;

public class BaseApi {
	static ByteBuffer buffer = ByteBuffer.allocate(1024);

	public synchronized boolean sendMsg(Task task) throws Exception {
		SocketChannel channel = (SocketChannel) task.params.get("channel");
		UserMsg msg = (UserMsg) task.params.get("msg");
		CFrameDb db = (CFrameDb) task.params.get("db");
		if (channel != null && channel.isConnected()) {
			buffer.clear();
			buffer.put(msg.toString().getBytes());
			channel.write(buffer);
			channel.configureBlocking(false);
		}
		// TODO save
		int uid = msg.getUid();
		int oid = msg.getOid();
		msg.setUid(uid);
		msg.setOid(oid);
		msg.setIsSend(1);// 发送者
		db.save(msg);
		return true;
	}

	public List<UserMsg> getIndexMsgList(Task task) {
		String sql = "select distinct m.uid ,m.oid, m.msgType,m.contentType ,m.time,m.isSend,m.photoFile,m.isRead,"
				+ "m.content from UserMsg m where m.time=(select max(time) from UserMsg where uid = m.uid) order by time DESC;";
		CFrameDb db = (CFrameDb) MyApplication.ctx.cache.get("cFrameDb");
		try {
			Log.w("sql>>>>", sql);
			List<UserMsg> msgs = db.findAllBySql(UserMsg.class, sql);
			// Log.w("msgs >>>> ", JSON.toJSONString(msgs));
			if (msgs != null && msgs.size() > 0)
				return msgs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<UserMsg> getUserMsgList(Task task) throws Exception {
		CFrameDb db = (CFrameDb) MyApplication.ctx.cache.get("cFrameDb");
		String uid = (String) task.params.get("oid");
		int pageNum = (Integer) task.params.get("pageNum");
		String sql = "select * from UserMsg where uid=" + uid
				+ " order by time desc  limit " + (pageNum * 10) + ","
				+ (pageNum * 10 + 10);
		Log.w("sql", sql);
		return db.findAllBySql(UserMsg.class, sql);
	}
}
