package day20

import kotlin.math.absoluteValue

data class Vec(val x: Long, val y: Long, val z: Long) {
    operator fun plus(other: Vec): Vec {
        return Vec(this.x + other.x, this.y + other.y, this.z + other.z)
    }
}

data class Particle(var pos: Vec, var vel: Vec, val acc: Vec) {
    fun tick() {
        vel += acc
        pos += vel
    }

    fun distanceToOrigo(): Long {
        return pos.x.absoluteValue + pos.y.absoluteValue + pos.z.absoluteValue
    }
}

fun MutableList<Particle>.resolveCollisions() {
    val collidingParticles =
            this.filter {
                particle -> this.filter{it.pos == particle.pos}.size > 1
            }
    this.removeAll(collidingParticles)
}