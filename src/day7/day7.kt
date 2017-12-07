package day7

import java.io.File
import java.io.InputStream

data class ParseResult(val name: String, val weight: Int, val children: List<String>)

fun main(args: Array<String>) {
    val nodes = parseInput()
//    val root = findRoot(nodes)
    val root = getProgram("ihnus", nodes)
    val total = getWeight(root, nodes)
    root.children.map { childname ->
        val node = getProgram(childname, nodes)
        println("$childname ${getWeight(node, nodes)}")
    }
    println(total)
}

fun getProgram(name: String, nodes: List<ParseResult>) : ParseResult {
    return nodes.find { name == it.name }!!
}

fun getWeight(root: ParseResult, nodes: List<ParseResult> ) : Int{
    if (root.children.count() == 0) {
        return root.weight
    }
    val res = root.weight + root.children.sumBy {
        name ->
        val child = getProgram(name, nodes)
        getWeight(child,nodes)
    }
    return res
}

fun findRoot(inputs : List<ParseResult>): ParseResult {
    inputs.map { me ->
        val nParents = inputs.filter { maybeParent ->
            maybeParent.children.contains(me.name)
//                println("Parent: ${maybeParent} child: ${me.name}")
        }.count()
        if (nParents < 1) return me
    }
    throw Exception("No Root Exception")
}

fun parseInput(): List<ParseResult> {
    println("Parsing: ")
    val results = listOf<ParseResult>().toMutableList()
    val inputStream: InputStream = File("E:\\rickard\\Documents\\Advent of code\\src\\day7\\input.txt").inputStream()

    inputStream.bufferedReader().useLines { lines -> lines.forEach {
        val lineMatcher = "(\\w+)\\s+\\((\\d+)\\)(.*)".toRegex()
        val match = lineMatcher.find(it)
        if (match != null) {
            val newProgram =
                    ParseResult(
                            name = match!!.groups[1]!!.value,
                            weight = match!!.groups[2]!!.value.toInt(),
                            children = parseGroups(match!!.groups[3]!!.value))
            results.add(newProgram)

            }
        }
    }
    return results
}

fun parseGroups(value: String): List<String> {

    val matcher = "\\w+".toRegex()
    val matches = matcher.findAll(value)
    return matches.map { it.value }.toList()
}

