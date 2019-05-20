package aoc2015.day18

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import kotlinx.coroutines.*
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.setValue
import kotlin.coroutines.CoroutineContext

class GridController : Controller(), CoroutineScope {

    private var job = (Job() as Job).apply { cancel() }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private var grid = Day18.inputGrid
    private val gridProperties = Grid.positions.associateWith { SimpleBooleanProperty(grid[it]) }

    val currentlyActiveProperty = SimpleIntegerProperty(grid.amountOfTurnedOnLights())
    var currentlyActive by currentlyActiveProperty

    val stepProperty = SimpleIntegerProperty(0)
    var step by stepProperty

    val speedProperty = SimpleDoubleProperty(0.5)
    var speed by speedProperty

    val turnOnCornersProperty = SimpleBooleanProperty(false)
    var turnOnCorners by turnOnCornersProperty

    init {
        turnOnCornersProperty.addListener { _, _, _ ->
            if (turnOnCorners) {
                grid.turnOnCorners()
                setGridProperties()
            }
        }
    }

    fun lightAt(x: Int, y: Int): SimpleBooleanProperty {
        val position = Position(x, y)
        return gridProperties[position] ?: error("no light found for position $position")
    }

    fun play() {
        job.cancel()
        job = launch(Dispatchers.Default) {
            while (isActive) {
                delay((10 + (1.0 - speed) * 500).toLong())
                incrementGrid()
            }
        }
    }

    fun pause() {
        job.cancel()
    }

    fun reset() {
        job.cancel()

        grid = Day18.inputGrid
        if (turnOnCorners) {
            grid.turnOnCorners()
        }
        setGridProperties()
    }

    private fun incrementGrid() {
        grid++
        if (turnOnCorners) {
            grid.turnOnCorners()
        }
        launch(Dispatchers.Main) { step++ }
        setGridProperties()
    }

    private fun setGridProperties() {
        Grid.positions.forEach { pos ->
            gridProperties[pos]?.set(grid[pos])
        }
        launch(Dispatchers.Main) {
            currentlyActive = grid.amountOfTurnedOnLights()
        }
    }
}