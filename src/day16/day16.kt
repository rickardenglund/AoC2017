package day16

import util.ProgressReporter
import kotlin.system.measureTimeMillis

val programNames = listOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p")
val nPrograms = 16
val loopSize = 44
val iterations = 1_000_000_000 % loopSize

fun main(args: Array<String>) {
    val programs = MutableList(nPrograms, { it -> it})
    val moveList = input.split(",").map { createAction(it, programs) }
    val moves = List(moveList.size, {i -> moveList[i]})

    val total = measureTimeMillis {
        dance(programs, moves)
    }
    println("Total time: ${total/1000.toFloat()}")
}

fun dance(programs: MutableList<Int>, moves: List<() -> Unit>) {
    val progress = ProgressReporter(iterations)
    (1..iterations).forEach {
        moves.forEach { it() }
        progress.iterationDone()
    }
    programs.print()
}

fun List<Int>.print() {
    this.forEach{
        print(programNames[it])
    }
    println()
}

fun createAction(cmd: String, programs: MutableList<Int>): () -> Unit {
    if (cmd.startsWith("s")) {
        val n = cmd.substring(1).toInt()
        return {programs.spin(n)}
    }
    if (cmd.startsWith("x")) {
        val indices = cmd.substring(1).split("/").map { it.toInt() }
        val i1 = indices[0]
        val i2 = indices[1]
        return {programs.exchange(i1, i2)}
    }
    if (cmd.startsWith("p")) {
        val names = cmd.substring(1).split("/")
        val id1 = programNames.indexOf(names[0])
        val id2 = programNames.indexOf(names[1])
        return {programs.partner(id1, id2)}
    }
    return {}
}

fun MutableList<Int>.spin(n: Int) {
    val copy = this.toMutableList()
    val splitAt = this.size - n

    var i = 0
    (splitAt until copy.size).forEach { this[i] = copy[it]; i++ }
    (0 until splitAt).forEach { this[i] = copy[it]; i++ }
}

fun MutableList<Int>.exchange(i: Int, j: Int) {
    val iVal = this[i]
    this[i] = this[j]
    this[j] = iVal
}

fun MutableList<Int>.partner(e: Int, e1: Int) {
    val eIndex = this.indexOf(e)
    val e1Index = this.indexOf(e1)
    val eVal = this[eIndex]

    this[eIndex] = this[e1Index]
    this[e1Index] = eVal
}
