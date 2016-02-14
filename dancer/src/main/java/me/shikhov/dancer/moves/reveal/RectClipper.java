/*******************************************************************************
 * Copyright 2016 Andrew Shikhov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package me.shikhov.dancer.moves.reveal;

import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.NonNull;

import me.shikhov.dancer.moves.RevealMove;


public class RectClipper implements RevealMove.Clipper
{
    @Override
    public void clip(@NonNull Canvas canvas, int containerWidth, int containerHeight,
                     @NonNull Point startPoint, float fraction)
    {
        int w = (int) (containerWidth * fraction);
        int h = (int) (containerHeight * fraction);

        int center_x = containerWidth/2;
        int center_y = containerHeight/2;

        int x;
        int y;

        if(startPoint.x <= center_x)
        {
            x = startPoint.x - (int) (startPoint.x*fraction);
        }
        else
        {
            x = startPoint.x + (int) ((containerWidth - startPoint.x)*fraction) - w;
        }

        if(startPoint.y <= center_y)
        {
            y = startPoint.y - (int)(startPoint.y*fraction);
        }
        else
        {
            y = startPoint.y + (int)((containerHeight - startPoint.y)*fraction) - h;
        }

        canvas.clipRect(x, y, x + w, y + h);
    }
}
