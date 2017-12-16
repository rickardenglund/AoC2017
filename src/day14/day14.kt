package day14

import day10.knotHash
import kotlin.math.absoluteValue

val input = "jzgqcdpd"
val test = "flqrgnkx"

fun main(args: Array<String>) {
    var mem = mutableListOf<MutableList<String>>()
    for (i in 0 until 128) {
        val str = "$input-$i"
        mem.add(knotHash(str).toBits().toMem())
    }

    val free = mem.sumBy { it.count{it == "#"} }
//    mem.print()
    println("Used: $free")
    val set = mutableSetOf<Coord>()
    val group = mem.findGroup(Coord(1,127), set)


    var n = 0
    var pos = mem.firstFree()
    while (pos != null) {
        val group = mutableSetOf<Coord>()
        mem.findGroup(pos, group)
        mem.assignGroup(n, group.toList())
        n++
        pos = mem.firstFree()
        println("$n: $pos")
    }

    mem.print()
    println("N: $n")


}

fun List<List<String>>.print() {
    this.map {
        it.map {print("$it ")}
        println()
    }
}

fun MutableList<MutableList<String>>.assignGroup(name: Int, members: List<Coord>) {
    for (pos in members) {
        this[pos.y][pos.x] = name.toString()
//        println("${pos.x} ${pos.y}")
    }

}

fun List<List<String>>.firstFree(): Coord? {
    for (y in 0 until this.size) {
        val row = this[y]
        for (x in 0 until row.size) {
            if (row[x] == "#") return Coord(x,y)
        }
    }
    return null
}

fun List<List<String>>.findGroup(origin: Coord, visited: MutableSet<Coord>){
    if (this[origin.y][origin.x]!="#") return

    visited.add(origin)
    val neighBours = this.getFreeNeighbours(origin)
//    println("O: $origin N: $neighBours")

    for (pos in neighBours) {
        if (visited.add(pos)) {
            this.findGroup(pos, visited)
        }
    }
}

fun String.toBits(): String {
    return this
            .map { Integer.toBinaryString(it.toString().toInt(radix = 16)).padStart(4,'0') }
            .fold(""){acc, s -> acc + s}
}

fun String.toMem(): MutableList<String>{
    val res = mutableListOf<String>()
    this.map {
        if (it == '1') res.add("#")
        else res.add(".")
    }
    return res
}

data class Coord(val x: Int, val y: Int)

fun List<List<String>>.getFreeNeighbours(pos: Coord): List<Coord> {
    val neighbours = mutableListOf<Coord>()
    for (i in -1..1)
        for (j in -1..1) {
            if (i.absoluteValue != j.absoluteValue) {
                try {
                    val char = this[pos.y + i][pos.x + j]
                    if (char == "#")
                        neighbours.add(Coord(pos.x + j, pos.y + i))

                } catch (e: Exception) {
                }
            }
        }
    return neighbours
}