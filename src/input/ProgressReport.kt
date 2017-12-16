package input

fun reportProgress(currentIteration: Int, totalIterations: Int, batchSize: Int, stackTime: Long) {
    val neededIterations = totalIterations / batchSize
    val seconds = (neededIterations * stackTime) / 1000
    val min = seconds / 60
    val h = seconds / 60 / 60

    println(
            "${(currentIteration.toFloat() / totalIterations.toFloat()) * 100}% " +
                    "batchTime: $stackTime " +
                    "batchSize: $batchSize " +
                    "Estimate: ${h % (60 * 60)}h ${min % 60}m ${seconds % 60}s")
}