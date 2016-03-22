package com.shanghai.volunteer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanghai.volunteer.R;
import com.shanghai.volunteer.bean.Active;

public class HomeEsayAdapter extends BaseAdapter {

	private ArrayList<Active> data;
	ViewHolder vh = null;
	private LayoutInflater mInflater;
	private Context context;

	
	
	public HomeEsayAdapter(ArrayList<Active> data, Context context) {
		super();
		this.data = data;
		this.context = context;
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
			convertView = mInflater.inflate(R.layout.listview_item_homeeasy,
					null);
			vh = new ViewHolder();
			vh.NewsTitle = (TextView) convertView.findViewById(R.id.NewsTitle);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.NewsTitle.setText(data.get(position).getTitle());
		return convertView;
	}

	class ViewHolder {
		private TextView NewsTitle;
	}

}
