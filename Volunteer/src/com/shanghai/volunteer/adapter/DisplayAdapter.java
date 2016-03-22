package com.shanghai.volunteer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.DetailDisplayActivity;
import com.shanghai.volunteer.bean.Display;

public class DisplayAdapter extends BaseAdapter {

	ArrayList<Display> list;
	Context context;
	private Drawable drawable;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	Intent intent;

	public ArrayList<Display> getList() {
		return list;
	}

	public void setList(ArrayList<Display> list) {
		this.list = list;
	}

	public DisplayAdapter(ArrayList<Display> list, Context context) {
		this.list = list;
		this.context = context;
		drawable = context.getResources().getDrawable(R.drawable.load_default);
	}

	public DisplayAdapter(Context context) {
		super();
		this.context = context;
		options = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
				.showImageOnLoading(R.drawable.white).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build();
	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup group) {
		final Holder holder;
		// 得到View
		if (view == null) {
			holder = new Holder();
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.listview_item_displaylist, null);
			holder.ivIcon = (ImageView) view.findViewById(R.id.row_icon);
			holder.pbLoad = (ProgressBar) view.findViewById(R.id.pb_load);
			holder.nice = (TextView) view.findViewById(R.id.nice);
			holder.command = (TextView) view.findViewById(R.id.command);
			//holder.bad = (TextView) view.findViewById(R.id.bad);
			holder.intro = (TextView) view.findViewById(R.id.intro);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		holder.command.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Toast.makeText(context, "command for position->" + position,
						2000).show();*/
			}
		});
		holder.nice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Toast.makeText(context, "nice for position->" + position, 2000)
						.show();*/
			}
		});
		/*
		 * holder.bad.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub
		 * 
		 * Toast.makeText(context, "bad for position->" + position, 2000)
		 * .show();
		 * 
		 * } });
		 */
		holder.ivIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				intent = new Intent(context, DetailDisplayActivity.class);
				intent.putExtra("mId", list.get(position).getID() + "");
				context.startActivity(intent);
			}
		});
		holder.nice.setText(list.get(position).getPraiseNum() + "");
		//holder.bad.setText(list.get(position).getCriticismNum() + "");
		holder.command.setText(list.get(position).getReviewCount() + "");
		holder.intro.setText(list.get(position).getDetails());
		ImageLoader.getInstance().displayImage(
				list.get(position).getImageUrl(), holder.ivIcon, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {

						// 这儿初先初始化出来image所占的位置的大小，先把瀑布流固定住，这样瀑布流就不会因为图片加载出来后大小变化了
						// LayoutParams lp = (LayoutParams)
						// holder.ivIcon.getLayoutParams();
						// 多屏幕适配
						// int dWidth = 480;
						// int dHeight = 800;
						// float wscale = dWidth / 480.0f;
						// float hscale = dHeight / 800.0f;
						// lp.height = (int) (yourImageHeight * hscale);
						// lp.width = (int) (yourImageWidth * wscale);
						// holder.ivIcon.setLayoutParams(lp);

						holder.ivIcon.setImageDrawable(drawable);
						holder.pbLoad.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "can not be decoding";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "内存不足";
							Toast.makeText(context, message, Toast.LENGTH_SHORT)
									.show();
							break;
						case UNKNOWN:
							message = "Unknown error";
							Toast.makeText(context, message, Toast.LENGTH_SHORT)
									.show();
							break;
						}
						holder.pbLoad.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						holder.pbLoad.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingCancelled(String paramString,
							View paramView) {
					}
				});

		return view;
	}

}

class Holder {
	public ImageView ivIcon;
	public ProgressBar pbLoad;
	public TextView nice;
	//public TextView bad;
	public TextView command;
	public TextView intro;
}
