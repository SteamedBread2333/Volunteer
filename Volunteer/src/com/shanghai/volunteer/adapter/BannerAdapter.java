package com.shanghai.volunteer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.DetailNewsActivity;
import com.shanghai.volunteer.bean.News;

public class BannerAdapter extends PagerAdapter {

	private ArrayList<View> items = null;
	private ArrayList<News> banners = null;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	private Context mContext;

	public BannerAdapter(Context context) {
		options = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
				// .showImageOnLoading(R.drawable.icon)
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build();

		mContext = context;
	}

	public ArrayList<News> getBanners() {
		return banners;
	}

	public void setBanners(ArrayList<News> banners) {
		this.banners = banners;
		items = new ArrayList<View>();
		
		for (int i = 0; i < banners.size(); i++) {
			View view  = LayoutInflater.from(mContext).inflate(
					R.layout.banner_pager_item, null);
			items.add(view);
			view.setTag(banners.get(i).getID());
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext, DetailNewsActivity.class);
					intent.putExtra("newsId", (String)arg0.getTag());
					mContext.startActivity(intent);
				}
			});
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(items.get(position));
		ImageLoader.getInstance()
				.displayImage(banners.get(position).getImg(),
						(ImageView)items.get(position).findViewById(R.id.banner_image_iv),
						options);
		return items.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(items.get(position));
	}

}
