package day5

import input.getInput

fun main(args: Array<String>) {
    val input = getInput("E:\\rickard\\Documents\\Advent of code\\src\\day5\\input",
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
//        println("cur: $cur input: $input")
    }
    println(cnt)
}
