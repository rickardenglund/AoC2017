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
        return data.substring(row*width, (row+1)*width)
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

        val matches = mutableListOf<Canvas>()
        for (row in 0 until chunksPerRow) {
            for (col in 0 until chunksPerRow) {
                var pos = row*chunksPerRow + col
                pos *= templateWidth
                matches.add(patterns.first { matches(pos, it) }.target)
            }
        }


        setData(matches.merge(chunksPerRow))
    }

    fun matches(pos: Int, pattern: Pattern): Boolean {
        val result = true
        val patternWidth = pattern.template.width
        for (row in 0 until patternWidth) {
            val start = pos + row*width
            val partOfRow = data.substring(start, start + patternWidth)
            if (pattern.template.getRow(row) != partOfRow)
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