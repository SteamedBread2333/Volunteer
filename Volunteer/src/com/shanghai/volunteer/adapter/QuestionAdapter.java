package com.shanghai.volunteer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanghai.volunteer.R;
import com.shanghai.volunteer.bean.Question;

public class QuestionAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ViewHolder vh = null;
	private Context context;
	private ArrayList<Question> data = null;

	public ArrayList<Question> getData() {
		return data;
	}

	public void setData(ArrayList<Question> data) {
		this.data = data;
	}

	public QuestionAdapter(Context context) {
		super();
		this.context = context;
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
		return arg0;
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
			convertView = mInflater.inflate(R.layout.listview_item_question,
					null);
			vh = new ViewHolder();
			vh.q = (TextView) convertView.findViewById(R.id.q);
			vh.a = (TextView) convertView.findViewById(R.id.a);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.q.setText(data.get(position).getQuestion());
		vh.a.setText(data.get(position).getAnswer());
		return convertView;

	}

	class ViewHolder {
		TextView q;
		TextView a;
	}
}
