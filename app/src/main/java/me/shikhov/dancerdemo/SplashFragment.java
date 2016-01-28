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

package me.shikhov.dancerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import me.shikhov.dancer.Dancer;
import me.shikhov.dancer.Direction;
import me.shikhov.dancer.Size;

/**
 * Created by andrew on 08.01.16.
 */
public class SplashFragment extends Fragment
{

    private ImageView logo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_splash, container, false);

        logo = Util.get(view, R.id.ani_logo);

        View caption = Util.get(view, R.id.splash_caption);
        View subCaption = Util.get(view, R.id.splash_subcaption);

        Dancer.show()
                .view(caption)
                .from(Direction.UP)
                .distance(Size.HEIGHT)
                .dance().setCallback((e, result) -> result.reverse().dance());

        Dancer.show().view(subCaption).from(Direction.RIGHT).distance(Size.WIDTH).dance();

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Ion.with(getActivity()).load("android.resource://me.shikhov.dancerdemo/" + R.raw.dancer128).intoImageView(logo);
    }
}
