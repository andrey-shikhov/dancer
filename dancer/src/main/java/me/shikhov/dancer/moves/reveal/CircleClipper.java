package me.shikhov.dancer.moves.reveal;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;

import me.shikhov.dancer.moves.RevealMove;

/**
 * Created by andrew on 14.02.16.
 */
public class CircleClipper implements RevealMove.Clipper
{
    private final Path path = new Path();

    @Override
    public void clip(@NonNull Canvas canvas, int containerWidth, int containerHeight, @NonNull Point startPoint, float fraction)
    {
        int rad1 = (int) Math.sqrt(startPoint.x*startPoint.x + startPoint.y*startPoint.y);
        int rad2 = (int) Math.sqrt(Math.pow(containerWidth - startPoint.x, 2) + Math.pow(containerHeight - startPoint.y, 2));

        int r = (int) (Math.max(rad1, rad2)*fraction);

        path.reset();
        path.addCircle(startPoint.x, startPoint.y, r, Path.Direction.CW);
        path.close();

        canvas.clipPath(path);
    }
}
