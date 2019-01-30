package day7

private val validVariableName = Regex("""[a-z]+""")

fun main(args: Array<String>) {
    val commands = input.map { Parser.parse(it) }
            .associate { it.result to it.expression }
            .toMutableMap()

    println("what is on b = ${commands["b".toVariable()]}")

    val a = Solver(commands).solveFor("a")

    commands["b".toVariable()] = a.toDiscrete()

    Solver(commands).solveFor("a")
}

fun String.toVariable() = Expression.Variable(this).also {
    assert(this.matches(validVariableName))
}

fun UShort.toDiscrete() = Expression.Discrete(this)

sealed class Expression {
    data class Discrete(val value: UShort) : Expression()
    data class Variable(val name: String) : Expression()
    data class And(val leftValue: Expression, val rightValue: Expression) : Expression()
    data class Or(val leftValue: Expression, val rightValue: Expression) : Expression()
    data class RightShift(val leftValue: Expression, val rightValue: Discrete) : Expression()
    data class LeftShift(val leftValue: Expression, val rightValue: Discrete) : Expression()
    data class Not(val value: Expression) : Expression()
}

object Parser {
    private const val rawValidCommand =
    //      1     2                      3        4        5     6           7        8        9        10           11
            """(\w+) (AND|OR|RSHIFT|LSHIFT) (\w+) -> ([a-z]+)|(NOT) ([a-z]+) -> ([a-z]+)|(\d+) -> ([a-z]+)|([a-z]+) -> ([a-z]+)"""

    private val validCommand = Regex(rawValidCommand)

    fun parse(rawCommand: String): Command {
        val matches = validCommand.find(rawCommand)?.groupValues ?: error("invalid raw command: $rawCommand")

        when {
            matches[2] == "AND" -> return and(matches[1], matches[3], matches[4])
            matches[2] == "OR" -> return or(matches[1], matches[3], matches[4])
            matches[2] == "RSHIFT" -> return rightShift(matches[1], matches[3], matches[4])
            matches[2] == "LSHIFT" -> return leftShift(matches[1], matches[3], matches[4])
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

        error("unsupported command: $rawCommand")
    }

    private fun setVariable(value: String, result: String) = result.toVariable().fromExpression(value.toVariable())

    private fun setDiscrete(value: String, result: String) = result.toVariable().fromExpression(value.toDiscrete())

    private fun not(value: String, result: String) =
            result.toVariable().fromExpression(Expression.Not(value.toVariable()))

    private fun rightShift(left: String, right: String, result: String) =
            result.toVariable().fromExpression(Expression.RightShift(left.toDiscreteOrVariable(), right.toDiscrete()))

    private fun leftShift(left: String, right: String, result: String) =
            result.toVariable().fromExpression(Expression.LeftShift(left.toDiscreteOrVariable(), right.toDiscrete()))

    private fun and(left: String, right: String, result: String) =
            result.toVariable().fromExpression(Expression.And(left.toDiscreteOrVariable(), right.toDiscreteOrVariable()))

    private fun or(left: String, right: String, result: String) =
            result.toVariable().fromExpression(Expression.Or(left.toDiscreteOrVariable(), right.toDiscreteOrVariable()))

    private fun String.toDiscrete(): Expression.Discrete = this.toUShort().toDiscrete()

    private fun String.toDiscreteOrVariable(): Expression {
        val numValue = this.toUShortOrNull()
        return if (numValue == null) Expression.Variable(this) else Expression.Discrete(numValue)
    }

    private fun Expression.Variable.fromExpression(expression: Expression) = Command(this, expression)

    data class Command(
            val result: Expression.Variable,
            val expression: Expression
    )
}

class Solver(
        private val commands: Map<Expression.Variable, Expression>,
        private val logging: Boolean = false
) {

    private val values = mutableMapOf<Expression.Variable, Expression.Discrete>()

    private fun command(variable: Expression.Variable) =
            values[variable] ?: commands[variable] ?: error("did not find expression for variable: $this")

    fun solveFor(variable: String): UShort {
        val expression = commands[Expression.Variable(variable)] ?: error("dit not find expression for $variable")

        println("now solve for $variable")

        return expression.solve().also { result ->
            println("result: $result")
        }
    }

    private fun Expression.solve(): UShort {
        if (logging) println("solve $this")
        return when (this) {
            is Expression.Discrete -> this.value
            is Expression.Variable -> command(this).solve().also { values[this] = Expression.Discrete(it) }
            is Expression.And -> leftValue.solve() and rightValue.solve()
            is Expression.Or -> leftValue.solve() or rightValue.solve()
            is Expression.Not -> value.solve().inv()
            is Expression.RightShift -> (leftValue.solve().toUInt() shr rightValue.value.toInt()).toUShort()
            is Expression.LeftShift -> (leftValue.solve().toUInt() shl rightValue.value.toInt()).toUShort()
        }
    }
}