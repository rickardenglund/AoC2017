package day16

import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Day16Test {
    var testList = MutableList(5,{i -> i+1})
    @BeforeMethod
    fun init() {
        for (i in 0 until testList.size) {
            testList[i] = i +1
        }
    }


    @Test
    fun testSpin() {
        testList.spin(2)
        assertEquals(
                mutableListOf(4,5,1,2,3),
                testList
        )
    }

    @Test
    fun testSpinEvenSize() {
        val list = mutableListOf(1,2,3,4)
        list.spin(2)
        assertEquals(
                mutableListOf(3,4,1,2),
                list
        )
    }

    @Test
    fun testSpinTwice() {
        testList.spin(2)
        testList.spin(2)
        assertEquals(
                mutableListOf(2,3,4,5,1),
                testList
        )
    }

    @Test
    fun testSpin0() {
        testList.spin(0)
        assertEquals(
                mutableListOf(1,2,3,4,5),
                testList
        )
    }

    @Test
    fun testSpin1() {
        testList.spin(1)
        assertEquals(
                mutableListOf(5,1,2,3,4),
                testList
        )
    }

    @Test
    fun testSpinAll() {
        testList.spin(testList.size)
        assertEquals(
                mutableListOf(1,2,3,4,5),
                testList
        )
    }

    @Test
    fun testSwap() {
        testList.exchange(2,3)
        assertEquals(
                mutableListOf(1,2,4,3,5),
                testList)
    }

    @Test
    fun testPartner() {
        testList.partner(1,5)
        assertEquals(
                mutableListOf(5,2,3,4,1),
                testList)
    }

}
