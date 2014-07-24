package com.androidanimator.animation;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;

/**
 * The PathAnimation translates the view according to the ArrayList of
 * Points provided by the user. The values of x and y in each Point must be
 * in the range of 0-100.
 * 
 * @author SiYao
 *
 */
public class PathAnimation extends Animation {
	
	public static final int ANCHOR_CENTER = 0,
			ANCHOR_TOP_LEFT = 1,
			ANCHOR_TOP_RIGHT = 2,
			ANCHOR_BOTTOM_LEFT = 3,
			ANCHOR_BOTTOM_RIGHT = 4;
	
	ArrayList<Point> points;
	int anchorPosition;
	
	public PathAnimation(ArrayList<Point> points, long duration, int anchorPosition, AnimationListener listener) {
		this.points = points;
		this.duration = duration;
		this.anchorPosition = anchorPosition;
		this.listener = listener;
	}

	@Override
	public void animate(View view) {
		ViewGroup parentView = (ViewGroup) view.getParent(), rootView = (ViewGroup) view.getRootView();
		
		int viewHeight = view.getHeight();
		int viewWidth = view.getWidth();
		
		while (!parentView.equals(rootView)) {
			parentView.setClipChildren(false);
			parentView = (ViewGroup) parentView.getParent();
		}
		rootView.setClipChildren(false);
		
		AnimatorSet allPathsAnim = new AnimatorSet(), pathAnim = new AnimatorSet();
		int numOfPoints = points.size();
		AnimatorSet[] pathAnimSetArray = new AnimatorSet[numOfPoints];
		List<Animator> pathAnimList = new ArrayList<Animator>();
		
		Activity activity = (Activity) view.getContext();
		Rect rect = new Rect();
		Window window = activity.getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rect);
		int windowWidth = window.getDecorView().getWidth();
		int windowHeight = window.getDecorView().getHeight();
		int windowTop = window.getDecorView().getTop();

		float posX, posY;
		int[] locationView = new int[2];
		view.getLocationOnScreen(locationView);
		for (int i = 0; i < numOfPoints; i++) {
			posX = (points.get(i).x / 100f * windowWidth);
			posY = (points.get(i).y / 100f * windowHeight) - locationView[1] + windowTop;
			
			switch (anchorPosition) {
			case ANCHOR_CENTER:
				posX = posX - viewWidth/2;
				posY = posY - viewHeight/2;
				break;
			case ANCHOR_TOP_RIGHT:
				posX -= viewWidth;
				break;
			case ANCHOR_BOTTOM_LEFT:
				posY -= viewHeight;
				break;
			case ANCHOR_BOTTOM_RIGHT:
				posX = posX - viewWidth;
				posY = posY - viewHeight;
				break;
			default:
				break;
			}
			pathAnimSetArray[i] = new AnimatorSet();
			pathAnimSetArray[i].playTogether(ObjectAnimator.ofFloat(view, View.X, posX), ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, posY));
			pathAnimSetArray[i].setInterpolator(new LinearInterpolator());
			pathAnimList.add(pathAnimSetArray[i]);
		}
		
		allPathsAnim.playSequentially(pathAnimList);
		pathAnim.play(allPathsAnim);
		allPathsAnim.setDuration(duration);
		allPathsAnim.start();
		allPathsAnim.addListener(new AnimatorListenerAdapter() {
			
			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(PathAnimation.this);
				}
			}
		});
	}

}
