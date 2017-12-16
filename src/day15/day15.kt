package day15

import java.math.BigInteger


fun main(args: Array<String>) {
    val genA = Generator(BigInteger.valueOf(699),BigInteger.valueOf(16807), BigInteger.valueOf(4))
    val genB = Generator(BigInteger.valueOf(124),BigInteger.valueOf(48271), BigInteger.valueOf(8))
    var score = 0
    for (i in 1..5_000_000) {
        if (genA.get() == genB.get()) score++

        genA.next()
        genB.next()
    }
    println(score)
}

class Generator(var value: BigInteger, val factor: BigInteger, val multiple: BigInteger) {

    private val divider = BigInteger.valueOf(2147483647)

    fun get(): String? {
        var str = Integer.toBinaryString(value.toInt())!!.padStart(16, '0')
        str = str.substring(str.length-16)
        return str
    }

    fun next(): BigInteger {
        do {
            value = (value * factor) % divider
        } while (value%multiple != BigInteger.ZERO)
        return value
    }
}
