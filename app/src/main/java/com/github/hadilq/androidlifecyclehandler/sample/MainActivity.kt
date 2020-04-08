/**
 * Copyright 2020 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.hadilq.androidlifecyclehandler.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.github.hadilq.androidlifecyclehandler.AndroidELifeHandlerImpl
import com.github.hadilq.androidlifecyclehandler.ELife
import com.github.hadilq.androidlifecyclehandler.LifeSpan

class MainActivity : ComponentActivity() {

    private val creature = object : ELife {

        override fun onBorn(bundle: Bundle?) {
            // Start being alive
        }

        override fun onDie(): Bundle = Bundle() // Dead
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        AndroidELifeHandlerImpl().register(this, creature, LifeSpan.CREATED, KEY)
    }

    companion object {
        private const val KEY = "KEY_TO_SAVE_THE_BUNDLE"
    }
}
