package com.github.hadilq.androidlifecyclehandler

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class MainLifeViewModelTest {

    @Test
    fun `ViewModel initiated must not call the onBorn`() {
        val owner = TestLifecycleOwner()
        val life: Life = mock()

        val viewModel = MainLifeViewModel(owner, FakFactory(life))

        verify(life, never()).onBorn()
    }

    @Test
    fun `LifecycleOwner created must call the onBorn`() {
        val owner = TestLifecycleOwner()
        val life: Life = mock()

        val viewModel = MainLifeViewModel(owner, FakFactory(life))
        owner.create()

        verify(life).onBorn()
    }

    @Test
    fun `LifecycleOwner created then ViewModel initiated must call the onBorn`() {
        val owner = TestLifecycleOwner()
        val life: Life = mock()

        owner.create()
        val viewModel = MainLifeViewModel(owner, FakFactory(life))

        verify(life).onBorn()
    }

    class FakFactory(private val life: Life) : LifeFactory<Life> {
        override fun get(): Life = life
    }
}
