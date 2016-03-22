package com.shanghai.volunteer.activity;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.adapter.DisplayAdapter;
import com.shanghai.volunteer.adapter.SuishoupaiListAdapter;
import com.shanghai.volunteer.other.parallax.lib.MultiColumnPullToRefreshListView;
import com.shanghai.volunteer.other.parallax.lib.MultiColumnPullToRefreshListView.OnRefreshListener;

public class SuishoupaiListActivity extends BaseActivity {

	private MultiColumnPullToRefreshListView waterfallView;// 可以把它当成一个listView
	// 如果不想用下拉刷新这个特性，只是瀑布流，可以用这个：MultiColumnListView 一样的用法

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suishoupailist);
		// 初始化图片加载库
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc(true)
				// 图片存本地
				.cacheInMemory(true).displayer(new FadeInBitmapDisplayer(50))
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY) // default
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCache(new UsingFreqLimitedMemoryCache(16 * 1024 * 1024))
				.defaultDisplayImageOptions(defaultOptions).build();
		ImageLoader.getInstance().init(config);

		waterfallView = (MultiColumnPullToRefreshListView) findViewById(R.id.suishoupaiWLV);

		ArrayList<String> imageList = new ArrayList<String>();
		imageList
				.add("http://g.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f766b5db45710e0cf3d6cad68b.jpg");
		imageList
				.add("http://h.hiphotos.baidu.com/image/pic/item/0b55b319ebc4b745f462c141ccfc1e178b8215f6.jpg");
		imageList
				.add("http://a.hiphotos.baidu.com/image/pic/item/bba1cd11728b4710f197b4c1c0cec3fdfc032306.jpg");
		imageList
				.add("http://g.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f766b5db45710e0cf3d6cad68b.jpg");
		imageList
				.add("http://h.hiphotos.baidu.com/image/pic/item/1c950a7b02087bf4d5d123c0f1d3572c11dfcfde.jpg");
		imageList
				.add("http://h.hiphotos.baidu.com/image/pic/item/1c950a7b02087bf4d5d123c0f1d3572c11dfcfde.jpg");
		imageList
				.add("http://h.hiphotos.baidu.com/image/pic/item/0b55b319ebc4b745f462c141ccfc1e178b8215f6.jpg");
		imageList
				.add("http://g.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f766b5db45710e0cf3d6cad68b.jpg");
		imageList
				.add("http://h.hiphotos.baidu.com/image/pic/item/0b55b319ebc4b745f462c141ccfc1e178b8215f6.jpg");
		imageList
				.add("http://g.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f766b5db45710e0cf3d6cad68b.jpg");

		SuishoupaiListAdapter adapter = new SuishoupaiListAdapter(imageList, this);
		waterfallView.setAdapter(adapter);
		waterfallView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 下拉刷新要做的事

				// 刷新完成后记得调用这个
				waterfallView.onRefreshComplete();
			}
		});
	}
	
}
