package me.shikhov.dancerdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shikhov.dancer.Dancer;
import me.shikhov.dancer.Direction;
import me.shikhov.dancer.Size;

/**
 * Created by andrew on 05.03.16.
 */
public class ShowHideMoveFragment extends Fragment
{
    @Bind(R.id.showhide_item)
    View animatedObject;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_showhide, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.showhide_show)
    void onShowClick(View view)
    {
        Dancer.show().view(animatedObject)
                .from(getDirection())
                .fromAlpha(0)
                .toAlpha(1)
                .distance(getDistance())
                .dance();
    }

    @OnClick(R.id.showhide_hide)
    void onHideClick(View view)
    {

    }

    private @Direction int getDirection()
    {
        return Direction.LEFT;
    }

    @NonNull
    private Size getDistance()
    {
        return Size.PARENT_WIDTH;
    }
}
