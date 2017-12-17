package day17

import util.reportProgress
import java.lang.System.currentTimeMillis

val input = 335
val nIterations = 50_000_000
val batchSize = 100_000

fun main(args: Array<String>) {
    val root = BufferNode(0)
    var currentNode = root
    var time = currentTimeMillis()
    for (i in 1..nIterations) {
        currentNode = currentNode.insert(input, i)
        if (i % batchSize == 0) {
            val now = currentTimeMillis()
            reportProgress(i, nIterations, batchSize, now - time)
            time = now
        }
    }

    println("next: ${root.next.value}")
}

class BufferNode<T>(val value: T) {
    var next = this

    fun insert(steps: Int, newValue: T):BufferNode<T> {
        var curNode = this
        var nextNode = this.next
        for (i in 0 until steps) {
            curNode = nextNode
            nextNode = curNode.next
        }
        val new = BufferNode(newValue)
        curNode.next = new
        new.next = nextNode

        return new
    }

    override fun toString(): String {
        var res = value.toString()
        var cur = this.next
        while (cur != this) {
            res += ",${cur.value}"
            cur = cur.next
        }
        return res
    }

}