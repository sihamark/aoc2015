package day9

import utility.allPermutations

fun main() {
    val distances = input.map { Parser.parse(it) }
    val map = distances
            .associate { it.coordinates to it.distance }
            .let { it.toRouteMap() }
    val allDestinations = distances
            .flatMap { it.coordinates.places }
            .distinct()
            .also { println(it) }

    val allRoutes = allDestinations.allPermutations()
            .map { it.toRoute() }

    val minDistance = allRoutes
            .map { map.distance(it) }
            .max()

    println("found ${allRoutes.size} permutations")
    println("maximal distance is: $minDistance")
}

object Parser {
    private val validCommand = Regex("(\\w+) to (\\w+) = (\\d+)")

    fun parse(rawDistance: String): Distance {
        val parsedValues = validCommand.find(rawDistance)?.groupValues ?: error("invalid raw command: $rawDistance")

        return Distance(
                Coordinates(
                        parsedValues[1],
                        parsedValues[2]
                ),
                parsedValues[3].toInt()
        )
    }
}

class Coordinates private constructor(
        val places: Set<String>
) {

    constructor(from: String, to: String) : this(setOf(from, to))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinates

        if (places != other.places) return false

        return true
    }

    override fun hashCode(): Int {
        return places.hashCode()
    }
}

data class Route(private val places: List<String>) : Iterable<Coordinates> {

    init {
        if (places.size < 2) error("must contain at least 2 places")
    }

    override fun iterator() = object : Iterator<Coordinates> {

        private var current = 0
        private val last = places.size - 2

        override fun hasNext() = current <= last

        override fun next() = Coordinates(places[current], places[current + 1]).also {
            current++
        }
    }
}

data class RouteMap(private val distances: Map<Coordinates, Int>) {
    fun distance(route: Route) = route.mapNotNull { distances[it] }.sum()
}

data class Distance(
        val coordinates: Coordinates,
        val distance: Int
)

fun List<String>.toRoute() = Route(this)
fun Map<Coordinates, Int>.toRouteMap() = RouteMap(this)

