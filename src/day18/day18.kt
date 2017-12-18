package day18

import util.getInput

class Program() {
    var pc = 0
    private val registers = hashMapOf<String, Long>()

    fun get(str: String): Long {
        try {
            return str.toLong()
        } catch (e: NumberFormatException) {}
        return registers[str] ?: 0
    }

    fun set(registry: String, value: Long) {
        registers[registry] = value
    }

    fun print() {
        println("###")
        registers.entries.forEach(::println)
        println()
    }

    fun jump(steps: Int) {
        pc += steps - 1
    }
}


fun main(args: Array<String>) {
    val program = Program()
    val code = getProgram().map { parse(it, program) }


    while (program.pc < code.size) {
        code[program.pc]()
        program.pc++
    }
}

fun getProgram():List<String> {
    return getInput("E:\\rickard\\Documents\\Advent of code\\src\\day18\\input",{a -> a})
}



fun parse(cmdString: String, program: Program): () -> Unit {
    val parts = cmdString.split(" ")
    println(parts)
    val cmd =  when (parts[0]) {
        "snd" -> {{println(parts); program.set("playing",program.get(parts[1]))}}
        "set" -> {{println(parts);  program.set(parts[1], program.get(parts[2]))}}
        "add" -> {{
            println(parts)
            program.set(parts[1], program.get(parts[1]) + program.get(parts[2]))
        }}
        "mul" -> {{
            println(parts)
            program.set(parts[1], program.get(parts[1]) * program.get(parts[2]))
        }}
        "mod" -> {{
            println(parts)
            program.set(parts[1], program.get(parts[1]) % program.get(parts[2]))
        }}
        "rcv" -> {{
            println(parts)
            if (program.get(parts[1]) != 0.toLong()) {
                println("Done: ${program.get("playing")}")
                throw Exception("Done")
            }
        }}
        "jgz" -> {{
            println(parts)
            if (program.get(parts[1]) > 0) {
                program.jump(parts[2].toInt())
            }
        }}

        else -> {{print("Failed to parse: "); println(parts); }}
    }
    return cmd
}

