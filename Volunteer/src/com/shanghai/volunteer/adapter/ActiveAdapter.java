package com.shanghai.volunteer.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.adapter.NewsListAdapter.ViewHolder;
import com.shanghai.volunteer.bean.Active;
import com.shanghai.volunteer.bean.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActiveAdapter extends BaseAdapter {

	private ArrayList<Active> data;
	private Context context;
	private LayoutInflater mInflater;
	private ViewHolder vh = null;


	public ActiveAdapter(Context context) {
		super();
		this.context = context;
	}

	public ArrayList<Active> getData() {
		return data;
	}

	public void setData(ArrayList<Active> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.listview_item_activity,
					null);
			vh = new ViewHolder();
			vh.activeTitle = (TextView) convertView
					.findViewById(R.id.activityTitleTV);
			vh.activeDetail = (TextView) convertView
					.findViewById(R.id.activityIDetailTV);
			vh.activeUnit = (TextView) convertView
					.findViewById(R.id.activityUnitTV);
			vh.activeTime = (TextView) convertView
					.findViewById(R.id.activityTimeTV);
			vh.activityIsComplete = (TextView) convertView
					.findViewById(R.id.activityIsComplete);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		// 判断任务是否完成
		if (data.get(position).getStatus().equals("招募中")) {
			vh.activityIsComplete.setBackgroundResource(R.drawable.activity_textview_bg_green);
		} else if (data.get(position).getStatus().equals("已招满")) {
			vh.activityIsComplete.setBackgroundResource(R.drawable.activity_textview_bg_yellow);
		} else {
			vh.activityIsComplete.setBackgroundResource(R.drawable.activity_textview_bg_red);
		}
		vh.activeTitle.setText(data.get(position).getTitle());
		vh.activeDetail.setText(data.get(position).getDetails());
		vh.activityIsComplete.setText(data.get(position).getStatus());
		vh.activeUnit.setText(data.get(position).getUnit());
		vh.activeTime.setText(data.get(position).getCreatTime());
		return convertView;
	}

	class ViewHolder {
		private TextView activeTitle;
		private TextView activeDetail;
		private TextView activeUnit;
		private TextView activeTime;
		private TextView activityIsComplete;
	}
}
