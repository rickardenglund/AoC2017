package day8

import input.getInput
import java.lang.Integer.max

data class Instruction(
        val registry: String,
        val inc: Boolean,
        val value: Int,
        val conditionRegistry: String,
        val conditionOperator: String,
        val conditionConstant: Int)

fun main(args: Array<String>) {
    val registers = hashMapOf<String, Int>()
    val instructions = getInput("E:\\rickard\\Documents\\Advent of code\\src\\day8\\input", ::parseRow)

    var totalMax = Int.MIN_VALUE
    instructions.map{ instruction ->
        var result = 0
        if (registers.containsKey(instruction.registry)) {
            result = registers[instruction.registry]!!
        }

        if (checkCondition(instruction, registers)) {
            if (instruction.inc) {
                result += instruction.value
            } else {
                result -= instruction.value
            }
        }
        registers[instruction.registry] = result
        totalMax = max(result, totalMax)
    }

    registers.entries.forEach{ entry ->
        println("${entry.key} ${entry.value}")
    }
    println("Largest: ${registers.values.max()}")
    println("TotalMax: $totalMax")
}

fun checkCondition(instruction: Instruction, registers: Map<String, Int>): Boolean {
    var registryValue = 0
    if (registers.containsKey(instruction.conditionRegistry)) {
     registryValue = registers[instruction.conditionRegistry]!!
    }
    when (instruction.conditionOperator) {
        "<" -> return registryValue < instruction.conditionConstant
        ">" -> return registryValue > instruction.conditionConstant
        ">=" -> return registryValue >= instruction.conditionConstant
        "==" -> return registryValue == instruction.conditionConstant
        "<=" -> return registryValue <= instruction.conditionConstant
        "!=" -> return registryValue != instruction.conditionConstant
    }
    return false
}

fun parseRow(row: String): Instruction {
    val parts= row.split(" ")
    return Instruction(
            registry = parts[0],
            inc = "inc" == parts[1],
            value = parts[2].toInt(),
            conditionRegistry = parts[4],
            conditionOperator = parts[5],
            conditionConstant = parts[6].toInt())
}

