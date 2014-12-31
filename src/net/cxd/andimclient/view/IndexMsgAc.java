package net.cxd.andimclient.view;

import net.cxd.andimclient.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * MSG   VIEW
 * @author oopp1990
 *
 */
public class IndexMsgAc extends RelativeLayout{
	private Context ctx ;
	private ListView msgList;
	private TextView msg_nomsg;
	private MsgAdapter adapter;
	
	public Context getCtx() {
		return ctx;
	}
	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}
	public ListView getMsgList() {
		return msgList;
	}
	public void setMsgList(ListView msgList) {
		this.msgList = msgList;
	}
	public TextView getMsg_nomsg() {
		return msg_nomsg;
	}
	public void setMsg_nomsg(TextView msg_nomsg) {
		this.msg_nomsg = msg_nomsg;
	}
	public IndexMsgAc(Context context) {
		super(context);
		this.ctx = context;
		initView();
	}
	private void initView() {
		LayoutInflater.from(ctx).inflate(R.layout.im_msg, this);
		msgList = (ListView) findViewById(R.id.msg_listview);
		msg_nomsg = (TextView) findViewById(R.id.msg_nomsg);
//		adapter = new MsgAdapter(ctx);
//		msgList.setAdapter(adapter);
	}
	public void setAdapter(MsgAdapter adapter) {
		this.adapter = adapter;
	}
	public MsgAdapter getAdapter() {
		return adapter;
	}
	
}
