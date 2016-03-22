package com.shanghai.volunteer.activity.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

public class GsfLayoutAnimation {
	private static final int DURATION = 300;
	/**
	 * 列表动画控制
	 * 
	 * @return
	 */
	public static LayoutAnimationController getLayoutAnimationController() {
		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(DURATION);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(DURATION);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		return controller;
	}
}
