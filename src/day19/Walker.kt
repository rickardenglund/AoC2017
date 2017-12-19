package day19

class Walker(val map: List<List<Char>>) {
    var pos: Vec
    var velocity = Vec(0, 1)
    var str = ""
    val visited = mutableListOf<Vec>()
    init {
        val startPos = map[0].indexOf('|')
        pos = Vec(startPos, 0)
        visited.add(pos)
    }

    fun doTurn():Boolean {
        return get(pos) == '+'
    }

    fun turn() {
        for (dir in allDirs) {
            val newPos = pos.add(dir)
            val newChar = get(newPos)
            if ( !(newPos in visited) && newChar in "-|ABCDEFGHIJKLMNOPQRSTUVXYZ") {
                pos = newPos
                velocity = dir
            }
        }
    }

    fun step() {
        val curChar = get(pos)
        if (curChar in "ABCDEFGHIJKLMNOPQRSTUVXYZ") str += curChar
        if (!doTurn()) {
            val newPos = pos.add(velocity)
            if (get(newPos) != ' ') pos = newPos
        } else {
            turn()
        }
        visited.add(pos)
    }

    fun get(pos: Vec): Char {
        if (pos.x > -1 && pos.y > -1 && pos.y < map.size && pos.x < map[pos.y].size) {
            return map[pos.y][pos.x]
        } else
        {
            return ' '
        }
    }

    fun print() {
        for (ri in 0 until map.size) {
            for (ci in 0 until map[ri].size) {
                if (Vec(ci, ri) != pos)
                    print(map[ri][ci])
                else
                    print('x')
            }
            println()
        }
        println("(${pos.x}, ${pos.y})")
        println("Steps: ${visited.size - 1} str: $str")
    }
}