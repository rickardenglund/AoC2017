package util

val format = {num: Float -> "%.1f".format(num)}

fun reportProgress(currentIteration: Int, totalIterations: Int, batchSize: Int, stackTime: Long) {
    val neededIterations = totalIterations / batchSize
    val seconds = (neededIterations * stackTime) / 1000
    val min = seconds / 60
    val h = seconds / 60 / 60
    val percentageDone = (currentIteration.toFloat() / totalIterations.toFloat()) * 100

    println(
            "${format(percentageDone)}% " +
                    "batchTime: $stackTime " +
                    "batchSize: $batchSize " +
                    "Estimate: ${h % (60 * 60)}h ${min % 60}m ${seconds % 60}s")
}