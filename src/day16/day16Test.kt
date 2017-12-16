package day16

import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Day16Test {
    var testList = mutableListOf<String>()
    @BeforeMethod
    fun init() {
        testList = mutableListOf("a","b","c","d","e")
    }


    @Test
    fun testSpin() {
        testList.spin(2)
        assertEquals(
                mutableListOf("d","e","a","b","c"),
                testList
        )
    }

    @Test
    fun testSpin1() {
        testList.spin(1)
        assertEquals(
                mutableListOf("e","a","b","c","d"),
                testList
        )
    }

    @Test
    fun testSwap() {
        testList.exchange(2,3)
        assertEquals(
                mutableListOf("a","b","d","c","e"),
                testList)
    }

    @Test
    fun testPartner() {
        testList.partner("a", "e")
        assertEquals(
                mutableListOf("e","b","c","d","a"),
                testList)
    }

}
