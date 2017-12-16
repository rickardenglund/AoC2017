package day12

import util.getInput


data class Node(val name: String, val neighbours: List<String>)

fun main(args: Array<String>) {
    var nodes = parseInput("testInput")

    var nGroups = 0
    while (!nodes.isEmpty()) {
        val visitedNodes = countGroup(nodes, mutableListOf(), nodes[0])
       nodes = nodes.filter { !visitedNodes.contains(it.name) }
        nGroups++
    }

    println("Ngroups: $nGroups")
}

fun countGroup(nodes: List<Node>, visited: MutableList<Node>, node: Node): MutableList<Node> {
    if (node in visited) return visited
    visited.add(node)

    val neighboursToVisit = node.neighbours.filter{nodes.contains(it)}.map{ node -> nodes.find{node == it.name}!!}
    neighboursToVisit.forEach{ countGroup(nodes, visited, it) }
    return visited
}

fun List<Node>.contains(name: String):Boolean {
    return this.count{it.name == name} > 0
}

fun parseInput(name: String) : List<Node>{
    val regex = "(\\w+) <->".toRegex()
    return getInput("E:\\rickard\\Documents\\Advent of code\\src\\day12\\$name",
            { row ->
                val match = regex.find(row)
                if (match != null) {
                    val name = match!!.groupValues[1]

                    val neighbours = "<-> (.*)".toRegex().find(row)!!.groupValues[1].split(", ")
                    Node(name, neighbours)
                } else throw Exception("Failed to parse $row")

            })
}