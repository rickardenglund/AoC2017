package day21

import util.getInput


fun main(args: Array<String>) {
    val patterns = getInput("E:\\rickard\\Documents\\Advent of code\\src\\day21\\testInput", ::parsePattern)
    val smallPatterns = patterns.filter { it.template.width == 2 }
    val largePatterns = patterns.filter { it.template.width == 3 }
    val canvas = Canvas(".#./..#/###")


    val newWidth = canvas.width + canvas.width / 3
    val newCanvas = Canvas(("O".repeat(newWidth) + "/").repeat(newWidth))

    println("### CANVAS ### \n$canvas")
    println("### CANVAS ### \n$newCanvas")
}

fun parsePattern(row: String): Pattern {
    val parts = row.split("=>").map { it.trim() }
    return Pattern(parts[0], parts[1])
}



