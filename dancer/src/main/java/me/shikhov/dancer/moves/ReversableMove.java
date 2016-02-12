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
