package util

import day17.nIterations
import java.awt.*
import java.lang.System.currentTimeMillis
import javax.swing.*

val format = {num: Float -> "%.1f".format(num)}

private fun progressText(currentIteration: Int, totalIterations: Int, batchSize: Int, stackTime: Long): String {
    val seconds = estimateMillisRemaining(totalIterations, batchSize, stackTime) / 1000
    val min = seconds / 60
    val h = seconds / 60 / 60
    val percentageDone = (currentIteration.toFloat() / totalIterations.toFloat()) * 100

    return "${format(percentageDone)}% " +
                    "batchTime: $stackTime " +
                    "batchSize: $batchSize " +
                    "Estimate: ${h % (60 * 60)}h ${min % 60}m ${seconds % 60}s"
}

private fun estimateMillisRemaining(totalIterations: Int, batchSize: Int, batchTime: Long): Long {
    val neededIterations = totalIterations / batchSize
    return neededIterations * batchTime
}

class ProgressReporter(val totalIterations: Int) {

    var currentIteration = 0
    var lastTimePrinted = currentTimeMillis()
    var lastReportedIteration = 0
    val progressFrame =  ProgressFrame(totalIterations)

    fun iterationDone() {
        currentIteration++
        val newTime = currentTimeMillis()

        if (newTime - lastTimePrinted > 1000) {
            val currentBatchSize = currentIteration - lastReportedIteration
            val currentBatchTime = newTime - lastTimePrinted
            val progressStr = progressText(
                    currentIteration,
                    totalIterations,
                    currentBatchSize,
                    currentBatchTime)
            println(progressStr)
            progressFrame.update(currentIteration, totalIterations, currentBatchSize, currentBatchTime, progressStr)
            lastTimePrinted = newTime
            lastReportedIteration = currentIteration
        }
    }
}

class ProgressFrame(nIterations: Int) : JFrame() {
    val progressBar = JProgressBar(0, nIterations)
    val graph = Graph(nIterations,1_000_000)
    init{
        title = "ProgressReport"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        contentPane.add(graph)

        progressBar.isStringPainted = true
        progressBar.value = progressBar.minimum
        contentPane.add(progressBar)

        pack()
        isLocationByPlatform = true
        isVisible = true
    }

    fun update(currentIteration: Int, totalIterations: Int, batchSize: Int, batchTime: Long, str: String) {
        progressBar.value = currentIteration
        progressBar.string = str
        val estimateMillis = estimateMillisRemaining(totalIterations, batchSize, batchTime)
        graph.addEstimate(currentIteration, estimateMillis)
    }
}

class Graph(val totalIterations: Int, val maxTime: Long): JPanel() {
    private val PREF_W = 600
    private val PREF_H = 400
    private val points = mutableListOf<Pair<Int, Long>>()
    fun addEstimate(currentIteration: Int, estimate: Long) {
        points.add(Pair(currentIteration, estimate))
        repaint()
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2 = g!! as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        for (i in 0 until points.size -1) {
            val p1 = getPoint(i)
            val p2 = getPoint(i+1)
            g2.drawLine(p1.x, p1.y, p2.x, p2.y)
        }
    }

    private fun getPoint(i: Int): Point {
        val x = (points[i].first*(width.toFloat()/ totalIterations)).toInt()
        val y = (points[i].second*(height.toFloat()/maxTime)).toInt()
        return Point(x,height - y)
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(PREF_W, PREF_H)
    }
}