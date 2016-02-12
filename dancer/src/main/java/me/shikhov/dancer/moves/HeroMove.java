package me.shikhov.dancer.moves;

import android.support.annotation.NonNull;
import android.view.View;

public interface HeroMove extends CancellableMove<HeroMove>, ReversableMove<HeroMove>
{
    @NonNull
    HeroMove from(@NonNull View view);

    @NonNull
    HeroMove to(@NonNull View view);

    @NonNull
    HeroMove duration(long duration);

    @NonNull
    HeroMove proportional();
}
