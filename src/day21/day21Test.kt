package day21

import org.testng.annotations.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Test
fun patternMatches() {
    val canvas = Canvas(".#./..#/###")
    val pattern = Pattern(".#./..#/###", "..../..../..../....")

    assertTrue (canvas.matches(0,pattern))
}

@Test
fun patternNotMatchShifted() {
    val canvas = Canvas(".#./#.#/###")
    val pattern = Pattern(".#./..#/###", "..../..../..../....")

    assertFalse(canvas.matches(1,pattern))
}

@Test
fun patternNotMatches() {
    val canvas = Canvas(".#./..#/###")
    val pattern = Pattern(".#./..#/##.", "..../..../..../....")

    assertFalse (canvas.matches(0,pattern))
}


@Test
fun patternMatch2SizePattern() {
    val canvas = Canvas(".##/..#/###")
    val pattern = Pattern(".#/..", ".../.../.../...")

    assertTrue (canvas.matches(0,pattern))
}

@Test
fun patternApply4by4() {
    val canvas = Canvas(".#.#/.#.#/.#.#/.#.#")
    val pattern = Pattern(".#/.#",  "..#/..#/..#/..#")

    canvas.apply(listOf(pattern))
    assertEquals("..#..#/..#..#/..#..#/..#..#/..#..#/..#..#", canvas.getRaw())
}

@Test
fun applyPatternSuccess() {
    val input = ".#/.."
    val target = ".../.../..."
    val canvas = Canvas(input)
    val pattern = Pattern(input, target)

    canvas.apply(listOf(pattern))
    assertEquals(target, canvas.getRaw())
}

@Test
fun apply2Patterns() {
    val canvas = Canvas("...#/..../..../....")
    val pattern1 = Pattern("../..", ".../.../...")
    val pattern2 = Pattern(".#/..", "..#/..#/..#")

    canvas.apply(listOf(pattern1, pattern2))
    assertEquals(".....#/.....#/.....#/....../....../......", canvas.getRaw())
}

@Test
fun getRaw() {
    val rawStr = ".##/..#/###"
    val canvas = Canvas(rawStr)
    assertEquals(rawStr, canvas.getRaw())
}

@Test
fun mergeCanvases() {
    val str = "../.."
    val canvas = Canvas(str)
    val canvases = listOf(canvas,canvas,canvas,canvas)

    assertEquals("..../..../..../....",
            Canvas(canvases.merge(canvas.width)).getRaw())
}