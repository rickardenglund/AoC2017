package day9

import input.getInput


fun main(args: Array<String>){
    testGarbage()
}

fun testGarbage() {
    getInput("E:\\rickard\\Documents\\Advent of code\\src\\day9\\garbage",
            { row ->
                if (row.length - parseGarbageSize(row).length != 0) println("TestGarbage Failed: $row")
            })

    getInput("E:\\rickard\\Documents\\Advent of code\\src\\day9\\dirtyStrings",
            { row ->
                val cleanRow = clean(row)
                if (cleanRow.any {it != '{' && it != '}' }) println("Failed to clean $row | $cleanRow")
            })

    println("#############################")
    getInput("E:\\rickard\\Documents\\Advent of code\\src\\day9\\gbTest",
            { row ->
                clean(row)})



    var totalScore = 0
    getInput("E:\\rickard\\Documents\\Advent of code\\src\\day9\\testInput",
            { row ->
                println("###################################################")
                val cleanRow = clean(row)
                val score = count(cleanRow, 1)
                totalScore += score
                println("Row: $score")// \n\t$cleanRow \n\t$row")
//                println()
            })
    println("TotalScore: $totalScore")
}

fun count(str: String, level: Int):Int {
    if (str == "{}") return level

    var result = 0
    var i = 0
    while (i < str.length) {
        val nextStop = findMatching(str.substring(i))
        val substr = str.substring(i, i+nextStop+1)
//        println("apa: $substr")
        result += level + count(substr.substring(1,substr.length-1), level+1)
        i += nextStop
        i++
    }
    return result
}

fun findMatching(str: String) : Int {
//    println("Searching: $str")
    var diff = 1
    var i = 1
    while (diff >0) {
        diff += when (str[i]) {
            '{' -> 1
            '}' -> -1
            else -> throw Exception("Dirty $str")
        }
        i++
    }
    return i-1
}

fun clean(str: String) : String {
    var result = ""
    var totalGb = 0
    var i = 0
    while (i < str.length) {
        if (str[i] == '<') {
            val gbResult = parseGarbageSize(str.substring(i))
            i += gbResult.length -1
            totalGb += gbResult.size
        } else {
            result += str[i]
        }
        i++
    }
    println("TotalGb: $totalGb")
    return result.filter { it != ',' }
}

data class GbResult(val length: Int, val size: Int)
fun parseGarbageSize(str: String): GbResult {
    var result = ""
    var gbSize = 0
    var i = 0
    while (i < str.length) {
        result += str[i]
        if (str[i] == '>') break
        if (str[i] == '!') {result+=str[i]; i++; gbSize--}
        i++
        gbSize++
    }
//    println(result)
    return GbResult(result.length, gbSize-1)
}

