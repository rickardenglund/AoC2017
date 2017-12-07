package input

import java.io.File
import java.io.InputStream

fun <T>getInput(path: String, parseRow: (String) -> T): List<T> {
    val inputStream: InputStream = File(path).inputStream()
    val lineList = mutableListOf<T>()

    inputStream.bufferedReader().useLines { lines -> lines.forEach {
        lineList.add(parseRow(it))}
    }
    return lineList
}