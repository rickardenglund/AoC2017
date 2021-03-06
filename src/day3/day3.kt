package day3

import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

data class Pos (var x:Int, var y:Int)

fun main(args:Array<String>) {
    val target = 325489

    val width = getSize(target)
    println("Target: $target")
    println("Width: $width")

    val memory = createMemory(width)

    for (x in 0 until width)
        for (y in 0 until width) {
            if (memory[y][x] == target) {
                println("x: $x, y: $y")
                println("center: ${width/2}")
                println("Distance: ${getDistance(x,y,width/2)}")
                }
        }
//    printMat(memory)
}

fun getDistance(x: Int, y: Int, center: Int): Int {
    val xd =  (center-x).absoluteValue
    val yd = (center-y).absoluteValue
    return xd+yd
}

private fun createMemory(width: Int): Array<Array<Int>> {
    var n = 1
    val mem =  Array(width) {
        Array(width) { 0 }
    }

    var currentLayer = 0
    var layerStart = Pos(width/2, width/2)
    while (n < width*width) {


        val layer = createLayer(currentLayer, layerStart)
        for (pos in layer) {
//            println("x:${pos.x} y:${pos.x}")
            val sum = getSumNeighbours(mem, pos)
            val nextValue = if (sum == 0) 1 else sum
            if (nextValue > 325489) {
                println("NextValue: $nextValue")
                throw Exception("Klar")
            }
            mem[pos.y][pos.x] = nextValue
            n++
        }

        currentLayer++
        val last = layer.last()
//        println("Last: x:${last.x} y:${last.x}")
        layerStart = Pos(last.x+1, last.y)
    }

    return mem
}

fun getSumNeighbours(mem: Array<Array<Int>>, pos: Pos): Int {
    var sum = 0
    val deltas = listOf<Pos>(
            Pos(-1,0),
            Pos(-1,1),
            Pos(-1,-1),
            Pos(0,1),
            Pos(0,-1),
            Pos(1,-1),
            Pos(1,0),
            Pos(1,1)
    )
    for (delta in deltas) {
        try {
            sum += mem[pos.y+delta.y][pos.x+delta.x]
        } catch (e: ArrayIndexOutOfBoundsException) {

        }
    }
    return sum
}


fun createLayer(layer: Int, start: Pos): List<Pos> {
    val poss = MutableList(0){Pos(0,0)}
    val layerWidth = layer*2+1
    var current:Pos
    for (i in 0..Math.max(layerWidth-2, 0)) {
        poss.add(Pos(start.x,start.y-i))
    }

    current = poss.last()
    for (i in 1..layerWidth-1) {
        poss.add(Pos(current.x-i, current.y))
    }

    current = poss.last()
    for (i in 1..layerWidth-1) {
        poss.add(Pos(current.x, current.y+i))
    }

    current = poss.last()
    for (i in 1..layerWidth-1) {
        poss.add(Pos(current.x+i, current.y))
    }

    println("Wall: $layer LayerWidth: $layerWidth Size: ${poss.size}")
    return poss
}

fun printMat(rows: Array<Array<Int>>) {
    rows.map {
        val row = it.fold("", {total, next -> total + next.toString().padStart(4, ' ')})
        println(row)
    }
}

private fun getSize(target: Int) =
        Math.sqrt(target.toDouble()).toInt()+1
