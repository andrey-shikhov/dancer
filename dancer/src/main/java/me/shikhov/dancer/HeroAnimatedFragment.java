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

package me.shikhov.dancer;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public abstract class HeroAnimatedFragment extends Fragment
{
    private View heroAnimationView;

    @Nullable
    @Override
    @CallSuper
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(getLayoutId(), container, false);

        onCreateView(view);

        heroAnimationView = view.findViewById(getHeroViewId());

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else
                {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        return view;
    }

    @NonNull
    public View getHeroAnimationView()
    {
        return heroAnimationView;
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract @IdRes int getHeroViewId();

    protected void onCreateView(@NonNull View rootView)
    {
        // do some stuff
    }
}
