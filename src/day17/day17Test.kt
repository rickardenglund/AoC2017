package day17

import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import org.testng.asserts.*
import kotlin.test.assertEquals


class BufferTest {
    var bufferRoot = BufferNode(0)

    @BeforeMethod
    fun init() {
        bufferRoot = BufferNode(0)
    }

    @Test
    fun getInitial() {
        assertEquals(0, bufferRoot.value)
    }

    @Test
    fun getNext() {
        assertEquals(bufferRoot.value,bufferRoot.next.value)
    }

    @Test
    fun insert0() {
        bufferRoot.insert(0,1)
        bufferRoot.insert(0,2)
        assertEquals("0,2,1",bufferRoot.toString())
    }

    @Test
    fun insert1() {
        bufferRoot.insert(1,1)
        bufferRoot.insert(1,2)
        bufferRoot.insert(1,3)
        assertEquals("0,1,3,2",bufferRoot.toString())
    }

    @Test
    fun insert2() {
        bufferRoot.insert(2,1)
        bufferRoot.insert(2,2)
        bufferRoot.insert(2,3)
        assertEquals("0,2,1,3",bufferRoot.toString())
    }

    @Test
    fun insert3() {
        bufferRoot.insert(3,1)
        bufferRoot.insert(3,2)
        bufferRoot.insert(3,3)
        assertEquals("0,3,1,2",bufferRoot.toString())
    }

    @Test
    fun example() {
        var cur = bufferRoot
        for (i in 1..3) {
            cur = cur.insert(3,i)
        }
        assertEquals("0,2,3,1", bufferRoot.toString())

    }


}

