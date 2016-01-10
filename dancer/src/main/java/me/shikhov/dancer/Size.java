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

package me.shikhov.dancer;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.View;

public final class Size
{
    public static final int ABSOLUTE = 0x01;
    public static final int SELF_RELATIVE = 0x02;
    public static final int PARENT_RELATIVE = 0x04;

    @IntDef({ABSOLUTE, SELF_RELATIVE, PARENT_RELATIVE})
    public @interface Type
    {
    }

    public static final int DIM_WIDTH = 0x11;
    public static final int DIM_HEIGHT = 0x12;
    public static final int DIM_DEPTH = 0x14;

    @IntDef({DIM_WIDTH, DIM_HEIGHT, DIM_DEPTH})
    public @interface Dimension
    {
    }

    private final int value;

    @Type
    private final int type;

    @Dimension
    private final int dimension;

    @NonNull
    public static final Size WIDTH = new Size(SELF_RELATIVE, DIM_WIDTH, 100);

    @NonNull
    public static final Size HEIGHT = new Size(SELF_RELATIVE, DIM_HEIGHT, 100);

    @NonNull
    public static final Size PARENT_WIDTH = new Size(PARENT_RELATIVE, DIM_WIDTH, 100);

    @NonNull
    public static final Size PARENT_HEIGHT = new Size(PARENT_RELATIVE, DIM_HEIGHT, 100);

    @NonNull
    public static final Size MINUS_WIDTH = new Size(SELF_RELATIVE, DIM_WIDTH, -100);

    @NonNull
    public static final Size MINUS_HEIGHT = new Size(SELF_RELATIVE, DIM_HEIGHT, -100);

    @NonNull
    public static final Size MINUS_PARENT_WIDTH = new Size(PARENT_RELATIVE, DIM_WIDTH, -100);

    @NonNull
    public static final Size MINUS_PARENT_HEIGHT = new Size(PARENT_RELATIVE, DIM_HEIGHT, -100);

    public Size(@Dimension int dimension, int value)
    {
        this(ABSOLUTE, dimension, value);
    }

    public Size(@Type int type, @Dimension int dimension, int value)
    {
        this.value = value;
        this.type = type;
        this.dimension = dimension;
    }

    public int getValue()
    {
        return value;
    }

    @Type
    public int getType()
    {
        return type;
    }

    @Dimension
    public int getDimension()
    {
        return dimension;
    }

    public int getValue(@NonNull View view)
    {
        switch (type)
        {
            case ABSOLUTE:
                return value;

            case SELF_RELATIVE:

                switch (dimension)
                {
                    case DIM_WIDTH:
                        return view.getWidth()*value/100;

                    case DIM_HEIGHT:
                        return view.getHeight()*value/100;

                    case DIM_DEPTH:
                        return value;
                }

                break;

            case PARENT_RELATIVE:
                View parent = (View) view.getParent();

                if(parent == null)
                    parent = view;

                switch (dimension)
                {
                    case DIM_WIDTH:
                        return parent.getWidth()*value/100;

                    case DIM_HEIGHT:
                        return parent.getHeight()*value/100;

                    case DIM_DEPTH:
                        return value;
                }

                break;
        }

        throw new IllegalStateException("!!!");
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        if(value < 0)
            sb.append("-");

        switch (dimension)
        {
            case DIM_WIDTH:
                sb.append("w");
                break;

            case DIM_HEIGHT:
                sb.append("h");
                break;

            case DIM_DEPTH:
                sb.append("d");
                break;
        }

        sb.append(Math.abs(value));

        if(type != ABSOLUTE)
            sb.append("%");

        if(type == PARENT_RELATIVE)
            sb.append("p");

        return sb.toString();
    }
}
