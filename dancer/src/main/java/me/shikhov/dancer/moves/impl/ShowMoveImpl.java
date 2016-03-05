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

package me.shikhov.dancer.moves.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.lang.ref.WeakReference;

import me.shikhov.dancer.Direction;
import me.shikhov.dancer.Size;
import me.shikhov.dancer.moves.AbstractMove;
import me.shikhov.dancer.moves.ShowMove;
import me.shikhov.wlog.Log;

public class ShowMoveImpl extends AbstractMove<ShowMove> implements ShowMove
{
    private static final String TAG = "dancer.showmove";

    private WeakReference<View> viewRef;

    private ViewPropertyAnimator animator;

    @Direction
    private int sourcePosition = Direction.DOWN;

    private Size distance;

    private float startAlpha;

    private float toAlpha = 1;

    private Interpolator interpolator;

    private boolean reverse;

    public ShowMoveImpl(boolean simulation)
    {
        super(simulation);
        setDuration(300);
    }

    @Override
    @NonNull
    public ShowMoveImpl view(@NonNull View view)
    {
        this.viewRef = new WeakReference<>(view);

        return this;
    }

    @Override
    @NonNull
    public ShowMoveImpl from(@Direction int direction)
    {
        this.sourcePosition = direction;

        return this;
    }

    @Override
    @NonNull
    public ShowMoveImpl fromAlpha(float alpha)
    {
        this.startAlpha = alpha;

        return this;
    }

    @Override
    @NonNull
    public ShowMoveImpl distance(@NonNull Size size)
    {
        this.distance = size;

        return this;
    }

    @Override
    @NonNull
    public ShowMoveImpl duration(long duration)
    {
        setDuration(duration);

        return this;
    }

    @Nullable
    @Override
    protected View getTargetView()
    {
        return viewRef.get();
    }

    @Override
    protected void execute()
    {
        View view = viewRef.get();

        if(view == null)
        {
            future.cancel();
        }
        else
        {
            int distancePixels = distance.getValue(view);

            Log.get(TAG).a("execute ").a(view.getId()).a(" ").a(view.getWidth()).a("x").a(view.getHeight()).r();
            Log.get(TAG).a("sourcePos: ").a(sourcePosition).a(" distance ").a(distancePixels).r();

            ViewHelper.setAlpha(view, startAlpha);

            switch (sourcePosition)
            {
                case Direction.LEFT:
                    ViewHelper.setTranslationX(view, -distancePixels);
                    break;

                case Direction.RIGHT:
                    ViewHelper.setTranslationX(view, distancePixels);
                    break;

                case Direction.UP:
                    ViewHelper.setTranslationY(view, -distancePixels);
                    break;

                case Direction.DOWN:
                    ViewHelper.setTranslationY(view, distancePixels);
                    break;
            }

            animator = ViewPropertyAnimator.animate(view)
                    .setListener(null)
                    .alpha(1)
                    .translationX(0)
                    .translationY(0)
                    .setDuration(getDuration());

            animator.setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationCancel(Animator animation)
                {

                }

                @Override
                public void onAnimationEnd(Animator animation)
                {
                    complete();
                }
            });
        }
    }

    @Override
    public boolean cancel()
    {
        if(animator != null)
        {
            animator.cancel();
        }

        return true;
    }

    @NonNull
    @Override
    public ShowMove toAlpha(float alpha)
    {
        toAlpha = 1;

        return this;
    }

    @Override
    public ShowMove reverse()
    {
        return this;
    }
}
