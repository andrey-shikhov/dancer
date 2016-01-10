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

import me.shikhov.dancer.LayoutWaiter;
import me.shikhov.dancer.future.Future;
import me.shikhov.dancer.future.FutureCallback;
import me.shikhov.dancer.future.SimpleFuture;
import me.shikhov.wlog.Log;

public abstract class Move
{
    private static final String TAG = "dancer.move";

    protected final Move parentMove;

    protected final boolean simulation;

    protected SimpleFuture<Move> future;

    private long duration;

    public Move(boolean simulation)
    {
        parentMove = null;
        this.simulation = simulation;
    }

    public Move(@NonNull Move parentMove, boolean simulation)
    {
        this.parentMove = parentMove;
        this.simulation = simulation;
    }

    public final long getDuration()
    {
        return duration;
    }

    protected final void setDuration(long duration)
    {
        this.duration = duration;
    }

    @NonNull
    public final Future<Move> dance()
    {
        Log.get(TAG).a("dance: ").a(getClass().getSimpleName()).r();

        future = createFuture();

        boolean execute = true;

        if(parentMove == null)
        {
            View targetView = getTargetView();

            if(targetView != null && !ViewCompat.isLaidOut(targetView))
            {
                Log.get(TAG).a("layout is not performed, waiting for layout").r();

                execute = false;

                final SimpleFuture<Move> layoutFuture = new SimpleFuture<>();

                future.setParent(layoutFuture);

                layoutFuture.setCallback(new FutureCallback<Move>()
                {
                    @Override
                    public void onCompleted(Exception e, Move result)
                    {
                        Log.get(TAG).a("onCompleted, layout, e=").a(e).a(" object ").a(result).t(new Throwable()).r();

                        if(e == null)
                        {
                            result.execute();
                        }
                    }
                });

                LayoutWaiter.waitLayout(targetView, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Log.get(TAG).a("layoutComplete").r();
                        layoutFuture.setComplete(Move.this);
                    }
                });
            }
            else
            {
                Log.get(TAG).a("targetView ").a(targetView).r();
            }
        }

        if(execute)
        {
            Log.get(TAG).a("execute without layout waiting.").r();
            execute();
        }

        return future;
    }

    @NonNull
    protected SimpleFuture<Move> createFuture()
    {
        return new SimpleFuture<>();
    }

    protected final void complete()
    {
        future.setComplete(this);
        future = null;
    }

    @Nullable
    protected View getTargetView()
    {
        return null;
    }

    protected abstract void execute();
}
