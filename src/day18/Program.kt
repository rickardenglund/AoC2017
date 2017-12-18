package day18

import java.util.*
import java.util.concurrent.LinkedBlockingDeque

class Program(id: Long) {
    var pc = 0
    private val registers = hashMapOf<String, Long>()
    val inbox: Queue<Long> = LinkedBlockingDeque()
    var sentValues = 0

    init {
        registers["p"] = id
    }

    fun receive(registry: String): Boolean {
        val value = inbox.poll()
        if (value != null) {
            registers[registry] = value
            pc++
            return true
        }
        return false
    }

    fun send(recipient: Program, value: Long) {
        recipient.inbox.add(value)
        sentValues++
        pc++
    }

    fun get(str: String): Long {
        try {
            return str.toLong()
        } catch (e: NumberFormatException) {}
        return registers[str] ?: 0
    }

    fun set(registry: String, value: Long) {
        registers[registry] = value
        pc++
    }

    fun print() {
        println("###")
        registers.entries.forEach(::println)
        println()
    }

    fun jgz(check: String, steps: String) {
        if (get(check) > 0)
            pc += get(steps).toInt()
        else
            pc++
    }

}