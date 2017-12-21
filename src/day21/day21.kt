package day21

import util.ProgressReporter
import util.getInput


fun main(args: Array<String>) {
    val patterns = getInput("E:\\rickard\\Documents\\Advent of code\\src\\day21\\input", ::parsePattern)
    val smallPatterns = patterns.filter { it.template.width == 2 }
    val largePatterns = patterns.filter { it.template.width == 3 }
    val canvas = Canvas(".#./..#/###")

    println("--- CANVAS --- \n$canvas")
    val iterations = 18
    val progress = ProgressReporter(iterations)
    for ( a in 1..iterations) {
        if (canvas.width % 2 == 0)
            canvas.apply(smallPatterns)
        else
            canvas.apply(largePatterns)
        progress.iterationDone()
    }

    println("--- CANVAS --- \n$canvas")
    println("#On: ${canvas.getRaw().count { it == '#' }}")
}

fun parsePattern(row: String): Pattern {
    val parts = row.split("=>").map { it.trim() }
    return Pattern(parts[0], parts[1])
}



