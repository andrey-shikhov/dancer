/*******************************************************************************
 * Copyright 2016 Andrew Shikhov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package me.shikhov.dancer.moves;

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.lang.ref.WeakReference;

public class HeroMove extends AbstractMove<HeroMove>
{
    private WeakReference<View> fromViewRef;
    private WeakReference<View> toViewRef;

    private boolean proportionalScale;

    private final int[] fromLocation = new int[2];

    private final int[] toLocation = new int[2];

    public HeroMove(boolean simulation)
    {
        super(simulation);
    }

    public HeroMove(@NonNull AbstractMove parentMove, boolean simulation)
    {
        super(parentMove, simulation);
        setDuration(500);
    }

    @NonNull
    public HeroMove from(@NonNull View fromView)
    {
        this.fromViewRef = new WeakReference<>(fromView);
        setDuration(500);

        return this;
    }

    @NonNull
    public HeroMove to(@NonNull View toView)
    {
        this.toViewRef = new WeakReference<>(toView);

        return this;
    }

    @NonNull
    public HeroMove duration(long duration)
    {
        setDuration(duration);

        return this;
    }

    @NonNull
    public HeroMove proportional()
    {
        this.proportionalScale = true;

        return this;
    }

    private void checkState()
    {
        if(fromViewRef == null || fromViewRef.get() == null)
            throw new IllegalStateException("fromView is null");

        if(toViewRef == null || toViewRef.get() == null)
            throw new IllegalStateException("toView is null");
    }

    @Override
    protected void execute()
    {
        checkState();

        View fromView = fromViewRef.get();
        View toView = toViewRef.get();

        fromView.getLocationInWindow(fromLocation);
        toView.getLocationInWindow(toLocation);

        float scaleX, scaleY;
        int translationX, translationY;

        int fromViewWidth = fromView.getWidth();
        int toViewWidth = toView.getWidth();
        int fromViewHeight = fromView.getHeight();
        int toViewHeight = toView.getHeight();

        if(proportionalScale)
        {
            if(fromViewHeight > fromViewWidth)
            {
                float scale = toViewHeight *1f/ fromViewHeight;
                scaleX = scale;
                scaleY = scale;

                int dx = (int) ((toViewWidth - fromViewWidth *scale)/2);

                translationX = toLocation[0] - (int) (fromLocation[0] + fromViewWidth *(1-scale)/2) + dx;
                translationY = toLocation[1] - (int) (fromLocation[1] + fromViewHeight *(1-scale)/2);
            }
            else
            {
                float scale = toViewWidth *1f/ fromViewWidth;
                scaleX = scale;
                scaleY = scale;

                int dy = (int) ((toViewHeight - fromViewHeight *scale)/2);
                translationX = toLocation[0] - (int) (fromLocation[0] + fromViewWidth *(1-scale)/2);
                translationY = toLocation[1] - (int) (fromLocation[1] + fromViewHeight *(1-scale)/2) + dy;
            }
        }
        else
        {
            scaleX = toViewWidth *1f/ fromViewWidth;
            scaleY = toViewHeight *1f/ fromViewHeight;
            translationX = toLocation[0] - (int) (fromLocation[0] + fromViewWidth *(1-scaleX)/2);
            translationY = toLocation[1] - (int) (fromLocation[1] + fromViewHeight *(1-scaleY)/2);
        }

        if(simulation)
        {
            processAnimationEnd();
            return;
        }

        ViewPropertyAnimator.animate(fromView)
                .setListener(null)
                .translationX(translationX)
                .translationY(translationY)
                .scaleX(scaleX).scaleY(scaleY)
                .setDuration(getDuration())
                .setListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        processAnimationEnd();
                    }
                });
    }

    private void processAnimationEnd()
    {
        View fromView = fromViewRef.get();
        View toView = toViewRef.get();

        ViewHelper.setTranslationX(fromView, 0);
        ViewHelper.setTranslationY(fromView, 0);
        ViewHelper.setScaleX(fromView, 1);
        ViewHelper.setScaleY(fromView, 1);

        /**
         * Hack, android 2.x shows view even after setVisibility(GONE) set, but with 0 alpha
         * we simulate invisibility
         */
        if(Build.VERSION.SDK_INT < 16)
        {
            ViewHelper.setAlpha(fromView, 0);
        }

        fromView.setVisibility(View.GONE);
        toView.setVisibility(View.VISIBLE);

        complete();
    }

}
