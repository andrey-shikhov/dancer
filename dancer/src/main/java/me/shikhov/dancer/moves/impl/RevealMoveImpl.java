package me.shikhov.dancer.moves.impl;

import android.support.annotation.NonNull;

import me.shikhov.dancer.RevealContainer;
import me.shikhov.dancer.moves.AbstractMove;
import me.shikhov.dancer.moves.RevealMove;

/**
 * Created by andrew on 12.02.16.
 */
public class RevealMoveImpl extends AbstractMove<RevealMove>
        implements RevealMove
{
    public RevealMoveImpl(boolean simulation)
    {
        super(simulation);
    }

    @Override
    protected void execute()
    {

    }

    @NonNull
    @Override
    public RevealMove container(@NonNull RevealContainer container)
    {
        return this;
    }

    @Override
    public RevealMove clipper(@NonNull Clipper clipper)
    {
        return this;
    }

    @Override
    public RevealMove reverse()
    {
        return this;
    }
}
