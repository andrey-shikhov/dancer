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

import android.os.Handler;
import android.os.Looper;

/**
 * Callback will be called from UIThread
 * @param <T>
 */
public class HandlerFuture<T> extends SimpleFuture<T>
{
    Handler handler;

    public HandlerFuture()
    {
        Looper looper = Looper.myLooper();

        if (looper == null)
            looper = Looper.getMainLooper();

        handler = new Handler(looper);
    }

    @Override
    public SimpleFuture<T> setCallback(final FutureCallback<T> callback)
    {
        FutureCallback<T> wrapped = new FutureCallback<T>()
        {
            @Override
            public void onCompleted(final Exception e, final T result)
            {
                if (Looper.myLooper() == handler.getLooper())
                {
                    callback.onCompleted(e, result);
                    return;
                }

                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        onCompleted(e, result);
                    }
                });
            }
        };

        return super.setCallback(wrapped);
    }
}