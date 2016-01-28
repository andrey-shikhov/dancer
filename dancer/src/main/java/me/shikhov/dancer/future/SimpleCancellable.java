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

package me.shikhov.dancer.future;

import android.support.annotation.NonNull;

public class SimpleCancellable implements DependentCancellable
{
    private boolean cancelled;

    private Cancellable parent;

    private boolean complete;

    @Override
    public final boolean isDone()
    {
        return complete;
    }

    protected void cancelCleanup()
    {

    }

    protected void cleanup()
    {
    }

    protected void completeCleanup()
    {
    }

    public boolean setComplete()
    {
        synchronized (this)
        {
            if (cancelled)
                return false;

            if (complete)
            {
                // don't allow a Cancellable to complete twice...
                return true;
            }

            complete = true;
            parent = null;
        }

        completeCleanup();
        cleanup();

        return true;
    }

    @Override
    public boolean cancel()
    {
        Cancellable parent;

        synchronized (this)
        {
            if (complete)
                return false;

            if (cancelled)
                return true;

            cancelled = true;
            parent = this.parent;

            // null out the parent to allow garbage collection
            this.parent = null;
        }

        if (parent != null)
            parent.cancel();

        cancelCleanup();
        cleanup();

        return true;
    }

    @Override
    public SimpleCancellable setParent(Cancellable parent)
    {
        synchronized (this)
        {
            if (!isDone())
                this.parent = parent;
        }
        return this;
    }

    @Override
    public boolean isCancelled()
    {
        synchronized (this)
        {
            return cancelled || (parent != null && parent.isCancelled());
        }
    }

    @NonNull
    public Cancellable reset()
    {
        cancel();
        complete = false;
        cancelled = false;
        return this;
    }
}