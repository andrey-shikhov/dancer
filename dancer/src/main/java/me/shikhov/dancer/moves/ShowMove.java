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
import android.view.View;

import me.shikhov.dancer.Direction;
import me.shikhov.dancer.Size;

public interface ShowMove extends CancellableMove<ShowMove>, ReversableMove<ShowMove>
{
    @NonNull
    ShowMove view(@NonNull View view);

    @NonNull
    ShowMove from(@Direction.Dir int direction);

    /**
     * Set
     * @param alpha
     * @return
     */
    @NonNull
    ShowMove fromAlpha(float alpha);

    @NonNull
    ShowMove toAlpha(float alpha);

    @NonNull
    ShowMove distance(@NonNull Size size);

    @NonNull
    ShowMove duration(long duration);
}
