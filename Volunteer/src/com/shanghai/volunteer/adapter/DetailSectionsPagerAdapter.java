package com.shanghai.volunteer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class DetailSectionsPagerAdapter extends FragmentStatePagerAdapter {
	
	public static final int ARG_SECTION_COUNT = 2;
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}

	public DetailSectionsPagerAdapter(FragmentManager context) {
		super(context);
	}


	@Override
	public int getCount() {
		return ARG_SECTION_COUNT;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
