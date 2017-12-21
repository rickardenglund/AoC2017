package day21

class Canvas(input: String) {
    var width: Int = 0
        private set
    private var data: String = ""

    init {
        setData(input)
    }

    private fun setData(str: String) {
        width = str.indexOf("/")
        data= str.replace("/", "")
    }


    fun getRow(row: Int, rotateClockwise: Int = 0): String {
        when (rotateClockwise) {
            0 -> return data.substring(row*width, (row+1)*width)
            2 -> return getRow(width - 1 - row).reversed()
            1 -> {
                var result = ""
                for (i in 0 until width) {
                    result += data[i * width + row]
                }
                return result.reversed()
            }
            3 -> return getRow(width - 1 - row, 1).reversed()
        }
        throw IllegalArgumentException("Invalid rotation value")
    }

    override fun toString(): String {
        var res = ""
        for (i in 0 until width) {
            res += getRow(i) + "\n"
        }
        return res
    }

    fun getRaw(): String {
        return data
                .chunked(width)
                .fold("", {acc, it-> acc + it + "/"})
                .trimEnd { it == '/' }
    }

    fun apply(patterns: List<Pattern>) {
        val templateWidth = patterns[0].template.width
        val chunksPerRow = width / templateWidth

        val matches = (0 until chunksPerRow).flatMap { row ->
            (0 until chunksPerRow).map { col ->
                var pos = row*width + col
                pos *= templateWidth
                patterns.first { matches(pos, it) }.target
            }
        }

        setData(matches.merge(chunksPerRow))
    }

    fun matches(pos: Int, pattern: Pattern): Boolean {
        return (0..3).any { matches(pos,pattern, it) }
                || (0..3).any { matches(pos,pattern, it, true) }
    }

    private fun matches(pos: Int, pattern: Pattern, rotation: Int, flip: Boolean = false): Boolean {
        val result = true
        val patternWidth = pattern.template.width
        for (row in 0 until patternWidth) {
            val start = pos + row*width
            var partOfRow = data.substring(start, start + patternWidth)
            if (flip) partOfRow = partOfRow.reversed()
            if (pattern.template.getRow(row, rotation) != partOfRow)
                return false
        }
        return result
    }
}

fun List<Canvas>.merge(chunksPerRow: Int): String {
    var newCanvas = ""
    val targetWidth = this[0].width
    this.chunked(chunksPerRow).forEach { canvasList ->
        for (row in 0 until targetWidth) {
            for (canvasIndex in 0 until chunksPerRow) {
                newCanvas += canvasList[canvasIndex].getRow(row)
            }
            newCanvas += "/"
        }
    }
    return newCanvas
}