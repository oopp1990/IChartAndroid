package net.cxd.andimclient.view.adapter;

import java.util.List;

import com.nb82.view.bitmap.CBitmap;

import net.cxd.andimclient.R;
import net.cxd.andimclient.app.MyApplication;
import net.cxd.andimclient.util.TimeConventUtil;
import net.cxd.im.entity.UserMsg;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserMsgAdapter extends BaseAdapter {
	private List<UserMsg> msgs;
	private CBitmap cbitmap;

	public UserMsgAdapter(List<UserMsg> msgs) {
		this.msgs = msgs;
		cbitmap = (CBitmap) MyApplication.ctx.cache.get("cbitmap");
	}

	@Override
	public int getCount() {
		return msgs == null ? 0 : msgs.size();
	}

	@Override
	public Object getItem(int position) {
		return msgs == null ? null : msgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Hoder hoder = null;
		UserMsg msg = msgs.get(position);
		if (convertView != null && convertView.getTag() != null)
			hoder = (Hoder) convertView.getTag();
		else {
			if (msg.getIsSend() == 1) // TODO send
				convertView = LayoutInflater.from(MyApplication.ctx).inflate(
						R.layout.chatting_item_msg_text_right, null);
			else
				convertView = LayoutInflater.from(MyApplication.ctx).inflate(
						R.layout.chatting_item_msg_text_left, null);

			hoder = new Hoder();
			convertView.setTag(hoder);
		}
		hoder.iv_userhead = (ImageView) convertView
				.findViewById(R.id.iv_userhead);
		hoder.tv_sendtime = (TextView) convertView
				.findViewById(R.id.tv_sendtime);
		hoder.tv_username = (TextView) convertView
				.findViewById(R.id.tv_username);
		hoder.tv_chatcontent = (TextView) convertView
				.findViewById(R.id.tv_chatcontent);

		// msg.getPhotoFile()
		try {
			hoder.tv_sendtime.setText(TimeConventUtil.timeConvent(System
					.currentTimeMillis()));
			if (msg.getPhotoFile() != null)
				cbitmap.display(hoder.iv_userhead, msg.getPhotoFile());
			if (msg.getNickName() != null)
				hoder.tv_username.setText(msg.getNickName());
			if (msg.getContent() != null)
				hoder.tv_chatcontent.setText(msg.getContent());// TODO chose
																// content type
																// .if it is
																// emoji/img
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	public List<UserMsg> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<UserMsg> msgs) {
		this.msgs = msgs;
	}

	class Hoder {
		public TextView tv_sendtime;
		public TextView tv_chatcontent;
		public TextView tv_username;
		public ImageView iv_userhead;
	}
}
