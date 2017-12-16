package day16

import java.lang.System.currentTimeMillis
import kotlin.system.measureTimeMillis

val testInput = "s1,x3/4,pe/b"
val programNames = listOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p")

data class Perm(val index: Int, val perm: List<Int>)

fun main(args: Array<String>) {
    val programs = mutableListOf<Int>()
    (0..15).forEach{programs.add(it)}
    val moves = input.split(",").map { cmd(it, programs) }

    dance(programs, moves)
}

fun dance(programs: MutableList<Int>, moves: List<() -> Unit>) {
    var time = currentTimeMillis()
    val stackSize = 10_000
    val targetIterations = 1_000_000_000
    val total = measureTimeMillis {
        (1..targetIterations).forEach {
            if (it % stackSize == 0) {
                val cur = currentTimeMillis()
                val neededIterations = targetIterations / stackSize
                val stackTime = cur - time
                val seconds = (neededIterations * stackTime) / 1000
                val min = seconds % 60

                println("${(it.toFloat() / targetIterations.toFloat())*100}% stackTime: $stackTime stackSize: $stackSize Estimate: ${seconds/60}:$min")
                time = cur
            }
            moves.forEach { move ->
                move()
            }
        }
    }
    println("total: ${total/1000.toFloat()}")
    programs.print()
}

fun List<Int>.print() {
    this.forEach{
        print(programNames[it])
    }
}

fun cmd(cmd: String, programs: MutableList<Int>): () -> Unit {
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

//70
fun MutableList<Int>.spin(n: Int) {
    val copy = this.toMutableList()
    val splitAt = this.size - n
    this.clear()
    (splitAt..copy.size-1).forEach { this.add(copy[it]) }
    (0..splitAt-1).forEach { this.add(copy[it]) }
}

//10
fun MutableList<Int>.exchange(i: Int, j: Int) {
    val ival = this[i]
    this[i] = this[j]
    this[j] = ival
}

//30
fun MutableList<Int>.partner(e: Int, e1: Int) {
    val eIndex = this.indexOf(e)
    val e1Index = this.indexOf(e1)
    if (e1Index == -1 || eIndex == -1) {
        println("e: $e e1: $e1 $this")
    }
    val eval = this[eIndex]
    this[eIndex] = this[e1Index]
    this[e1Index] = eval
}
