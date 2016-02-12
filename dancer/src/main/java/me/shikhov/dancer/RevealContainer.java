package me.shikhov.dancer;

import android.support.annotation.Nullable;

import me.shikhov.dancer.moves.RevealMove;

public interface RevealContainer
{
    void setClipper(@Nullable RevealMove.Clipper clipper);


}
