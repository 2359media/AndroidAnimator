package com.androidanimator.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class ScaleOutAnimation extends Animation {
	
	long duration;
	AnimationListener listener;
	
	public ScaleOutAnimation(View view) {
		this.view = view;
		duration = Animation.DEFAULT_DURATION;
		listener = null;
	}

	@Override
	public void animate() {
		view.setScaleX(1f);
		view.setScaleY(1f);
		view.animate().scaleX(0f).scaleY(0f).setDuration(duration).setListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(ScaleOutAnimation.this);
				}
			}
		});
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public ScaleOutAnimation setDuration(long duration) {
		this.duration = duration;
		return this;
	}

	/**
	 * @return the listener
	 */
	public AnimationListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public ScaleOutAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
