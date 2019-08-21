import javafx.scene.control.Label
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import javafx.animation.AnimationTimer

class Ticker : Label() {
    private var str = StringBuilder()
    private var num = 0
    private var del = 0.0

    private var index = 0
    private var last: Long = -1
    private var direction = true // true = right, false = left
    private var status = false // false = no settings, true = pause

    private val timer = object: AnimationTimer() {
        override fun handle(now: Long) {
            if (now - last > del) {
                text = str.substring(index, index + num)

                if (direction) {
                    if (++index > str.length - num) {
                        index--
                        direction = false
                    }
                } else {
                    if (--index < 0) {
                        index++
                        direction = true
                    }
                }

                last = now
            }
        }
    }

    // 1 string for show
    // 2 number of visible characters in label (ticker)
    // 3 delay in seconds
    fun start(string: String?, number: Int, delay: Double) {
        if (string == null || number < 1 || delay < 0)
            throw IllegalArgumentException("Wrong settings")

        if (string.length <= number)
            text = string
        else {
            pause()

            index = 0
            direction = true
            status = true
            str = StringBuilder(" ")
            str.append(string).append(" ")
            num = number
            del = delay * 1000000000

            start()
        }
    }

    fun start() { if (status) timer.start() }

    fun pause() { timer.stop() }

    fun clear() {
        pause()

        text = ""
        status = false
    }
}