package com.shanghai.volunteer.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.adapter.QuestionAdapter.ViewHolder;
import com.shanghai.volunteer.bean.News;
import com.shanghai.volunteer.bean.OnlineAdvice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OnlineAdviceListAdapter extends BaseAdapter {

	private ArrayList<OnlineAdvice> data;
	private Context context;
	private LayoutInflater mInflater;
	private ViewHolder vh = null;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类

	public OnlineAdviceListAdapter(Context context) {
		super();
		this.context = context;
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
				.showImageOnLoading(R.drawable.white).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build();
	}

	public ArrayList<OnlineAdvice> getData() {
		return data;
	}

	public void setData(ArrayList<OnlineAdvice> data) {
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
			convertView = mInflater.inflate(R.layout.list_online_advice,
					null);
			vh = new ViewHolder();
			vh.adviceUserImageView = (ImageView) convertView.findViewById(R.id.adviceMyImageIV);
			vh.adviceAdminImageView = (ImageView) convertView.findViewById(R.id.adviceAdminImageIV);
			vh.adviceQTextView = (TextView) convertView.findViewById(R.id.adviceQTextView);
			vh.adviceFTextView = (TextView) convertView.findViewById(R.id.adviceFTextView);
			vh.adviceRelpayLayout = (ViewGroup) convertView.findViewById(R.id.adviceRelpayLayout);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(data.get(position).getuUrl(),
				vh.adviceUserImageView, options);
		ImageLoader.getInstance().displayImage(data.get(position).getAdviceUrl(),
				vh.adviceAdminImageView, options);
		vh.adviceQTextView.setText(data.get(position).getQ());
		vh.adviceFTextView.setText(data.get(position).getF());
		if(data.get(position).getMark() == 0){
			vh.adviceRelpayLayout.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		private ImageView adviceUserImageView;
		private ImageView adviceAdminImageView;
		private ViewGroup adviceRelpayLayout;
		private TextView adviceQTextView;
		private TextView adviceFTextView;
	}

}
