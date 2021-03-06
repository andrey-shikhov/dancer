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

import me.shikhov.dancer.Move;

/**
 * Interface identifies that move can be reversed.<br/>
 * If animation is running it will be reversed immediately from current position.<br/>
 * If animation is not running reverse animation will be played from end to start point.
 * @param <T>
 */
public interface ReversableMove<T extends ReversableMove> extends Move<T>
{
    /**
     * Move should be done in reverse order.
     * @return this object
     */
    T reverse();
}
