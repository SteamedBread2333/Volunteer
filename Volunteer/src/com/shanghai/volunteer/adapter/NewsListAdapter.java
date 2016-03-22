package com.shanghai.volunteer.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.adapter.QuestionAdapter.ViewHolder;
import com.shanghai.volunteer.bean.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsListAdapter extends BaseAdapter {

	private ArrayList<News> data;
	private Context context;
	private LayoutInflater mInflater;
	private ViewHolder vh = null;


	public NewsListAdapter(Context context) {
		super();
		this.context = context;

	}

	public ArrayList<News> getData() {
		return data;
	}

	public void setData(ArrayList<News> data) {
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
			convertView = mInflater.inflate(R.layout.listview_item_news,
					null);
			vh = new ViewHolder();
			vh.newslistTitle = (TextView) convertView
					.findViewById(R.id.listNewsTitleItemTV);
			vh.newslistDetail = (TextView) convertView
					.findViewById(R.id.listNewsDetailItemTV);
			vh.newslistType = (TextView) convertView
					.findViewById(R.id.listNewsTypeItemTV);
			vh.newslistTime = (TextView) convertView
					.findViewById(R.id.listNewsTimeItemTV);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.newslistTitle.setText(data.get(position).getTitle());
		vh.newslistDetail.setText("");
		vh.newslistType.setText(data.get(position).getAuthor());
		vh.newslistTime.setText(data.get(position).getCreatTime());
		return convertView;
	}

	class ViewHolder {
		private TextView newslistTitle;
		private TextView newslistDetail;
		private TextView newslistType;
		private TextView newslistTime;
	}

}
