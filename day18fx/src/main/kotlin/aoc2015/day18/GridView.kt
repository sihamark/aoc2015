package aoc2015.day18

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*


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
        val lights = mutableMapOf<Position, SimpleBooleanProperty>()
        gridpane {
            (0 until Grid.MAX_HEIGHT).forEach { y ->
                row {
                    (0 until Grid.MAX_WIDTH).forEach { x ->
                        add(LightView().apply {
                            lights[Position(x, y)] = isLightOnProperty
                        })
                    }
                }
            }
        }
    }

    class LightView : View() {

        val isLightOnProperty = SimpleBooleanProperty(false)

        override val root = pane {
            setPrefSize(2.0, 2.0)

            backgroundProperty().bind(Bindings.`when`(isLightOnProperty)
                    .then(Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)))
                    .otherwise(Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))))
        }
    }
}

class GridController : Controller() {
    val grid = Day18.inputGrid
    val gridProperties = (0 until Grid.MAX_WIDTH)
            .flatMap { x ->
                (0 until Grid.MAX_HEIGHT).map { y -> Position(x, y) }
            }

    fun play() {

    }

    fun pause() {

    }
}