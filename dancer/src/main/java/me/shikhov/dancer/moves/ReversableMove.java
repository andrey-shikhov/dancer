package me.shikhov.dancer.moves;

import me.shikhov.dancer.Move;

public interface ReversableMove<T extends ReversableMove> extends Move<T>
{
    /**
     * Move should be done in reverse order.
     * @return this object
     */
    T reverse();
}
