package com.androidanimator.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * The PulsateAnimation1 causes the view to blink a number of times to mimic a
 * pulsating animation.
 * 
 * @author SiYao
 * 
 */
public class PulsateAnimation1 extends Animation {

	int repetitions, pulsateCount = 0;

	public PulsateAnimation1() {
		repetitions = 2;
		duration = Constant.DEFAULT_DURATION;
	}

	public PulsateAnimation1(int repetitions, long duration, AnimationListener listener) {
		this.repetitions = repetitions;
		this.duration = duration;
		this.listener = listener;
	}

	@Override
	public void animate(final View view) {
		long singlePulsateDuration = duration / repetitions;
		if (singlePulsateDuration == 0)
			singlePulsateDuration = 1;
		ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, View.ALPHA, 0), fadeIn = ObjectAnimator
				.ofFloat(view, View.ALPHA, 1);
		final AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playSequentially(fadeOut, fadeIn);
		animatorSet.setDuration(singlePulsateDuration);
		animatorSet.start();
		animatorSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				pulsateCount++;
				if (pulsateCount != repetitions) {
					animation.start();
					if (pulsateCount == repetitions - 1) {
						animation.addListener(new AnimatorListenerAdapter() {

							@Override
							public void onAnimationEnd(Animator animation) {
								if (getListener() != null) {
									getListener().onAnimationEnd(PulsateAnimation1.this);
								}
							}
						});
					}
				}
			}
		});
	}

}
