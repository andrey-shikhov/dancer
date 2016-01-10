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

import java.util.LinkedList;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;

public class ThreadQueue extends LinkedList<Runnable>
{
    final private static WeakHashMap<Thread, ThreadQueue> mThreadQueues = new WeakHashMap<>();

    static ThreadQueue getOrCreateThreadQueue(@NonNull Thread thread)
    {
        ThreadQueue queue;
        synchronized (mThreadQueues)
        {
            queue = mThreadQueues.get(thread);
            if (queue == null)
            {
                queue = new ThreadQueue();
                mThreadQueues.put(thread, queue);
            }
        }

        return queue;
    }

    static void release(AsyncSemaphore semaphore)
    {
        synchronized (mThreadQueues)
        {
            for (ThreadQueue threadQueue : mThreadQueues.values())
            {
                if (threadQueue.waiter == semaphore)
                    threadQueue.queueSemaphore.release();
            }
        }
    }

    AsyncSemaphore waiter;
    final Semaphore queueSemaphore = new Semaphore(0);

    @Override
    public boolean add(@NonNull Runnable object)
    {
        synchronized (this)
        {
            return super.add(object);
        }
    }

    @Override
    public boolean remove(Object object)
    {
        synchronized (this)
        {
            return super.remove(object);
        }
    }

    @Override
    public Runnable remove()
    {
        synchronized (this)
        {
            if (this.isEmpty())
                return null;

            return super.remove();
        }
    }
}