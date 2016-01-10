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

import java.util.ArrayList;
import java.util.List;

public class ChainedMove extends Move
{
    class MoveChain
    {
        long duration;

        @NonNull
        private final List<Move> moves = new ArrayList<>();
        
    }


    private final List<MoveChain> chains = new ArrayList<>();

    public ChainedMove(boolean simulation)
    {
        super(simulation);
    }

    public ChainedMove(@NonNull Move parentMove, boolean simulation)
    {
        super(parentMove, simulation);
    }

    @NonNull
    public ChainedMove with(Move... moves)
    {
        return this;
    }

    @Override
    protected void execute()
    {

    }
}
