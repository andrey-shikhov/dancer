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

public abstract class TransformFuture<T, F> extends SimpleFuture<T> implements FutureCallback<F>
{
    @Override
    public void onCompleted(Exception e, F result)
    {
        if (isCancelled())
            return;

        if (e != null)
        {
            error(e);
            return;
        }

        try
        {
            transform(result);
        }
        catch (Exception ex)
        {
            error(ex);
        }
    }

    protected void error(Exception e)
    {
        setComplete(e);
    }

    protected abstract void transform(F result) throws Exception;
}
