/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

import com.ppandroid.readenglish.R;


public class Utils_Animator {


    /**
     * 关闭动画
     * @param v
     */
    public static ValueAnimator openAnim(final View v) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0, v.getHeight());
        return animator;

    }

    /**
     * 展开动画
     * @param view
     */

    public static void closeAnimate(final View view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        /**防止在动画执行期间再次点击*/
        view.setClickable(false);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setClickable(true);
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }



    public static ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }

        });
        return animator;
    }







   public static void scale(View target, Long duration, float... values){
        ObjectAnimator animatorX= ObjectAnimator.ofFloat(target, "scaleX", values);
        ObjectAnimator animatorY= ObjectAnimator.ofFloat(target, "scaleY", values);
        AnimatorSet set=new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.start();
    }

    /**Shake Horizontal */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static ObjectAnimator nope(View view) {
        int delta = view.getResources().getDimensionPixelOffset(R.dimen.spacing_medium);
        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(.10f, -delta),
                Keyframe.ofFloat(.26f, delta),
                Keyframe.ofFloat(.42f, -delta),
                Keyframe.ofFloat(.58f, delta),
                Keyframe.ofFloat(.74f, -delta),
                Keyframe.ofFloat(.90f, delta),
                Keyframe.ofFloat(1f, 0f)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX).
                setDuration(1000);
    }



}
