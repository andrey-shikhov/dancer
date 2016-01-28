package me.shikhov.dancer;

import android.support.annotation.NonNull;
import android.view.animation.Interpolator;

import me.shikhov.dancer.future.Future;


public interface Move<T extends Move>
{
    @NonNull T interpolator(@NonNull Interpolator interpolator);

    @NonNull Future<T> dance();
}
