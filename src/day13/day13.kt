package day13

import input.getInput

data class Wall(val time: Int, val depth: Int)

fun main(args: Array<String>) {
    val input = parseInput("input")

    for (delay in 1 until Int.MAX_VALUE) {
//val    delay = 1
        if (delay % 10000 == 0) println(delay)
        val safe = isDelaySafe(input, delay)
        if (safe) {
            println("D: $delay")
            break
        }
    }

//    var severity = Int.MAX_VALUE
//    var delay = 0
//    while (severity > 0) {
//        severity = getSeverity(input, delay)
//        delay++
//    }
//        println("delay: ${delay-1} S: $severity")
}

enum class Direction{UP,DOWN}

fun getSeverity(input: List<Wall>): Int {
    return input.filter { scannerPos(it, it.time) == 0 }.sumBy { it.time*it.depth }
}

fun isDelaySafe(input: List<Wall>, delay: Int): Boolean {
    val poss = input.map{scannerPos(it,it.time+delay )}
//    poss.map { print("$it ") }
//    println()
    return poss.count{it == 0} ==0

}

fun scannerPos(wall: Wall, clock: Int) : Int {
    var scannerPos = -1
    var scannerDirection = Direction.DOWN
    for (currentTime in 0..(clock % (wall.depth*2-2))) {
        if (scannerPos == 0) scannerDirection = Direction.DOWN
        if (scannerPos == wall.depth -1) scannerDirection = Direction.UP

        when (scannerDirection) {
            Direction.UP -> scannerPos--
            Direction.DOWN -> scannerPos++
        }
    }
    return scannerPos
}






fun parseInput(name: String) : List<Wall>{
    return getInput("E:\\rickard\\Documents\\Advent of code\\src\\day13\\$name",
            { row ->
                val res = row.split(": ")
                val time = res[0]
                val depth = res[1]
                Wall(time.toInt(), depth.toInt())

            })
}