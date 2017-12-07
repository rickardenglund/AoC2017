package day6


fun main(args: Array<String>) {
    var input = listOf(14,0,15,12,11,11,3,5,1,6,8,4,9,1,8,4).toMutableList()
    var testInput = listOf(0,2,7,0).toMutableList()

    loop(input)
    loop(input)
    loop(input)
}

fun loop(input : MutableList<Int>) {
    var visitedStates = listOf<List<Int>>().toMutableList()

    while (!visitedStates.contains(input)) {
        visitedStates.add(input.toMutableList())
        redistribute(input)
    }
    println("VisitedStates: ${visitedStates.size}")
}

fun redistribute(input: MutableList<Int>): List<Int>{
    val largestBank = getLargestBank(input)
    var remaining = input[largestBank]
    input[largestBank]=0
    var currentBank = largestBank
    while (remaining > 0) {
        currentBank = (currentBank+1)%input.size
        input[currentBank]++
        remaining--
    }
    return input
}

fun getLargestBank(input: List<Int>): Int {
    var maxSize = Int.MIN_VALUE
    var maxIndex = -1
    for (i in 0 until input.size) {
        if (input[i] > maxSize) {
            maxSize = input[i]
            maxIndex = i
        }
    }
    return maxIndex
}
