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

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import me.shikhov.dancer.Move;

public interface CancellableMove<T extends CancellableMove> extends Move<T>
{
    /**
     * Animator after cancellation will do nothing with views it's animate.
     */
    int DO_NOTHING = 1;

    /**
     * Animator after cancellation will set target view to state like animation is fully played.
     */
    int MOVE_TO_DESTINATION = 2;

    /**
     * Animator after cancellation will set target view to state like there were no animation played.
     */
    int MOVE_TO_ORIGIN = 4;


    @IntDef({DO_NOTHING,MOVE_TO_DESTINATION, MOVE_TO_ORIGIN})
    @Retention(RetentionPolicy.SOURCE)
    @interface CancelAction
    {

    }

    @NonNull
    T onCancel(@CancelAction int action);
}
