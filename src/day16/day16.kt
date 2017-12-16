package day16

import kotlin.system.measureTimeMillis

val programNames = listOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p")
val nPrograms = 16
val loopSize = 44
val iterations = 1_000_000_000 % loopSize

enum class ActionType {Spin, Exchange, Partner, NoAction}
data class Action(val type: ActionType, val n1: Int, val n2: Int)

fun main(args: Array<String>) {
    val programs = MutableList(nPrograms, { it -> it})
    val moveList = input.split(",").map { createAction(it) }
    val moves = Array(moveList.size, {i -> moveList[i]})

    val total = measureTimeMillis {
        dance(programs, moves)
    }
    println("Total time: ${total/1000.toFloat()}")
}

fun dance(programs: MutableList<Int>, moves: Array<Action>) {
    (1..iterations).forEach {
        moves.forEach { action ->
            when (action.type) {
                ActionType.Spin -> programs.spin(action.n1)
                ActionType.Exchange -> programs.exchange(action.n1, action.n2)
                ActionType.Partner -> programs.partner(action.n1, action.n2)
                ActionType.NoAction -> println("Warning no Action")
            }
        }
    }
    programs.print()
}

fun List<Int>.print() {
    this.forEach{
        print(programNames[it])
    }
    println()
}

fun createAction(cmd: String): Action {
    if (cmd.startsWith("s")) {
        val n = cmd.substring(1).toInt()
        return Action(ActionType.Spin, n, -1)
    }
    if (cmd.startsWith("x")) {
        val indices = cmd.substring(1).split("/").map { it.toInt() }
        val i1 = indices[0]
        val i2 = indices[1]
        return Action(ActionType.Exchange,i1,i2)
    }
    if (cmd.startsWith("p")) {
        val names = cmd.substring(1).split("/")
        val id1 = programNames.indexOf(names[0])
        val id2 = programNames.indexOf(names[1])
        return Action(ActionType.Partner,id1, id2)
    }
    return Action(ActionType.NoAction, -1,-1)
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
