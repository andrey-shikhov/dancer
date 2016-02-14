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

import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.NonNull;

import me.shikhov.dancer.RevealContainer;
import me.shikhov.dancer.moves.reveal.RectClipper;

public interface RevealMove extends CancellableMove<RevealMove>, ReversableMove<RevealMove>
{
    interface Clipper
    {
        void clip(@NonNull Canvas canvas, int containerWidth, int containerHeight, @NonNull Point startPoint, float fraction);
    }

    @NonNull
    Clipper DEFAULT_CLIPPER = new RectClipper();

    @NonNull
    RevealMove container(@NonNull RevealContainer container);

    RevealMove clipper(@NonNull Clipper clipper);
}
