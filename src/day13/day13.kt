package day13

import input.getInput

data class Wall(val time: Int, val depth: Int)

fun main(args: Array<String>) {
    val input = parseInput("testInput")

//    input.map{println("pos: ${scannerPos(it, 10)}")}


    var severity = Int.MAX_VALUE
    var delay = 0
    while (severity > 0) {
        severity = getSeverity(input, delay)
        delay++
    }
        println("delay: ${delay-1} S: $severity")

    println(severity)
}

enum class Direction{UP,DOWN}

fun getSeverity(input: List<Wall>, delay: Int): Int {

    return input.filter { scannerPos(it, delay) == 0 }.sumBy { it.time*it.depth }
}

fun scannerPos(wall: Wall, delay: Int) : Int {
    var scannerPos = -1
    var scannerDirection = Direction.DOWN
    for (currentTime in 0..(wall.time + delay)) {
        if (scannerPos == 0) scannerDirection = Direction.DOWN
        if (scannerPos == wall.depth -1) scannerDirection = Direction.UP

        when (scannerDirection) {
            Direction.UP -> scannerPos--
            Direction.DOWN -> scannerPos++
        }
//        if (currentTime == delay) println("0:   $scannerPos $scannerDirection")
//        else println("$scannerPos $scannerDirection")
//        println(scannerPos)
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