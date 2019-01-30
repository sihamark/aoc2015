package day10

const val input = "1321131112"
const val times = 50

fun main() {
    var current = input
    (1..times).forEach { i ->
        val result = current.lookAndSay()
        current = result
    }

    println("result is ${current.length} long")
}

fun String.lookAndSay(): String {
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