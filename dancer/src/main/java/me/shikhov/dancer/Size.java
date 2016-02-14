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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Structure to set some size: width/height/depth using absolute/relative values
 */
public final class Size
{
    public static final int ABSOLUTE = 0x01;
    public static final int SELF_RELATIVE = 0x02;
    public static final int PARENT_RELATIVE = 0x04;

    /**
     * Possible values are: {@link #ABSOLUTE},{@link #SELF_RELATIVE},{@link #PARENT_RELATIVE}
     */
    @IntDef({ABSOLUTE, SELF_RELATIVE, PARENT_RELATIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type
    {
    }

    public static final int DIM_WIDTH = 0x11;
    public static final int DIM_HEIGHT = 0x12;
    public static final int DIM_DEPTH = 0x14;

    /**
     * Possible values are: {@link #DIM_WIDTH},{@link #DIM_HEIGHT}, {@link #DIM_DEPTH}
     */
    @IntDef({DIM_WIDTH, DIM_HEIGHT, DIM_DEPTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Dimension
    {
    }

    private final int value;

    @Type
    private final int type;

    @Dimension
    private final int dimension;

    /**
     * Predefined relative size: 100% of view width
     * @see #HEIGHT
     * @see #MINUS_WIDTH
     * @see #getValue(View)
     */
    @NonNull
    public static final Size WIDTH = new Size(SELF_RELATIVE, DIM_WIDTH, 100);

    /**
     * Predefined relative size: 100% of view height
     * @see #WIDTH
     * @see #MINUS_HEIGHT
     * @see #getValue(View)
     */
    @NonNull
    public static final Size HEIGHT = new Size(SELF_RELATIVE, DIM_HEIGHT, 100);

    /**
     * Predefined relative size: 100% of parent view width
     * @see #PARENT_HEIGHT
     * @see #MINUS_PARENT_WIDTH
     * @see #getValue(View)
     */
    @NonNull
    public static final Size PARENT_WIDTH = new Size(PARENT_RELATIVE, DIM_WIDTH, 100);

    /**
     * Predefined relative size: 100% of parent view height
     * @see #HEIGHT
     * @see #MINUS_PARENT_HEIGHT
     * @see #getValue(View)
     */
    @NonNull
    public static final Size PARENT_HEIGHT = new Size(PARENT_RELATIVE, DIM_HEIGHT, 100);

    /**
     * Predefined relative size: -100% of view width
     * @see #WIDTH
     * @see #PARENT_WIDTH
     * @see #getValue(View)
     */
    @NonNull
    public static final Size MINUS_WIDTH = new Size(SELF_RELATIVE, DIM_WIDTH, -100);

    /**
     * Predefined relative size: -100% of view height
     * @see #HEIGHT
     * @see #PARENT_HEIGHT
     * @see #getValue(View)
     */
    @NonNull
    public static final Size MINUS_HEIGHT = new Size(SELF_RELATIVE, DIM_HEIGHT, -100);

    /**
     * Predefined relative size: -100% of parent view width
     * @see #PARENT_WIDTH
     * @see #WIDTH
     * @see #getValue(View)
     */
    @NonNull
    public static final Size MINUS_PARENT_WIDTH = new Size(PARENT_RELATIVE, DIM_WIDTH, -100);

    /**
     * Predefined relative size: -100% of parent view height
     * @see #PARENT_HEIGHT
     * @see #HEIGHT
     * @see #getValue(View)
     */
    @NonNull
    public static final Size MINUS_PARENT_HEIGHT = new Size(PARENT_RELATIVE, DIM_HEIGHT, -100);

    /**
     * Constructor for creating size using absolute values
     * @param dimension, see {@link Dimension}
     * @param value size in pixels
     */
    public Size(@Dimension int dimension, int value)
    {
        this(ABSOLUTE, dimension, value);
    }

    /**
     * Constructs object with all fields initialization.
     * @param type {@link Type}
     * @param dimension {@link Dimension}
     * @param value
     */
    public Size(@Type int type, @Dimension int dimension, int value)
    {
        this.value = value;
        this.type = type;
        this.dimension = dimension;
    }

    /**
     * Constructs new size relative to this by multiplication of value attribute <br/>
     * For example: handy use of 50% of width: {@link #WIDTH}.multiply(0.5f)
     * @param coef
     * @return
     */
    @NonNull
    public Size multiply(float coef)
    {
        return new Size(type, dimension, (int) (value*coef));
    }

    /**
     * Returns raw value of size, which is meaningless for relative sizes(use {@link #getValue(View) to get right value}
     * @return size in pixels for absolute values, or percents for relative values
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Returns type of size
     * @see Type
     * @return type of size
     */
    @Type
    public int getType()
    {
        return type;
    }

    /**
     * Returns dimension of size
     * @see Dimension
     * @return dimension type
     */
    @Dimension
    public int getDimension()
    {
        return dimension;
    }

    /**
     * Calculates size according to input view.
     * @param view view which is used for calculating relative sizes
     * @return size in pixels
     */
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
