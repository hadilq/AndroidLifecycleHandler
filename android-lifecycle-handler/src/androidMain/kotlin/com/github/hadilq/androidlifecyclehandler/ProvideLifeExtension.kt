package com.github.hadilq.androidlifecyclehandler

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.hadilq.androidlifecyclehandler.LifeSpan.CONFIGURATION_CHANGED

/**
 * Provides a life object with [CONFIGURATION_CHANGED] [LifeSpan], which means its lifecycle is the same as an
 * ordinary [ViewModel]. There are two advantage of using a [Life] over a [ViewModel]. One is that it unify the
 * concept of [Life]. Second one is that this method can provide a [SLife] object where is suitable to observe
 * other lives and sync them.
 *
 * To provide it [ComponentActivity] must implement the [factory] of that life object.
 */
@MainThread
@Suppress("UNCHECKED_CAST")
fun <T : Life> ComponentActivity.provideLife(
    factory: LifeFactory<T>
): T = ViewModelProvider(this, object : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <V : ViewModel?> create(modelClass: Class<V>): V = MainLifeViewModel(this@provideLife, factory) as V
}).get(MainLifeViewModel::class.java).life as T

/**
 * Try to call [Life.onBorn] after onCreate.
 */
internal class MainLifeViewModel(owner: LifecycleOwner, factory: LifeFactory<Life>) : ViewModel() {
    val life: Life = factory.get()

    init {
        AndroidLifeHandlerImpl().register(owner, life, CONFIGURATION_CHANGED)
    }

    override fun onCleared() {
        life.onDie()
    }
}
