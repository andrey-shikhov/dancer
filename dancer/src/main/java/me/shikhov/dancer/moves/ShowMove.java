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

    @NonNull
    ShowMove fromAlpha(float alpha);

    @NonNull
    ShowMove toAlpha(float alpha);

    @NonNull
    ShowMove distance(@NonNull Size size);

    @NonNull
    ShowMove duration(long duration);
}
