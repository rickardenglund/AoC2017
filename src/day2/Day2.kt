package day2

import java.io.File
import java.io.InputStream

data class MinMax(val min :Int, val max: Int)

fun main(args : Array<String>) {
    val rows = getInput()//listOf(listOf(5,9,2,8), listOf(9,4,7,3), listOf(3,8,6,5))
    val diffs = rows.map{
        val pair = getDivideAble(it)
        println(pair)
        div(pair.first, pair.second)
    }
    println("Sum: ${diffs.sum()}")
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

fun getInput(): List<List<Int>> {
    val inputStream: InputStream = File("E:\\rickard\\Documents\\Advent of code\\src\\day2\\input").inputStream()
    val lineList = mutableListOf<List<Int>>()

    inputStream.bufferedReader().useLines { lines -> lines.forEach {
        lineList.add(it.split("\\W+".toRegex()).map { it.toInt() })}
    }
    return lineList
}