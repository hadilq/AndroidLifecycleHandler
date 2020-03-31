package com.github.hadilq.androidlifecyclehandler

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import com.github.hadilq.androidlifecyclehandler.LifeSpan.USER_FLOW

/**
 * A store to keep life object with [USER_FLOW] [LifeSpan] in memory to use by an entire user flow. Assuming the
 * user flow includes multiple Activities.
 */
object LifeStore {

    private val impl = LifeStoreImpl()

    /**
     * Provides a life object with [USER_FLOW] [LifeSpan], which means its lifecycle can be expanded over multiple
     * activities of a user flow. To **keep** this life object in the memory until the next Activity in the user flow
     * grab it by calling this method, one must call the [keep] method before onDestroy of the current Activity.
     * Assuming the user flow includes multiple Activities.
     *
     * To access to the the life object, all activities in the flow must have the [key], which is a unique [String].
     * Also all activities must be able to create this life object by implementing the [factory]. However, the [owner]
     * is each of the activities in the user flow.
     */
    @MainThread
    fun <T : Life> provideLife(
        key: String,
        owner: LifecycleOwner,
        factory: LifeFactory<T>
    ): T = impl.provideLife(key, owner, factory)

    /**
     * **Keeps** the life in memory for the next Activity to grab it by calling [provideLife] method. To do so, one
     * must call this method before onDestroy of the current Activity in the user flow. Assuming the user flow
     * includes multiple Activities.
     *
     * To keep the life object, all activities must have the [key] and call this method exactly before launching the
     * next Activity in the user flow, that would be a good rule to guaranty to call it before onDestroy.
     */
    @MainThread
    fun keep(key: String) = impl.keep(key)
}

internal class LifeStoreImpl {

    private val store = mutableMapOf<String, LifeWrapper>()

    @MainThread
    @Suppress("UNCHECKED_CAST")
    fun <T : Life> provideLife(
        key: String,
        owner: LifecycleOwner,
        factory: LifeFactory<T>,
        handler: AndroidLifeHandler = AndroidLifeHandlerImpl()
    ): T = store[key]?.run {
        keep = false
        handler.register(owner, this, USER_FLOW)
        life as? T
    } ?: let {
        val life = factory.get()
        val wrapper = LifeWrapper(key, life, store)
        store[key] = wrapper
        handler.register(owner, wrapper, USER_FLOW)
        life
    }

    @MainThread
    fun keep(key: String) = store[key]?.run { keep = true }

    fun get(key: String) = store[key]
}

internal class LifeWrapper(
    private val key: String,
    val life: Life,
    private val store: MutableMap<String, LifeWrapper>,
    var keep: Boolean = false
) : Life {
    private var isAlive = false

    override fun onBorn() {
        if (!isAlive) {
            isAlive = true
            life.onBorn()
        }
    }

    override fun onDie() {
        if (isAlive && !keep) {
            isAlive = false
            life.onDie()
            store.remove(key)
        }
    }
}
