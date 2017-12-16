package day2

import util.getInput

data class MinMax(val min :Int, val max: Int)

fun main(args : Array<String>) {
    val rows = getInput(
            "E:\\rickard\\Documents\\Advent of code\\src\\day2\\testInput",
            ::parseRow)//listOf(listOf(5,9,2,8), listOf(9,4,7,3), listOf(3,8,6,5))
    val diffs = rows.map{
        val pair = getDivideAble(it)
        println(pair)
        div(pair.first, pair.second)
    }
    println("Sum: ${diffs.sum()}")
}

fun parseRow(line: String) :List<Int> {
    return line.split("\\W+".toRegex()).map { it.toInt() }
}

fun getMinMax(row: List<Int>): MinMax {
    return MinMax(row.min()!!, row.max()!!)
}

fun getDivideAble(row: List<Int>): Pair<Int,Int> {
    for (n in row)
        for (y in row) {
            if (n !== y && n % y == 0) {
                return Pair(n, y)
            }
        }
    throw IllegalArgumentException("Invalid Input, Divisible number")
}

fun diff(value: MinMax): Int {
    return value.max - value.min
}

fun div(n:Int, y:Int): Int {
    return n/y
}

