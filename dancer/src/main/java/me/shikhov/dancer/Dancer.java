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

package me.shikhov.dancer;

import android.support.annotation.NonNull;

import me.shikhov.dancer.moves.HeroMove;
import me.shikhov.dancer.moves.LadderMove;
import me.shikhov.dancer.moves.RevealMove;
import me.shikhov.dancer.moves.ShowMove;

public final class Dancer
{
    private static boolean simulateAnimations;

    public static void simulate()
    {
        simulateAnimations = true;
    }

    @NonNull
    public static HeroMove hero()
    {
        return new HeroMove(simulateAnimations);
    }

    @NonNull
    public static LadderMove ladder()
    {
        return new LadderMove();
    }

    @NonNull
    public static RevealMove reveal()
    {
        return new RevealMove();
    }

    @NonNull
    public static ShowMove show()
    {
        return new ShowMove(simulateAnimations);
    }
}