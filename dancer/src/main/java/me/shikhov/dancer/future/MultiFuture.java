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

import java.util.ArrayList;
import java.util.List;

public class MultiFuture<T> extends SimpleFuture<T>
{
    private List<FutureCallback<T>> callbacks;

    private final FutureCallback<T> callback = new FutureCallback<T>()
    {
        @Override
        public void onCompleted(Exception e, T result)
        {

            List<FutureCallback<T>> callbacks;

            synchronized (MultiFuture.this)
            {
                callbacks = MultiFuture.this.callbacks;
                MultiFuture.this.callbacks = null;
            }

            if (callbacks == null)
                return;

            for (FutureCallback<T> cb : callbacks)
            {
                cb.onCompleted(e, result);
            }
        }
    };

    @Override
    @NonNull
    public MultiFuture<T> setCallback(@NonNull FutureCallback<T> callback)
    {
        synchronized (this)
        {
            if (callbacks == null)
                callbacks = new ArrayList<>();

            callbacks.add(callback);
        }

        // so, there is a race condition where this internal callback could get
        // executed twice, if two callbacks are added at the same time.
        // however, it doesn't matter, as the actual retrieval and nulling
        // of the callback list is done in another sync block.
        // one of the invocations will actually invoke all the callbacks,
        // while the other will not get a list back.

        // race:
        // 1-ADD
        // 2-ADD
        // 1-INVOKE LIST
        // 2-INVOKE NULL
        super.setCallback(this.callback);

        return this;
    }
}
