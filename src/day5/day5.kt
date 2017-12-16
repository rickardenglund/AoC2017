package day5

import util.getInput

fun main(args: Array<String>) {
    val input = getInput("E:\\rickard\\Documents\\Advent of code\\src\\day5\\testInput",
            {str -> str.toInt()}).toMutableList()
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
//        println("cur: $cur testInput: $testInput")
    }
    println(cnt)
}
