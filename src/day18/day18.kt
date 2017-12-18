package day18

import util.getInput

fun main(args: Array<String>) {
    val p1 = Program(0)
    val p2 = Program(1)
    val code1 = getProgram().map { parse(it, p1, p2) }
    val code2 = getProgram().map { parse(it, p2, p1) }

    while (p1.pc < code1.size && p2.pc < code2.size) {
        code1[p1.pc]()
        code2[p2.pc]()
        println("0 sent: ${p1.sentValues} 1 sent: ${p2.sentValues}")
    }
}

fun getProgram():List<String> {
    return getInput("E:\\rickard\\Documents\\Advent of code\\src\\day18\\input",{a -> a})
}

fun parse(cmdString: String, program: Program, otherProgram: Program): () -> Unit {
    val parts = cmdString.split(" ")
    println(parts)
    val cmd =  when (parts[0]) {
        "snd" -> {{
            println(parts)
            program.send(otherProgram, program.get(parts[1]))
        }}
        "rcv" -> {{
            println(parts)
            program.receive(parts[1])
        }}
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
        "jgz" -> {{
            println(parts)
            program.jgz(parts[1], parts[2])
        }}

        else -> {{print("Failed to parse: "); println(parts); }}
    }
    return cmd
}

