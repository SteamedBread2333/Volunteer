package com.shanghai.volunteer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.view.RoundedImageView;
import com.shanghai.volunteer.adapter.ActiveAdapter.ViewHolder;
import com.shanghai.volunteer.bean.MeinCommend;

public class CommandAdapter extends BaseAdapter {

	private ArrayList<MeinCommend> data = null;
	private Context context;
	private LayoutInflater mInflater;
	private ViewHolder vh = null;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类

	public CommandAdapter(Context context) {
		super();
		this.context = context;
		options = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
				// .showImageOnLoading(R.drawable.icon)
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build();
	}

	public ArrayList<MeinCommend> getData() {
		return data;
	}

	public void setData(ArrayList<MeinCommend> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (data != null) {
			return data.size();
		}
		return 0;
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
			convertView = mInflater.inflate(R.layout.listview_item_command,
					null);
			vh = new ViewHolder();
			vh.commandUserImg = (RoundedImageView) convertView
					.findViewById(R.id.commandUserImg);
			vh.CommandUserName = (TextView) convertView
					.findViewById(R.id.CommandUserName);
			vh.Command = (TextView) convertView.findViewById(R.id.Command);
			vh.CommandTime = (TextView) convertView
					.findViewById(R.id.CommandTime);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(
				data.get(position).getUserIcon(), vh.commandUserImg, options);
		vh.CommandUserName.setText(data.get(position).getUName());
		vh.Command.setText(data.get(position).getReview());
		vh.CommandTime.setText(data.get(position).getCreatTime());
		return convertView;
	}

	class ViewHolder {
		private RoundedImageView commandUserImg;
		private TextView CommandUserName;
		private TextView Command;
		private TextView CommandTime;
	}
}
