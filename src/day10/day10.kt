package day10

val realLengths = listOf(206,63,255,131,65,80,238,157,254,24,133,2,16,0,1,3)
val testLengths = listOf(3, 4, 1, 5)


val appendList = listOf(17, 31, 73, 47, 23)

fun main(args: Array<String>) {

//    println(listOf(64, 7, 255).toHex())
    val list = listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22, 65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22,65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22)
    println(list.sparseToDense())

//    hash(listOf(10,10,10,10), 10)
//    hash(appendList, 256)
}

fun hash(lengths: List<Int>, size: Int) {
    val list = (0 until size).toMutableList()
    var i = 0
    var skipSize = 0
    for (hashLength in lengths) {
        println("skip: ${skipSize.pad(2)} i : ${i.pad(3)} Length: ${hashLength.pad(3)} | ${list.print()}")
        val reversed = list.sublistWrapping(i, (i+hashLength)%list.size, hashLength).asReversed()

        if (reversed.size != hashLength) {
            println("Failed sublist: $reversed")
            return
        }
//        println("Reversed: $reversed")
        var iCopy = i
        for (x in reversed) {
            list[iCopy] = x
            iCopy = (iCopy + 1)%list.size
        }

        i = (i + skipSize + hashLength)%list.size
        skipSize++
    }

    println(list)
    println("Result: ${list[0]} * ${list[1]} = ${list[0]*list[1]}")
}

fun Int.pad(width: Int):String {
    return this.toString().padEnd(width, ' ')
}


fun List<Int>.sparseToDense(): String {
    if (this.size != 256) throw Exception("Invalid sparse hash size: ${this.size}")

    var i = 0
    val result = mutableListOf<Int>()
    while (i < 255) {
        result.add(this.subList(i, i+16).xor())
        i+= 16
    }
    return result.toHex()
}

fun List<Int>.toHex() : String {
    return this.fold("", {acc, el -> acc + java.lang.Integer.toHexString(el).padStart(2,'0')})
}

fun List<Int>.xor(): Int {
    return this.fold(0, {acc, el -> acc xor el})
}

fun <T>List<T>.sublistWrapping(start:  Int, stop:Int, length: Int): List<T> {
//    println("$start $stop $length")
    if (stop <= start && length > 0) {
        val longList = this.toMutableList()
        longList.addAll(this.subList(0, stop))
        return longList.subList(start, longList.size)
    }

    return this.subList(start, stop).toMutableList()
}

fun <T>List<T>.print():String {
    return this.map { it.toString().padStart(3) }.toString()
}