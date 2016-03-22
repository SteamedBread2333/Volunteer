package com.shanghai.volunteer.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;

public class DefaultActivity extends BaseActivity {
	private ImageView bgImageView;
	private static final int DURATION = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_default);
		bgImageView = (ImageView) findViewById(R.id.default_bg_iv);

		// 动画效果
		AnimatorSet set = new AnimatorSet();
		// 执行动画队列
		set.playTogether(ObjectAnimator.ofFloat(bgImageView, "alpha", 1f, 1f)
				.setDuration(DURATION),
				ObjectAnimator.ofFloat(bgImageView, "scaleX", 1f, 1.0f)
						.setDuration(DURATION),
				ObjectAnimator.ofFloat(bgImageView, "scaleY", 1f, 1.0f)
						.setDuration(DURATION));
		// 动画事件监听
		set.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(DefaultActivity.this,
						MainActivity.class));
				DefaultActivity.this.finish();
			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub

			}
		});
		set.start();
	}
}
