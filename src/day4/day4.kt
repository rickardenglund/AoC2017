package day4

import java.io.File
import java.io.InputStream


fun main(args: Array<String>) {
    val validLines = parseInput().count{ lineIsValid(it)}
    println("Valid lines: $validLines")

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

fun parseInput(): List<List<String>> {
    val inputStream: InputStream = File("E:\\rickard\\Documents\\Advent of code\\src\\day4\\input").inputStream()
    val lineList = mutableListOf<List<String>>()

    inputStream.bufferedReader().useLines { lines -> lines.forEach {
            lineList.add(
                    it.split("\\W+".toRegex())
            )
        }
    }
    return lineList
}