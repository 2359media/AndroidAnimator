package com.example.androidanimationdemo;

import java.util.ArrayList;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.androidanimationdemo.model.DemoItem;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.BlindAnimation;
import com.easyandroidanimations.library.BlinkAnimation;
import com.easyandroidanimations.library.BounceAnimation;
import com.easyandroidanimations.library.ExplodeAnimation;
import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.FadeOutAnimation;
import com.easyandroidanimations.library.FlipHorizontalAnimation;
import com.easyandroidanimations.library.FlipHorizontalToAnimation;
import com.easyandroidanimations.library.FlipVerticalAnimation;
import com.easyandroidanimations.library.FlipVerticalToAnimation;
import com.easyandroidanimations.library.UnfoldAnimation;
import com.easyandroidanimations.library.FoldLayout.Orientation;
import com.easyandroidanimations.library.FoldAnimation;
import com.easyandroidanimations.library.HighlightAnimation;
import com.easyandroidanimations.library.ParallelAnimator;
import com.easyandroidanimations.library.ParallelAnimatorListener;
import com.easyandroidanimations.library.PathAnimation;
import com.easyandroidanimations.library.PuffInAnimation;
import com.easyandroidanimations.library.PuffOutAnimation;
import com.easyandroidanimations.library.RotationAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;
import com.easyandroidanimations.library.ScaleOutAnimation;
import com.easyandroidanimations.library.ShakeAnimation;
import com.easyandroidanimations.library.SlideInAnimation;
import com.easyandroidanimations.library.SlideInUnderneathAnimation;
import com.easyandroidanimations.library.SlideOutAnimation;
import com.easyandroidanimations.library.SlideOutUnderneathAnimation;
import com.easyandroidanimations.library.TransferAnimation;

public class AnimationDetailFragment extends Fragment implements OnClickListener {

    public static final String ARG_ITEM_ID = "item_id";

    private DemoItem.DemoAnimation mItem;
    private View mPlayView, target;
    private ImageView card, card2;
    private FrameLayout frameLayout;
    private TextView tvCode;

    private boolean isFinished = false;

