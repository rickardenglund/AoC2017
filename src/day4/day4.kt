package day4

import input.getInput

fun main(args: Array<String>) {
    val validLines = getInput("E:\\rickard\\Documents\\Advent of code\\src\\day4\\testInput",
            {line -> line.split("\\W+".toRegex())})

    val nValidLines = validLines.count{ lineIsValid(it)}
    println("Valid lines: $nValidLines")

//    parseInput().forEach {println("$it : ${lineIsValid(it)}")}
}

fun lineIsValid(words: List<String>): Boolean {
    words.forEach {
        val cnt = words.count {
            word -> word.toCharArray().sorted() == it.toCharArray().sorted()
        }
        if (cnt > 1) {
            return false
        }
    }
    return true
}