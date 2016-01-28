package me.shikhov.dancer.moves;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

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
    @interface CancelAction
    {

    }

    @NonNull
    T onCancel(@CancelAction int action);
}
