package aoc2015.day18

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import kotlinx.coroutines.*
import tornadofx.Controller
import kotlin.coroutines.CoroutineContext

class GridController : Controller(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private var grid = Day18.inputGrid
    private val gridProperties = Grid.positions.associateWith { SimpleBooleanProperty(grid[it]) }

    val currentlyActive = SimpleIntegerProperty(grid.amountOfTurnedOnLights())

    val speed = SimpleDoubleProperty(0.5)

    private var job = Job().apply { cancel() }

    fun lightAt(x: Int, y: Int): SimpleBooleanProperty {
        val position = Position(x, y)
        return gridProperties[position] ?: error("no light found for position $position")
    }

    fun play() {
        job.cancel()
        job = launch(Dispatchers.Default) {
            while (isActive) {
                delay((10 + (1.0 - speed.get()) * 500).toLong())
                incrementGrid()
            }
        }
    }

    fun pause() {
        job.cancel()
    }

    private fun incrementGrid() {
        grid++
        Grid.positions.forEach { pos ->
            gridProperties[pos]?.set(grid[pos])
        }
        launch(Dispatchers.Main) {
            currentlyActive.set(grid.amountOfTurnedOnLights())
        }
    }
}