package aoc2015.day18

import javafx.beans.binding.Bindings
import javafx.beans.binding.When
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.Label
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.scene.layout.RowConstraints
import javafx.scene.paint.Color
import tornadofx.*


class GridView : View() {

    private val controller: GridController by inject()

    private var grid: GridPane by singleAssign()

    private var widthLabel: Label by singleAssign()
    private var heightLabel: Label by singleAssign()

    override val root = borderpane {
        top {
            hbox {
                button("play") {
                    action { controller.play() }
                }
                button("pause") {
                    action { controller.pause() }
                }
            }
        }
        center {
            grid = gridpane {
                background = Color.ALICEBLUE.asBackground()
                Grid.columns.forEach { y ->
                    row {
                        Grid.rows.forEach { x ->
                            add(LightView(controller.lightAt(x, y)))
                        }
                    }
                }
                Grid.columns.forEach {
                    columnConstraints.add(ColumnConstraints().apply {
                        hgrow = Priority.ALWAYS

                    })
                }
                Grid.rows.forEach {
                    rowConstraints.add(RowConstraints().apply {
                        vgrow = Priority.ALWAYS
                    })
                }
            }
        }
        bottom {
            hbox {
                widthLabel = label()
                heightLabel = label()
            }
        }
    }

    init {
        currentStage?.apply {
            minWidth = 200.0
            minHeight = 200.0
        }
        widthLabel.textProperty().bind(Bindings.selectString(currentStage?.widthProperty()))
        heightLabel.textProperty().bind(Bindings.selectString(currentStage?.heightProperty()))

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

