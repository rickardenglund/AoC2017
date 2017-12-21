package day21

class Pattern(_pattern: String, _target: String) {
    val template: Canvas = Canvas(_pattern)
    val target: Canvas = Canvas(_target)

    override fun toString(): String {
        var res = "\n"
        for (row in 0 until target.width) {
            if (row < template.width) {
                res += "${template.getRow(row)}"
            } else res += " ".repeat(template.width)
            res += " | "
            res += "${target.getRow(row)}\n"
        }
        return res
    }
}