package aoc2015.day18

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.stage.Stage
import kotlinx.coroutines.*

/**
 * Created by Hans Markwart on 24.03.2019.
 */
object Helper {

    /**
     * sets the min size of the [stage] to the first calculated size that is set to it
     */
    fun setMinSizeToFirstChildSize(stage: Stage) {
        with(stage) {
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
}