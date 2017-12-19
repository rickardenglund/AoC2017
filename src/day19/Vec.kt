package day19

data class Vec(val x: Int, val y: Int) {
    fun add(other: Vec): Vec {
        return Vec(x + other.x, y + other.y)
    }

}

val UP = Vec(0, -1)
val LEFT = Vec(-1, 0)
val DOWN = Vec(0, 1)
val RIGHT = Vec(1, 0)
val allDirs = listOf(UP, LEFT, DOWN, RIGHT)