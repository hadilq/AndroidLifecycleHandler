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
package com.github.hadilq.androidlifecyclehandler

import androidx.fragment.app.FragmentActivity

/**
 * Defines how long the life would be alive.
 */
enum class LifeSpan {

    /**
     * This [LifeSpan] is just for [Life] objects who want to expand their life over multiple activities of a
     * user flow, which means onDestroy would not make them die. Check out [LifeStore.provideLife] function for
     * more details. Assuming the user flow includes multiple Activities.
     */
    USER_FLOW,

    /**
     * This [LifeSpan] is just for [Life] objects who want to expand their life over their owner activity, which
     * means configuration changes would not make them die. Check out [FragmentActivity.provideLife] extension
     * function for more details.
     */
    CONFIGURATION_CHANGED,

    /**
     * In case of object being [Life], it's alive after onCreate and before onDestroy of LifecycleOwner.
     * In case of object being [ELife], it's alive after onCreate and before onDestroy or onSaveInstanceState
     * of SavedStateRegistryOwner, depends which one is called sooner.
     */
    CREATED,

    /**
     * In case of object being [Life], it's alive after onStart and before onStop or onDestroy of LifecycleOwner,
     * depends which one is called sooner.
     * In case of object being [ELife], it's alive after onStart and before onStop or onDestroy or
     * onSaveInstanceState of SavedStateRegistryOwner, depends which one is called sooner.
     */
    STARTED,

    /**
     * In case of object being [Life], it's alive after onResume and before onPause or onStop or onDestroy of
     * LifecycleOwner, depends which one is called sooner.
     * In case of object being [ELife], it's alive after onResume and before onPause or onStop or onDestroy or
     * onSaveInstanceState of SavedStateRegistryOwner, depends which one is called sooner
     */
    RESUMED
}
