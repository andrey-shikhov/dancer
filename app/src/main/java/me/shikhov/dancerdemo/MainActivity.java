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
import android.support.v7.app.AppCompatActivity;

import me.shikhov.wlog.Log;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "dancer.activity";

    {
        System.setProperty("wlog.logLevel", "info");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.get(TAG).event(this, "onCreate").r();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Log.get(TAG).event(this, "onResume").r();

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.frags_root, new SplashFragment(),"SPLASH").commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frags_root, new ShowHideMoveFragment(),"SHOWHIDE").commit();
    }

    @Override
    protected void onPause()
    {
        Log.get(TAG).event(this, "onPause").r();
        super.onPause();
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
