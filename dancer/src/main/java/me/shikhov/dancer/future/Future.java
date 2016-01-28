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

public interface Future<T> extends Cancellable, java.util.concurrent.Future<T>
{
    /**
     * Set a callback to be invoked when this Future completes.
     * @param callback
     * @return this future
     */
    Future<T> setCallback(FutureCallback<T> callback);

    /**
     * Set a callback to be invoked when this Future completes.
     * @param callback
     * @param <C>
     * @return The callback
     */
    <C extends FutureCallback<T>> C then(C callback);

    /**
     * Get the result, if any. Returns null if still in progress.
     * @return result, if any
     */
    T tryGet();

    /**
     * Get the exception, if any. Returns null if still in progress.
     * @return exception, if any.
     */
    Exception tryGetException();
}
