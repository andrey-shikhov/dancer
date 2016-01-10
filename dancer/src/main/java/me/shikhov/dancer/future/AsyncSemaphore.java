
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

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AsyncSemaphore
{
    Semaphore semaphore = new Semaphore(0);

    public void acquire() throws InterruptedException
    {
        ThreadQueue threadQueue = ThreadQueue.getOrCreateThreadQueue(Thread.currentThread());
        AsyncSemaphore last = threadQueue.waiter;
        threadQueue.waiter = this;
        Semaphore queueSemaphore = threadQueue.queueSemaphore;

        try
        {
            if (semaphore.tryAcquire())
                return;

            while (true)
            {
                // run the queue
                while (true)
                {
                    Runnable run = threadQueue.remove();
                    if (run == null)
                        break;
//                        Log.i(LOGTAG, "Pumping for AsyncSemaphore");
                    run.run();
                }

                int permits = Math.max(1, queueSemaphore.availablePermits());

                queueSemaphore.acquire(permits);

                if (semaphore.tryAcquire())
                    break;
            }
        }
        finally
        {
            threadQueue.waiter = last;
        }
    }

    public boolean tryAcquire(long timeout, TimeUnit timeunit) throws InterruptedException
    {
        long timeoutMs = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
        ThreadQueue threadQueue = ThreadQueue.getOrCreateThreadQueue(Thread.currentThread());
        AsyncSemaphore last = threadQueue.waiter;
        threadQueue.waiter = this;
        Semaphore queueSemaphore = threadQueue.queueSemaphore;

        try
        {
            if (semaphore.tryAcquire())
                return true;

            long start = System.currentTimeMillis();

            do
            {
                // run the queue
                while (true)
                {
                    Runnable run = threadQueue.remove();
                    if (run == null)
                        break;
//                        Log.i(LOGTAG, "Pumping for AsyncSemaphore");
                    run.run();
                }

                int permits = Math.max(1, queueSemaphore.availablePermits());

                if (!queueSemaphore.tryAcquire(permits, timeoutMs, TimeUnit.MILLISECONDS))
                    return false;

                if (semaphore.tryAcquire())
                    return true;
            }
            while (System.currentTimeMillis() - start < timeoutMs);

            return false;
        }
        finally
        {
            threadQueue.waiter = last;
        }
    }

    public void release()
    {
        semaphore.release();
        ThreadQueue.release(this);
    }
}