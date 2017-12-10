package day10

val salt = listOf(17, 31, 73, 47, 23)

fun main(args: Array<String>) {
    val lengths = "206,63,255,131,65,80,238,157,254,24,133,2,16,0,1,3".toAsciiList()
    lengths.addAll(salt)

    val sparseHash = sparseHash(lengths, 256)
    println(sparseHash.sparseToDense())
}

fun sparseHash(lengths: List<Int>, size: Int): List<Int> {
    val list = (0 until size).toMutableList()
    var i = 0
    var skipSize = 0

    for (round in 1..64) {
        for (hashLength in lengths) {
            list.reverse(i, (i+hashLength)%list.size, hashLength)

            i = (i + skipSize + hashLength)%list.size
            skipSize++
        }
    }
    return list
}

fun MutableList<Int>.reverse(start: Int, stop: Int, length: Int) {
    val reversed = this.sublistWrapping(start, stop, length).asReversed()

    if (reversed.size != length) {
        throw Exception("Failed sublist: $reversed")
    }
    var iCopy = start
    for (x in reversed) {
        this[iCopy] = x
        iCopy = (iCopy + 1)%this.size
    }

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
    if (stop <= start && length > 0) {
        val longList = this.toMutableList()
        longList.addAll(this.subList(0, stop))
        return longList.subList(start, longList.size)
    }

    return this.subList(start, stop).toMutableList()
}

fun String.toAsciiList() : MutableList<Int> {
    return this.map { it.toInt()}.toMutableList()
}

fun <T>List<T>.print():String {
    return this.map { it.toString().padStart(3) }.toString()
}