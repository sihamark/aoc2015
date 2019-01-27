package aoc2015

class Starter(private val partStarter: PartStarter) {

    fun start(args: Array<String>) {
        if (args.isEmpty()) {
            printPart1()
            printPart2()
        } else {
            when (args[0]) {
                "part1" -> printPart1()
                "part2" -> printPart2()
            }
        }
    }

    private fun printPart1() {
        println("part 1: ${partStarter.doPart1()}")
    }

    private fun printPart2() {
        println("part 2: ${partStarter.doPart2()}")
    }

    interface PartStarter {
        fun doPart1(): String
        fun doPart2(): String
    }
}

fun prepare(part1: () -> String, part2: () -> String): Starter.PartStarter = object : Starter.PartStarter {
    override fun doPart1() = part1()
    override fun doPart2() = part2()
}

fun Starter.PartStarter.start(args: Array<String>) {
    Starter(this).start(args)
}