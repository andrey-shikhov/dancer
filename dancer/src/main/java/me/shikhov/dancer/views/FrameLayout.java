package me.shikhov.dancer.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import me.shikhov.dancer.RevealContainer;
import me.shikhov.dancer.moves.RevealMove;

public class FrameLayout extends android.widget.FrameLayout
    implements RevealContainer
{
    private RevealMove.Clipper clipper;

    public FrameLayout(Context context)
    {
        this(context, null);
    }

    public FrameLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public FrameLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void setClipper(@Nullable RevealMove.Clipper clipper)
    {
        this.clipper = clipper;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime)
    {
        if(clipper == null)
        {
            return super.drawChild(canvas, child, drawingTime);
        }

        return super.drawChild(canvas, child, drawingTime);
    }
}
