package aoc2015.day18

import javafx.beans.InvalidationListener
import javafx.beans.Observable
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
import kotlinx.coroutines.*
import tornadofx.*
import java.util.concurrent.Callable


class GridView : View() {

    private val controller: GridController by inject()

    override val root = borderpane {
        top {
            vbox {
                hbox {
                    alignment = Pos.CENTER
                    button("play") {
                        action { controller.play() }
                    }
                    button("pause") {
                        action { controller.pause() }
                    }
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
                            Callable { parent.width.floorToMultiple(Grid.MAX_HEIGHT) }, parent.heightProperty()))

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
                label {
                    textProperty().bind(controller.currentlyActive.asString("is active: %d"))
                }
            }
        }
    }

    init {
        currentStage?.apply {
            GlobalScope.launch {
                var initWidth = 0.0
                var initHeight = 0.0

                widthProperty().addListener(object : InvalidationListener {
                    override fun invalidated(observable: Observable?) {
                        if (width != Double.NaN && width != 0.0) {
                            initWidth = width
                            widthProperty().removeListener(this)
                        }
                    }
                })
                heightProperty().addListener(object : InvalidationListener {
                    override fun invalidated(observable: Observable?) {
                        if (height != Double.NaN && height != 0.0) {
                            initHeight = height
                            heightProperty().removeListener(this)
                        }
                    }
                })

                while (isActive && (initWidth == 0.0 || initHeight == 0.0)) {
                    yield()
                }

                launch(Dispatchers.Main) {
                    minWidth = initWidth
                    minHeight = initHeight
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

    companion object {
        private fun Double.floorToMultiple(of: Int) = ((this.toInt() / of) * of).toDouble()
    }
}

