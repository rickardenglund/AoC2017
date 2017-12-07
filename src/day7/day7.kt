package day7

import input.getInput

data class ParseResult(val name: String, val weight: Int, val children: List<String>)

fun main(args: Array<String>) {
    val nodes = getInput("E:\\\\rickard\\\\Documents\\\\Advent of code\\\\src\\\\day7\\\\input.txt", ::parseLine)
//    val root = findRoot(nodes)
    val root = getProgram("ihnus", nodes)
    val total = getWeight(root, nodes)
    root.children.map { childName ->
        val node = getProgram(childName, nodes)
        println("$childName ${getWeight(node, nodes)}")
    }
    println(total)
}

fun parseLine(line: String): ParseResult {
    val lineMatcher = "(\\w+)\\s+\\((\\d+)\\)(.*)".toRegex()
    val match = lineMatcher.find(line)
    if (match != null) {
        return ParseResult(
                name = match.groups[1]!!.value,
                weight = match.groups[2]!!.value.toInt(),
                children = parseGroups(match.groups[3]!!.value))
    }
    throw Exception("Failed to parse line: $line")
}

fun parseGroups(value: String): List<String> {
    val matcher = "\\w+".toRegex()
    val matches = matcher.findAll(value)
    return matches.map { it.value }.toList()
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


