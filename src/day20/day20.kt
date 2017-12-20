package day20

import util.ProgressReporter
import util.getInput

fun main(args: Array<String>) {
    val iterations = 5000
    val particles = parse().toMutableList()
    val reporter = ProgressReporter(iterations)

    for (i in 1..iterations) {
        particles.resolveCollisions()
        particles.forEach{it.tick()}
        if (i % 100 == 0)
            println("size: ${particles.size}")
        reporter.iterationDone()
    }
}

fun parse(): List<Particle> {
    return getInput("E:\\rickard\\Documents\\Advent of code\\src\\day20\\input",
            ::parseLine)
}

fun parseLine(line: String): Particle {
    return Particle(line.getVec("p"), line.getVec("v"), line.getVec("a"))
}

fun String.getVec(name: String): Vec {
    val str = "$name=<(.*?)>"
    val match = str
                    .toRegex()
            .find(this, 0)

    if (match != null) {
        val pList = match.groupValues[1]
                .split(",")
                .map { it.trim().toLong() }
        return Vec(pList[0], pList[1], pList[2])
    } else {
        throw Exception("Failed to parse: $this")
    }
}