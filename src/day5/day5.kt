package day5

import java.io.File
import java.io.InputStream
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val input = parseInput()
    println(input)

    var cur = 0
    var cnt = 0

    while (cur > -1 && cur < input.size) {
        val prevIndex = cur
        cur += input[cur]
        if (input[prevIndex] < 3) {
            input[prevIndex] = input[prevIndex] + 1
        } else {
            input[prevIndex] = input[prevIndex] - 1
        }
        cnt++
//        println("cur: $cur input: $input")
    }
    println(cnt)
}


fun parseInput(): MutableList<Int> {
    val inputStream: InputStream = File("E:\\rickard\\Documents\\Advent of code\\src\\day5\\input").inputStream()
    val lineList = mutableListOf<Int>()

    inputStream.bufferedReader().useLines { lines -> lines.forEach {
        lineList.add(
                it.trim().toInt()
        )
    }
    }
    return lineList
}

