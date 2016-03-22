package com.shanghai.volunteer.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
public class MenuItemView extends ViewGroup{
	public static final int POSITION_LEFT_TOP = 1;
	public static final int POSITION_RIGHT_TOP = 2;
	public static final int POSITION_LEFT_BOTTOM = 3;
	public static final int POSITION_RIGHT_BOTTOM = 4;
	public  final static int STATUS_CLOSE = 5;
	public  final static int STATUS_OPEN = 6;
	
	private int flagX;
	private int flagY;
	private float radius;
	private int status;
	
	private int positon;
	
	private Context context;
	public MenuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MenuItemView(Context context) {
		super(context);
	}
	
	private void init(Context context) {
		this.context = context;
		this.status = STATUS_CLOSE;
		
	}
	
	public void setPosition(int position){
		this.positon = position;
		flagX = position == POSITION_LEFT_TOP || position == POSITION_LEFT_BOTTOM ? -1 : 1;
		flagY = position == POSITION_LEFT_TOP || position == POSITION_RIGHT_TOP ? -1 : 1;
	}
	

	
	@Override
	     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	         for (int index = 0; index < getChildCount(); index++) {
	             final View child = getChildAt(index);
	             // measure
	            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
	        }
	
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    }

	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(positon == 0)
			throw new RuntimeException("PositonUnknow!Use method setPosition to set the position first!");
		if(radius == 0)
			throw new RuntimeException("RadiusUnknow!Use method setRadiusByDP to set the radius first!");
		
		if(changed){
			int count = getChildCount();
			int dx = -flagX * (int) MyAnimations.dip2px(context, 20);
			int dy = -flagY * (int) MyAnimations.dip2px(context, 0);
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			childView.setVisibility(View.GONE);
			int width = childView.getMeasuredWidth();
			int height = childView.getMeasuredHeight();
			//the position of childview leftTop
			int x = (int) (MyAnimations.dip2px(context, radius) *  Math.sin(MyAnimations.PI / 2 / (float)(count - 1) * i));
			int y = (int) (MyAnimations.dip2px(context, radius) *  Math.cos(MyAnimations.PI / 2 / (float)(count - 1) * i));
			x = positon == POSITION_RIGHT_BOTTOM || positon == POSITION_RIGHT_TOP ? (getMeasuredWidth() - x -width) : x;
			y = positon == POSITION_LEFT_BOTTOM || positon == POSITION_RIGHT_BOTTOM ? (getMeasuredHeight() - y - height) : y;
			childView.layout(x + dx , y + dy, x + width+ dx, y + height + dy );
				}
		}
	}
	
	public int getFlagX() {
		return flagX;
	}

	public void setFlagX(int flagX) {
		this.flagX = flagX;
	}

	public int getFlagY() {
		return flagY;
	}

	public void setFlagY(int flagY) {
		this.flagY = flagY;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
