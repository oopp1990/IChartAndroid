package net.cxd.andimclient.api;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

import net.cxd.im.entity.UserMsg;
import android.database.SQLException;

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
		//TODO save
		int uid = msg.getUid();
		int oid = msg.getOid();
		msg.setUid(uid);
		msg.setOid(oid);
		msg.setIsSend(1);//发送者
		db.save(msg);
		return true;
	}
	
	
	
	public List<UserMsg> getIndexMsgList(Task task) {
		String sql = "select distinct m.uid ,m.oid, m.msgType,m.contentType ,m.time,m.isSend,"
				+ "m.content from UserMsg m where m.time=(select max(time) from UserMsg where uid = m.uid) order by time DESC;";
		CFrameDb db = (CFrameDb) task.params.get("cFrameDb");
		try {
			return db.findAllBySql(UserMsg.class, sql);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
