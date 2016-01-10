package me.shikhov.dancerdemo;

import android.support.annotation.IdRes;
import android.view.View;

/**
 *
 * Created by andrew on 08.01.16.
 */
public class Util
{
    @SuppressWarnings("unchecked")
    public static <E extends View> E get(View root,@IdRes int id)
    {
        return (E) root.findViewById(id);
    }
}
