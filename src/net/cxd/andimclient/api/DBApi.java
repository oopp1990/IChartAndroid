package net.cxd.andimclient.api;

import java.util.List;

import android.database.SQLException;

import com.nb82.bean.db.CFrameDb;
import com.nb82.entity.Task;
import com.nb82.util.DbException;

import net.cxd.im.entity.UserMsg;

public class DBApi {
	public List<UserMsg> getIndexMsgList(Task task) {
		String sql = "select distinct m.uid ,m.oid, m.msgType,m.contentType ,m.time,m.isSend,"
				+ "m.content from User m where m.time=(select max(time) from User where uid = m.uid) order by time DESC;";
		CFrameDb db = (CFrameDb) task.params.get("cFrameDb");
		try {
			return db.findAllBySql(UserMsg.class, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
}
