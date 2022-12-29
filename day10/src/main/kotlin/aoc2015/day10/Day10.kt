package aoc2015.day10

/**
 * Created by Hans Markwart on 31.01.2019.
 */
object Day10 {
    private const val input = "1321131112"

    fun lengthOfLookAndSayAfter40Times() = lookAndSay(40)
    fun lengthOfLookAndSayAfter50Times() = lookAndSay(50)

    private fun lookAndSay(times: Int): Int {
        var current = input
        (1..times).forEach {
            val result = current.lookAndSay()
            current = result
        }

        return current.length
    }

    private fun String.lookAndSay(): String {
        var latest = this.first()
        var count = 0

        val result = StringBuilder()

        var isAppended = false

        forEach {
            if (it != latest) {
                result.append("$count$latest")
                latest = it
                count = 1
                isAppended = true
            } else {
                count++
                isAppended = false
            }
        }

        if (isAppended) {
            result.append("$count$latest")
        }

        return result.toString()
    }
}