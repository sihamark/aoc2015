package aoc2015.day7

/**
 * Created by Hans Markwart on 29.01.2019.
 */
@ExperimentalUnsignedTypes
object Day7 {

    fun valueForWireA(): Int {
        val commands = input.map { Parser.parse(it) }
                .associate { it.result to it.expression }

        return Solver(commands).solveFor("a").toInt()
    }

    private object Parser {
        //                                1     2                      3        4        5     6           7        8        9        10           11
        private const val rawValidCommand = """(\w+) (AND|OR|RSHIFT|LSHIFT) (\w+) -> ([a-z]+)|(NOT) ([a-z]+) -> ([a-z]+)|(\d+) -> ([a-z]+)|([a-z]+) -> ([a-z]+)"""
        private val validCommand = Regex(rawValidCommand)
        private val validVariableName = Regex("""[a-z]+""")

        fun parse(rawCommand: String): Command {
            val matches = validCommand.find(rawCommand)?.groupValues ?: error("invalid raw command: $rawCommand")

            when {
                matches[2] == "AND" -> return and(matches[1], matches[3], matches[4])
                matches[2] == "OR" -> return or(matches[1], matches[3], matches[4])
                matches[2] == "RSHIFT" -> return or(matches[1], matches[3], matches[4])
                matches[2] == "LSHIFT" -> return or(matches[1], matches[3], matches[4])
            }

            if (matches[5] == "NOT") {
                return not(matches[6], matches[7])
            }

            if (matches[8].isNotEmpty()) {
                return setDiscrete(matches[8], matches[9])
            }

            if (matches[10].isNotEmpty()) {
                return setVariable(matches[10], matches[11])
            }

            TODO("could not parse: $rawCommand $matches")
        }

        private fun setVariable(value: String, result: String) = Command(
                result.toVariable(),
                value.toVariable()
        )

        private fun setDiscrete(value: String, result: String) = Command(
                result.toVariable(),
                value.toDiscrete()
        )

        private fun not(value: String, result: String) = Command(
                result.toVariable(),
                Expression.Not(value.toVariable())
        )

        private fun rshift(left: String, right: String, result: String) = Command(
                result.toVariable(),
                Expression.Rshift(left.toDiscreteOrVariable(), right.toDiscrete())
        )

        private fun lshift(left: String, right: String, result: String) = Command(
                result.toVariable(),
                Expression.Lshift(left.toDiscreteOrVariable(), right.toDiscrete())
        )

        private fun and(left: String, right: String, result: String) = Command(
                result.toVariable(),
                Expression.And(left.toDiscreteOrVariable(), right.toDiscreteOrVariable())
        )

        private fun or(left: String, right: String, result: String) = Command(
                result.toVariable(),
                Expression.Or(left.toDiscreteOrVariable(), right.toDiscreteOrVariable())
        )

        private fun String.toDiscrete(): Expression.DiscreteNumber = Expression.DiscreteNumber(this.toUShort())
        private fun String.toVariable(): Expression.Variable = Expression.Variable(this).also {
            assert(this.matches(validVariableName))
        }

        private fun String.toDiscreteOrVariable(): Expression {
            val numValue = this.toUShortOrNull()
            return if (numValue == null) Expression.Variable(this) else Expression.DiscreteNumber(numValue)
        }
    }

    private data class Command(
            val result: Expression.Variable,
            val expression: Expression
    )

    private sealed class Expression {
        data class DiscreteNumber(val value: UShort) : Expression()
        data class Variable(val variable: String) : Expression()
        data class And(val leftValue: Expression, val rightValue: Expression) : Expression()
        data class Or(val leftValue: Expression, val rightValue: Expression) : Expression()
        data class Rshift(val leftValue: Expression, val rightValue: DiscreteNumber) : Expression()
        data class Lshift(val leftValue: Expression, val rightValue: DiscreteNumber) : Expression()
        data class Not(val value: Expression) : Expression()
    }

    private class Solver(
            private val commands: Map<Expression.Variable, Expression>
    ) {

        fun solveFor(variable: String): UShort {
            val expression = commands[Expression.Variable(variable)] ?: error("dit not find expression for $variable")

            println("now solve $expression")

            return expression.solve()
        }

        private fun Expression.solve(): UShort = when (this) {
            is Expression.DiscreteNumber -> this.value
            is Expression.Variable -> commands[this]?.solve() ?: error("did not find expression for variable: $this")
            is Expression.And -> leftValue.solve() and rightValue.solve()
            is Expression.Or -> leftValue.solve() or rightValue.solve()
            is Expression.Not -> value.solve().inv()
            else -> TODO("implement solving for $this")
        }
    }
}