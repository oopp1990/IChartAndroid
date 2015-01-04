package net.cxd.andimclient.view;

import net.cxd.andimclient.R;
import net.cxd.andimclient.view.adapter.IndexMsgAdapter;
import net.cxd.im.entity.UserMsg;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * MSG VIEW
 * 
 * @author oopp1990
 * 
 */
public class IndexMsgAc extends RelativeLayout implements
		OnItemSelectedListener, OnItemClickListener {
	private Context ctx;
	private ListView msgList;
	private TextView msg_nomsg;
	private IndexMsgAdapter adapter;

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
		Log.w("initView  >>>>　　　", "init .........");
		LayoutInflater.from(ctx).inflate(R.layout.im_msg, this);
		msgList = (ListView) findViewById(R.id.msg_listview);
		msg_nomsg = (TextView) findViewById(R.id.msg_nomsg);
		// msgList.setOnItemSelectedListener(this);
		msgList.setOnItemClickListener(this);
		// adapter = new MsgAdapter(ctx);
		// msgList.setAdapter(adapter);
	}

	public void setAdapter(IndexMsgAdapter adapter) {
		this.adapter = adapter;
	}

	public IndexMsgAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Log.w("onItemSelected", "onItemSelected。。。。。");
		Intent intent = new Intent(ctx, UserMsgAc.class);
		UserMsg msg = getAdapter().getList().get(position);
		intent.putExtra("oid", msg.getUid());
		ctx.startActivity(intent);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		Log.w("onNothingSelected", "onNothingSelected。。。。。");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.w("onItemClick />>>> ", "onItemClick");
		Log.w("onItemSelected", "onItemSelected。。。。。");
		Intent intent = new Intent(ctx, UserMsgAc.class);
		UserMsg msg = getAdapter().getList().get(position);
		intent.putExtra("oid", msg.getUid());
		ctx.startActivity(intent);
	}
}
