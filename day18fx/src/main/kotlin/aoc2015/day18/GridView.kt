package aoc2015.day18

import javafx.beans.binding.Bindings
import javafx.beans.binding.When
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.control.Control
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.Priority
import javafx.scene.layout.RowConstraints
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import tornadofx.*
import java.util.concurrent.Callable


class GridView : View() {

    private val controller: GridController by inject()

    override val root = borderpane {
        right {
            vbox {
                alignment = Pos.CENTER
                spacing = 8.0
                padding = tornadofx.insets(8, 4)
                hbox {
                    alignment = Pos.CENTER
                    spacing = 8.0
                    button("play") {
                        action { controller.play() }
                    }
                    button("pause") {
                        action { controller.pause() }
                    }
                    button("reset") {
                        action { controller.reset() }
                    }
                }
                label("speed:")
                slider(0.0..1.0) {
                    prefWidth(50.0)
                    valueProperty().bindBidirectional(controller.speedProperty)
                }
                checkbox("turn on corners") {
                    selectedProperty().bindBidirectional(controller.turnOnCornersProperty)
                }
            }
        }
        center {
            stackpane {
                gridpane {
                    alignment = Pos.CENTER

                    val parent = parent as StackPane
                    setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE)

                    prefWidthProperty().bind(Bindings.createDoubleBinding(
                            Callable { parent.width.floorToMultiple(Grid.MAX_WIDTH) }, parent.widthProperty()))
                    prefHeightProperty().bind(Bindings.createDoubleBinding(
                            Callable { parent.height.floorToMultiple(Grid.MAX_HEIGHT) }, parent.heightProperty()))

                    Grid.columns.forEach { y ->
                        row {
                            Grid.rows.forEach { x ->
                                add(LightView(controller.lightAt(x, y)))
                            }
                        }
                    }
                    repeat(Grid.MAX_WIDTH) {
                        columnConstraints.add(ColumnConstraints().apply { hgrow = Priority.ALWAYS })
                    }
                    repeat(Grid.MAX_HEIGHT) {
                        rowConstraints.add(RowConstraints().apply { vgrow = Priority.ALWAYS })
                    }
                }
            }
        }
        bottom {
            hbox {
                padding = insets(2)
                label {
                    textProperty().bind(controller.currentlyActiveProperty.asString("is active: %d"))
                }
                region { hgrow = Priority.ALWAYS }
                label {
                    textProperty().bind(controller.stepProperty.asString("step: %d"))
                }
            }
        }
    }

    override fun onDock() {
        //make sure the window cannot become smaller as it is when it was first shown
        primaryStage.minWidth = primaryStage.width
        primaryStage.minHeight = primaryStage.height
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

    companion object {
        private fun Double.floorToMultiple(of: Int) = ((this.toInt() / of) * of).toDouble()
    }
}

