package aoc2015.day18

import javafx.beans.binding.When
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.paint.Color
import kotlinx.coroutines.*
import tornadofx.*
import kotlin.coroutines.CoroutineContext


class GridView : View() {

    private val controller: GridController by inject()

    override val root = vbox {
        hbox {
            button("play") {
                action { controller.play() }
            }
            button("pause") {
                action { controller.pause() }
            }
        }
        gridpane {
            (0 until Grid.MAX_HEIGHT).forEach { y ->
                row {
                    (0 until Grid.MAX_WIDTH).forEach { x ->
                        add(LightView(controller.gridProperties[Position(x, y)]
                                ?: kotlin.error("no property found")))
                    }
                }
            }
        }
    }

    class LightView(
            private val isLightOnProperty: SimpleBooleanProperty = SimpleBooleanProperty(false)
    ) : View() {

        override val root = pane {
            setMinSize(1.0, 1.0)

            backgroundProperty().bind(
                    When(isLightOnProperty)
                            .then(onBackground)
                            .otherwise(offBackground)
            )
        }

        companion object {
            private val onBackground = Color.BLACK.asBackground()
            private val offBackground = Color.WHITE.asBackground()
        }
    }
}

class GridController : Controller(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private var grid = Day18.inputGrid
    val gridProperties = Grid.positions().associateWith { SimpleBooleanProperty(grid[it]) }

    private var job = Job().apply { cancel() }

    fun play() {
        job.cancel()
        job = launch(Dispatchers.Default) {
            while (isActive) {
                delay(DELAY)
                incrementGrid()
            }
        }
    }

    fun pause() {
        job.cancel()
    }

    private fun incrementGrid() {
        grid++
        Grid.positions().forEach { pos ->
            gridProperties[pos]?.set(grid[pos])
        }
    }

    companion object {
        private const val DELAY = 100L
    }
}
