package com.github.hadilq.androidlifecyclehandler

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class SLifeTest {

    @Test
    fun `life observing nested life must not call onBorn `() {
        val nestedLife: Life = mock()
        val life = FakeSLife()

        life.syncLife(nestedLife)

        verify(nestedLife, never()).onBorn()
    }

    @Test
    fun `life born then observing nested life must not call onBorn `() {
        val nestedLife: Life = mock()
        val life = FakeSLife()
        life.onBorn()

        life.syncLife(nestedLife)

        verify(nestedLife, times(1)).onBorn()
    }

    @Test
    fun `life observing nested life then born then die must call onDie `() {
        val nestedLife: Life = mock()
        val life = FakeSLife()
        life.onBorn()
        life.syncLife(nestedLife)

        life.onDie()

        verify(nestedLife, times(1)).onDie()
    }

    @Test
    fun `life born then observing nested life then die must call onDie `() {
        val nestedLife: Life = mock()
        val life = FakeSLife()
        life.onBorn()
        life.syncLife(nestedLife)

        life.onDie()

        verify(nestedLife, times(1)).onDie()
    }

    class FakeSLife : SLife() {
        fun syncLife(life: Life) = life.sync()
    }
}
