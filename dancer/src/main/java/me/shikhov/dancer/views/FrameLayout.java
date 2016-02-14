package me.shikhov.dancer.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

import me.shikhov.dancer.RevealContainer;
import me.shikhov.dancer.moves.RevealMove;
import me.shikhov.dancer.moves.reveal.CircleClipper;

public class FrameLayout extends android.widget.FrameLayout
    implements RevealContainer
{
    private RevealMove.Clipper clipper;

    private float fraction;
    private Point startPoint;

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

        clipper = new CircleClipper();
        startPoint = new Point(100,100);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        if(oldw == 0 && oldh == 0)
        {
            ObjectAnimator.ofFloat(new Object(){
                public void setFraction(float fraction)
                {
                    FrameLayout.this.fraction = fraction;
                    invalidate();
                }
            }, "fraction", 0, 1).setDuration(2000).start();
        }
    }

    @Override
    public void setClipper(@Nullable RevealMove.Clipper clipper)
    {
        this.clipper = clipper;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime)
    {
        if(clipper == null || Float.compare(fraction, 1f) == 0)
        {
            return super.drawChild(canvas, child, drawingTime);
        }

        int state = canvas.save();

        clipper.clip(canvas, getWidth(), getHeight(), startPoint, fraction);

        boolean result = super.drawChild(canvas, child, drawingTime);

        canvas.restoreToCount(state);

        return result;
    }
}
