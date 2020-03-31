package com.github.hadilq.androidlifecyclehandler

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import org.junit.Test

class LifeStoreImplTest {

    @Test
    fun `provideLife must set the keep to false and register the life`() {
        val store = LifeStoreImpl()
        val owner = TestLifecycleOwner()
        val handler: AndroidLifeHandler = mock()
        val life: Life = mock()
        val key = "key"

        store.provideLife(key, owner, FakFactory(life), handler)

        assert(store.get(key)?.keep == false)
        verify(handler).register(eq(owner), any(), eq(LifeSpan.USER_FLOW))
    }

    @Test
    fun `provideLife for the next activity must set the keep to false and register the life`() {
        val store = LifeStoreImpl()
        val firstOwner = TestLifecycleOwner()
        val secondOwner = TestLifecycleOwner()
        val handler: AndroidLifeHandler = mock()
        val life: Life = mock()
        val key = "key"

        store.provideLife(key, firstOwner, FakFactory(life), handler)
        store.provideLife(key, secondOwner, FakFactory(life), handler)


        assert(store.get(key)?.keep == false)
        verify(handler).register(eq(firstOwner), any(), eq(LifeSpan.USER_FLOW))
        verify(handler).register(eq(secondOwner), any(), eq(LifeSpan.USER_FLOW))
    }

    @Test
    fun `provideLife must born the life`() {
        val store = LifeStoreImpl()
        val owner = TestLifecycleOwner()
        val life: Life = mock()
        val key = "key"

        owner.create()
        store.provideLife(key, owner, FakFactory(life))

        assert(store.get(key)?.keep == false)
        verify(life).onBorn()
        verifyNoMoreInteractions(life)
    }

    @Test
    fun `onDestroy must kill the life if keep is not called`() {
        val store = LifeStoreImpl()
        val owner = TestLifecycleOwner()
        val life: Life = mock()
        val key = "key"

        owner.create()
        store.provideLife(key, owner, FakFactory(life))
        owner.destroy()

        verify(life).onBorn()
        verify(life).onDie()
        verifyNoMoreInteractions(life)
        assert(store.get(key) == null)
    }

    @Test
    fun `onDestroy must not kill the life if keep is called`() {
        val store = LifeStoreImpl()
        val owner = TestLifecycleOwner()
        val life: Life = mock()
        val key = "key"

        owner.create()
        store.provideLife(key, owner, FakFactory(life))
        store.keep(key)
        owner.destroy()

        assert(store.get(key) != null)
        assert(store.get(key)?.keep == true)
        verify(life).onBorn()
        verifyNoMoreInteractions(life)
    }

    @Test
    fun `provideLife must provide the same instance for two owners in user flow`() {
        val store = LifeStoreImpl()
        val firstOwner = TestLifecycleOwner()
        val secondOwner = TestLifecycleOwner()
        val handler: AndroidLifeHandler = mock()
        val key = "key"

        firstOwner.create()
        val firstLife = store.provideLife(key, firstOwner, FakFactory(mock()), handler)
        store.keep(key)
        firstOwner.destroy()
        secondOwner.create()
        val secondLife = store.provideLife(key, secondOwner, FakFactory(mock()), handler)

        assert(store.get(key)?.keep == false)
        assert(firstLife === secondLife)
    }

    @Test
    fun `onBorn and onDie must call once in a flow with two owners`() {
        val store = LifeStoreImpl()
        val firstOwner = TestLifecycleOwner()
        val secondOwner = TestLifecycleOwner()
        val life: Life = mock()
        val key = "key"

        firstOwner.create()
        store.provideLife(key, firstOwner, FakFactory(life))
        store.keep(key)
        firstOwner.destroy()
        secondOwner.create()
        store.provideLife(key, secondOwner, FakFactory(life))
        secondOwner.destroy()

        verify(life, times(1)).onBorn()
        verify(life, times(1)).onDie()
        verifyNoMoreInteractions(life)
        assert(store.get(key) == null)
    }

    class FakFactory(private val life: Life) : LifeFactory<Life> {
        override fun get(): Life = life
    }
}
