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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Interpolator;

import me.shikhov.dancer.LayoutWaiter;
import me.shikhov.dancer.future.Future;
import me.shikhov.dancer.future.SimpleCancellable;
import me.shikhov.dancer.future.SimpleFuture;
import me.shikhov.wlog.Log;

public abstract class AbstractMove<T extends CancellableMove> extends SimpleCancellable
    implements CancellableMove<T>
{
    private static final String TAG = "dancer.move";

    protected final boolean simulation;

    protected SimpleFuture<T> future;

    private long duration;

    @CancellableMove.CancelAction
    private int cancelAction = CancellableMove.DO_NOTHING;

    protected Interpolator interpolator;

    public AbstractMove(boolean simulation)
    {
        this.simulation = simulation;
    }

    /**
     * Method used for detecting situation when View is not in layout. If it is - any
     * animation calculation depending on view bounds is meaningless and requires to wait until view will be
     * laid out. Base class will schedule waiting operation and start actual animation only after layout operation
     * complete.
     * @see #checkViewBounds()
     * @see #execute()
     * @return view which will be animated or its parent, or null if there is no view required for animation
     */
    @Nullable
    protected View getTargetView()
    {
        return null;
    }

    /**
     * Gets animation duration.<br/>
     * Note: this value can be changed if this Move compound animation -
     * it is updated after every new animation schedule. For some animations this value can be set explicitly.
     * @return duration in ms
     */
    public final long getDuration()
    {
        return duration;
    }

    protected final void setDuration(long duration)
    {
        this.duration = duration;
    }

    /**
     * Sets animator behavior when animation flow was interrupted.
     * @param action {@link #DO_NOTHING}, {@link #MOVE_TO_ORIGIN}, {@link #MOVE_TO_DESTINATION}
     * @return this object
     */
    @NonNull
    public T onCancel(@CancelAction int action)
    {
        this.cancelAction = action;

        return getThis();
    }

    @NonNull
    @Override
    public T interpolator(@NonNull Interpolator interpolator)
    {
        this.interpolator = interpolator;

        return getThis();
    }

    /**
     * Schedules animation. Return object
     * @see
     * @return
     */
    @NonNull
    public Future<T> dance()
    {
        Log.get(TAG).a("dance: ").a(getClass().getSimpleName()).r();

        future = createFuture();

        boolean execute = true;

//        if(parentMove == null)
//        {
//            SimpleFuture<T> layoutFuture = checkViewBounds();
//
//            if(layoutFuture != null)
//            {
//                future.setParent(layoutFuture);
//                execute = false;
//            }
//        }

        if(execute)
        {
            Log.get(TAG).a("execute without layout waiting.").r();
            execute();
        }

        return future;
    }

    @Nullable
    private SimpleFuture<T> checkViewBounds()
    {
        View targetView = getTargetView();

        if(targetView != null && !ViewCompat.isLaidOut(targetView))
        {
            final SimpleFuture<T> layoutFuture = new SimpleFuture<>();

            LayoutWaiter.waitLayout(targetView, new Runnable()
            {
                @Override
                public void run()
                {
                    layoutFuture.setComplete(getThis());
                }
            });

            return layoutFuture;
        }

        return null;
    }

    @NonNull
    protected SimpleFuture<T> createFuture()
    {
        return new SimpleFuture<>();
    }

    protected final void complete()
    {
        future.setComplete(getThis());
        future = null;
    }

    protected abstract void execute();

    @SuppressWarnings("unchecked")
    protected T getThis()
    {
        return (T) this;
    }
}