    public AnimationDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DemoItem.ITEM_MAP.get(getArguments().getInt(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_animation_detail,
                container, false);
        mPlayView = rootView.findViewById(R.id.imgPlay);
        frameLayout = (FrameLayout) rootView.findViewById(R.id.fragment_animation_frame);
        card = (ImageView) rootView.findViewById(R.id.imgTarget);
        mPlayView.setOnClickListener(this);
        card2 = (ImageView) rootView.findViewById(R.id.imgBehind);
        target = rootView.findViewById(R.id.textView1);
        card2.setVisibility(View.INVISIBLE);
        tvCode = (TextView) rootView.findViewById(R.id.tv_code);
        if (mItem.code != null && mItem.code.length() > 0) {
            tvCode.setVisibility(View.VISIBLE);
            tvCode.setText(mItem.code);
        } else {
            tvCode.setVisibility(View.INVISIBLE);
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isFinished) {
                    mPlayView.setVisibility(View.VISIBLE);
                    card.setVisibility(View.VISIBLE);
                    card2.setVisibility(View.INVISIBLE);
                    setUpById(mItem.id);
                    isFinished = false;
                }
            }
        });
        setUpById(mItem.id);
        return rootView;
    }

    public void setUpById(int id) {
        // Differentiates the images for all animations
        if (id <= 5) {
            card.setImageResource(R.drawable.img1);
        } else if (id > 5 && id <= 10) {
            card.setImageResource(R.drawable.img2);
        } else if (id > 10 && id <= 20) {
            card.setImageResource(R.drawable.img3);
        } else {
            card.setImageResource(R.drawable.img4);
        }
        mPlayView.setLayoutParams(card.getLayoutParams());

        // Shows the second image to be covered for FoldIn
        if (id == 11) {
            card2.setVisibility(View.VISIBLE);
        }

        // Scales the image smaller for PathAnimation
        if (id == 14) {
            card.setImageResource(R.drawable.yellow_star);
            card.setScaleX(0.5f);
            card.setScaleY(0.5f);
        }
        // Sets view to <code>View.INVISIBLE</code> for entrance animations
        if (id == 5 || id == 11 || id == 15 || id == 18
                || id == 21 || id == 22 || id == 26) {
            card.setVisibility(View.INVISIBLE);
        }
        // Sets destination view to <code>View.VISIBLE</code> only for
        // TransferAnimation
        if (id != 25) {
            target.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(final View v) {
        // Sets play button to <code>View.INVISIBLE</code> before starting
        // animation
        mPlayView.setVisibility(View.INVISIBLE);
        doAnimation();
    }

    private void doAnimation() {
        switch (mItem.id) {
            case 1:
                new BlindAnimation(card).animate();
                isFinished = true;
                break;
            case 2:
                new BlinkAnimation(card).setListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mPlayView.setVisibility(View.VISIBLE);
                    }
                }).animate();
                break;
            case 3:
                new BounceAnimation(card).setNumOfBounces(3)
                        .setDuration(Animation.DURATION_SHORT)
                        .setListener(new AnimationListener() {

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mPlayView.setVisibility(View.VISIBLE);
                            }
                        }).animate();
                break;
            case 4:
                new ExplodeAnimation(card).animate();
                isFinished = true;
                break;
            case 5:
                new FadeInAnimation(card).animate();
                isFinished = true;
                break;
            case 6:
                new FadeOutAnimation(card).animate();
                isFinished = true;
                break;
            case 7:
                new FlipHorizontalAnimation(card).setListener(
                        new AnimationListener() {

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mPlayView.setVisibility(View.VISIBLE);
                            }
                        }).animate();
                break;
            case 8:
                new FlipHorizontalToAnimation(card).setFlipToView(card2)
                        .setInterpolator(new LinearInterpolator()).animate();
                isFinished = true;
                break;
            case 9:
                new FlipVerticalAnimation(card).setListener(
                        new AnimationListener() {

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mPlayView.setVisibility(View.VISIBLE);
                            }
                        }).animate();
                break;
            case 10:
                new FlipVerticalToAnimation(card).setFlipToView(card2)
                        .setInterpolator(new LinearInterpolator()).animate();
                isFinished = true;
                break;
            case 11:
                card2.setVisibility(View.VISIBLE);
                new UnfoldAnimation(card).setNumOfFolds(10).setDuration(1000)
                        .setOrientation(Orientation.HORIZONTAL).animate();
                isFinished = true;
                break;
            case 12:
                card2.setVisibility(View.VISIBLE);
                new FoldAnimation(card).setNumOfFolds(10).setDuration(1000)
                        .setOrientation(Orientation.VERTICAL).animate();
                isFinished = true;
                break;
            case 13:
                new HighlightAnimation(card).setListener(new AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mPlayView.setVisibility(View.VISIBLE);
                    }
                }).animate();
                break;
            case 14:
                ArrayList<Point> points = new ArrayList<>();
                points.add(new Point(0, 100));
                points.add(new Point(50, 0));
                points.add(new Point(100, 100));
                points.add(new Point(0, 50));
                points.add(new Point(100, 50));
                points.add(new Point(0, 100));
                points.add(new Point(50, 50));
                new PathAnimation(card).setPoints(points).setDuration(2000)
                        .setListener(new AnimationListener() {

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mPlayView.setVisibility(View.VISIBLE);
                            }
                        }).animate();
                break;

            case 15:
                new PuffInAnimation(card).animate();
                isFinished = true;
                break;
            case 16:
                new PuffOutAnimation(card).animate();
                isFinished = true;
                break;
            case 17:
                new RotationAnimation(card)
                        .setPivot(RotationAnimation.PIVOT_TOP_LEFT)
                        .setListener(new AnimationListener() {

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mPlayView.setVisibility(View.VISIBLE);
                            }
                        }).animate();
                break;

            case 18:
                new ScaleInAnimation(card).animate();
                isFinished = true;
                break;
            case 19:
                new ScaleOutAnimation(card).animate();
                isFinished = true;
                break;
            case 20:
                new ShakeAnimation(card).setNumOfShakes(3)
                        .setDuration(Animation.DURATION_SHORT)
                        .setListener(new AnimationListener() {

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mPlayView.setVisibility(View.VISIBLE);
                            }
                        }).animate();
                break;

            case 21:
                new SlideInAnimation(card).setDirection(Animation.DIRECTION_UP)
                        .animate();
                isFinished = true;
                break;
            case 22:
                new SlideInUnderneathAnimation(card).setDirection(
                        Animation.DIRECTION_DOWN).animate();
                isFinished = true;
                break;
            case 23:
                new SlideOutAnimation(card).setDirection(Animation.DIRECTION_LEFT)
                        .animate();
                isFinished = true;
                break;
            case 24:
                new SlideOutUnderneathAnimation(card).setDirection(
                        Animation.DIRECTION_RIGHT).animate();
                isFinished = true;
                break;
            case 25:
                new TransferAnimation(card).setDestinationView(target).animate();
                break;
            case 26:
                card.setImageResource(R.drawable.img1);

                final AnimationListener explodeAnimListener = new AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        card.setVisibility(View.INVISIBLE);
                        mPlayView.setVisibility(View.VISIBLE);
                    }
                };

                final AnimationListener bounceAnimListener = new AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        new ExplodeAnimation(card2)
                                .setListener(explodeAnimListener).animate();
                    }
                };

                final ParallelAnimatorListener slideFadeInAnimListener = new ParallelAnimatorListener() {

                    @Override
                    public void onAnimationEnd(ParallelAnimator parallelAnimator) {
                        BounceAnimation bounceAnim = new BounceAnimation(card2);
                        bounceAnim.setNumOfBounces(10);
                        bounceAnim.setListener(bounceAnimListener);
                        bounceAnim.animate();
                    }
                };

                final ParallelAnimatorListener slideFadeOutAnimListener = new ParallelAnimatorListener() {

                    @Override
                    public void onAnimationEnd(ParallelAnimator parallelAnimator) {
                        ParallelAnimator slideFadeInAnim = new ParallelAnimator();
                        slideFadeInAnim.add(new SlideInAnimation(card2)
                                .setDirection(Animation.DIRECTION_RIGHT));
                        slideFadeInAnim.add(new FadeInAnimation(card2));
                        slideFadeInAnim.setDuration(1000);
                        slideFadeInAnim.setListener(slideFadeInAnimListener);
                        slideFadeInAnim.animate();
                    }
                };

                final ParallelAnimatorListener rotatePathAnimListener = new ParallelAnimatorListener() {

                    @Override
                    public void onAnimationEnd(ParallelAnimator parallelAnimator) {
                        ParallelAnimator slideFadeOutAnim = new ParallelAnimator();
                        slideFadeOutAnim.add(new SlideOutAnimation(card)
                                .setDirection(Animation.DIRECTION_RIGHT));
                        slideFadeOutAnim.add(new FadeOutAnimation(card));
                        slideFadeOutAnim.setInterpolator(new LinearInterpolator());
                        slideFadeOutAnim.setDuration(1000);
                        slideFadeOutAnim.setListener(slideFadeOutAnimListener);
                        slideFadeOutAnim.animate();
                    }
                };

                final ParallelAnimatorListener scaleFlipAnimListener = new ParallelAnimatorListener() {

                    @Override
                    public void onAnimationEnd(ParallelAnimator parallelAnimator) {
                        ArrayList<Point> parallelPoints = new ArrayList<>();
                        parallelPoints.add(new Point(50, 0));
                        parallelPoints.add(new Point(100, 50));
                        parallelPoints.add(new Point(50, 100));
                        parallelPoints.add(new Point(0, 50));
                        parallelPoints.add(new Point(50, 50));
                        ParallelAnimator rotatePathAnim = new ParallelAnimator();
                        rotatePathAnim.add(new PathAnimation(card)
                                .setPoints(parallelPoints));
                        rotatePathAnim.add(new RotationAnimation(card));
                        rotatePathAnim.setInterpolator(new LinearInterpolator());
                        rotatePathAnim.setDuration(2000);
                        rotatePathAnim.setListener(rotatePathAnimListener);
                        rotatePathAnim.animate();
                    }
                };

                ParallelAnimator scaleFlipAnim = new ParallelAnimator();
                scaleFlipAnim.add(new ScaleInAnimation(card));
                scaleFlipAnim.add(new FlipHorizontalAnimation(card));
                scaleFlipAnim.add(new FlipVerticalAnimation(card));
                scaleFlipAnim.setDuration(2000);
                scaleFlipAnim.setListener(scaleFlipAnimListener);
                scaleFlipAnim.animate();
                break;
            default:
                break;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup vg = (ViewGroup) view;
        // vg.setClipChildren(false);
        vg.setClipToPadding(false);
    }
}
