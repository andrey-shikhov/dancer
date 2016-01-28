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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;

import me.shikhov.dancer.future.Cancellable;
import me.shikhov.dancer.future.SimpleCancellable;


@UiThread
public class LayoutWaiter extends SimpleCancellable
        implements ViewTreeObserver.OnGlobalLayoutListener
{
    private final Runnable onLayoutCompleteAction;
    private final Runnable onWaitCanceledAction;

    private final WeakReference<View> anchorRef;

    public LayoutWaiter(@NonNull View anchor, @NonNull Runnable onLayoutAction, @Nullable Runnable cancelAction)
    {
        this.onLayoutCompleteAction = onLayoutAction;
        this.onWaitCanceledAction = cancelAction;
        this.anchorRef = new WeakReference<>(anchor);

        if(ViewCompat.isLaidOut(anchor))
        {
            setComplete();
        }
        else
        {
            anchor.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
    }

    @NonNull
    public static Cancellable waitLayout(@NonNull final View view, @NonNull final Runnable completeAction)
    {
        return waitLayout(view, completeAction, null);
    }

    @NonNull
    public static Cancellable waitLayout(@NonNull final View view, @NonNull Runnable completeAction, @Nullable Runnable cancelAction)
    {
        return new LayoutWaiter(view, completeAction, cancelAction);
    }

    @Override
    public void onGlobalLayout()
    {
        setComplete();
    }

    @Override
    protected void cleanup()
    {
        super.cleanup();

        View view = anchorRef.get();

        if(view != null)
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
    }

    @Override
    protected void completeCleanup()
    {
        onLayoutCompleteAction.run();
    }

    @Override
    protected void cancelCleanup()
    {
        if(onWaitCanceledAction != null)
            onWaitCanceledAction.run();
    }
}
