package day18

import util.getInput


val registers = hashMapOf<String, Int>()


fun main(args: Array<String>) {
    val program = getProgram().map { parse(it, registers) }
    registers["pc"] = 0
    registers["playing"] = 0

    while (registers["pc"]!! < program.size) {
        program[registers["pc"]!!]()
        registers.print()
        registers["pc"] = registers["pc"]!! + 1
    }
}

fun getProgram():List<String> {
    return getInput("E:\\rickard\\Documents\\Advent of code\\src\\day18\\input",{a -> a})
}

fun HashMap<String, Int>.print() {
    println("###")
    this.entries.forEach(::println)
    println()
}

fun parse(cmd: String, registers: HashMap<String, Int>): () -> Unit {
    val parts = cmd.split(" ")
    println(parts)
    val cmd =  when (parts[0]) {
        "snd" -> {{println(parts); registers["playing"] = getOperand(registers,parts[1])}}
        "set" -> {{println(parts);  registers[parts[1]] = getOperand(registers,parts[2])}}
        "add" -> {{println(parts); registers[parts[1]] = getOperand(registers,parts[1]) + getOperand(registers,parts[2])}}
        "mul" -> {{println(parts); registers[parts[1]] = getOperand(registers,parts[1]) * getOperand(registers,parts[2])}}
        "mod" -> {{println(parts); registers[parts[1]] = getOperand(registers,parts[1]) % getOperand(registers,parts[2])}}
        "rcv" -> {{println(parts);  if (registers[parts[1]]!! != 0) {println("Done: ${registers["playing"]}");throw Exception("Done ")}}}//registers[parts[1]] = registers["playing"]!!}}
        "jgz" -> {{println(parts); if (registers[parts[1]]!! > 0) registers["pc"] = registers["pc"]!! + parts[2].toInt() - 1}}

        else -> {{print("Failed to parse: "); println(parts); }}
    }
    return cmd
}

fun getOperand(registers: HashMap<String, Int>, str: String): Int {
    try {
        return str.toInt()
    } catch (e: NumberFormatException) {}

    val value = registers[str]
    return if (value != null) value!!
    else 0
}