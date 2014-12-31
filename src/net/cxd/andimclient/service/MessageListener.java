package net.cxd.andimclient.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import android.app.Activity;
import android.content.ComponentName;

import com.alibaba.fastjson.JSON;
import com.nb82.bean.db.CFrameDb;
import com.nb82.util.AcUtil;

import net.cxd.andimclient.app.MyApplication;
import net.cxd.andimclient.util.SoundManager;
import net.cxd.andimclient.view.IndexAc;
import net.cxd.andimclient.view.UserMsgAc;
import net.cxd.im.entity.UserMsg;
import net.cxd.im.server.MessageLister;

public class MessageListener extends MessageLister {

	@Override
	public void open(SocketChannel channel) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void openComplate(SocketChannel channel) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(SocketChannel channel, ByteBuffer receivebuffer)
			throws IOException {
		// TODO Auto-generated method stub

	}

	public void read(SocketChannel channel, byte[] buffer) throws IOException {
		if (buffer[0] == 0) {
			return;
		}
		if (buffer[1] == START_BYTE && buffer[buffer.length - 1] == END_CHAR) {
			// 完整数据包，进行解析
			try {
				String str = new String(buffer, 1, buffer.length - 1);
				UserMsg msg = JSON.parseObject(str, UserMsg.class);
				if (AcUtil.getAc() != null) {
					if ("UserMsgAc".equals(AcUtil.getAc().getClass()
							.getSimpleName())) {
						UserMsgAc msgAc = (UserMsgAc) AcUtil.getAc();
						if (msgAc.getOid() == msg.getUid()) {
							// 收到消息和当前聊天用户ID一样
							msg.setIsRead(1);// 已读
							msgAc.newMsg(msg);
						}else{
							SoundManager.newInstance(MyApplication.ctx).playSound(1);
						}
					} else {
						// TODO 播放声音， 并且将未读消息添加到IndexMsg上面，并且显示小红点
						if ("IndexAc".equals(AcUtil.getAc().getClass()
								.getSimpleName())) {
							IndexAc indexAc = (IndexAc) AcUtil.getAc();
							indexAc.newMsg(msg);
						}
						SoundManager.newInstance(MyApplication.ctx).playSound(1);
					}
				} else {
					// TODO notify
					SoundManager.newInstance(MyApplication.ctx).notifyAction(msg);
				}
				// save msg
				CFrameDb cFrameDb = (CFrameDb) MyApplication.ctx.cache.get("cFrameDb");
				cFrameDb.save(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// TODO 判断当前界面 是否是聊天界面， 如果不是则进行notifyaction 如果是， 则更新界面
		} else
			return;// 丢弃该数据包，因为不完整。

	}

	@Override
	public void cachtException(Exception e) throws IOException {
		// TODO Auto-generated method stub
		e.printStackTrace();
	}
}
