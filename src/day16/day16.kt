package day16

import java.lang.System.currentTimeMillis
import kotlin.system.measureTimeMillis

val testInput = "s1,x3/4,pe/b"
val programNames = listOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p")
enum class ActionType {Spin, Exchange, Partner, NoAction}
data class Action(val type: ActionType, val n1: Int, val n2: Int)

fun main(args: Array<String>) {
    val programs = MutableList<Int>(16, {it -> it})
    val moveList = input.split(",").map { cmd(it) }
    val moves = Array<Action>(10000, {i -> moveList[i]})

    dance(programs, moves)

}

fun dance(programs: MutableList<Int>, moves: Array<Action>) {
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
                val min = seconds / 60
                val h = seconds / 60 / 60

                println(
                        "${(it.toFloat() / targetIterations.toFloat())*100}% " +
                                "stackTime: $stackTime " +
                                "stackSize: $stackSize " +
                                "Estimate: ${h%(60*60)}h ${min%60}m ${seconds%60}s")
                time = cur
            }

            moves.forEach { action ->
                when (action.type) {
                    ActionType.Spin -> programs.spin(action.n1)
                    ActionType.Exchange -> programs.exchange(action.n1, action.n2)
                    ActionType.Partner -> programs.partner(action.n1, action.n2)
                    ActionType.NoAction -> println("Warning no Action")
                }
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

fun cmd(cmd: String): Action {
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

//70
fun MutableList<Int>.spin(n: Int) {
    val copy = this.toMutableList()
    val splitAt = this.size - n

    var i = 0
    (splitAt..copy.size-1).forEach { this[i] = copy[it]; i++ }
    (0..splitAt-1).forEach { this[i] = copy[it]; i++ }
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
