package day19

import util.getInput


fun main(args: Array<String>) {
    val map = getProgram()
    val walker = Walker(map)

    var lastPos = Vec(-1, -1)
    while (lastPos != walker.pos) {
        lastPos = walker.pos
        walker.step()
    }
    walker.print()

}
fun getProgram():List<List<Char>> {
    return getInput("E:\\rickard\\Documents\\Advent of code\\src\\day19\\input",{a -> a.map{it}})
}
