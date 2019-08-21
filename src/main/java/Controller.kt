import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import jaco.mp3.player.MP3Player // http://jacomp3player.sourceforge.net/guide.html
import javafx.scene.canvas.Canvas
import javafx.scene.control.Tooltip
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File

/*
    How to add external library to project?
        File->Project Structure...->Modules->Dependencies->icon plus->JARs or directories...
        Set check the box opposite the library
        Go to Artifacts; below "Available Elements" double click on the library
*/

class Controller {
    val buttonOpenFile = Button("Open Audio File")
    val buttonStartStop = Button()
    val ticker = Ticker()
    val background = Canvas(315.0, 134.0)

    private var statusPlayer = false // false = off, true = on

    val player = MP3Player()
    fun startStop() {
        if (statusPlayer) {
            buttonStartStop.graphicProperty().value = ImageView(Image("startImage.png"))
            statusPlayer = false
            player.pause()
        } else {
            buttonStartStop.graphicProperty().value = ImageView(Image("stopImage.png"))
            statusPlayer = true
            player.play()
        }
    }

    fun chooseFile() {
        val fileChooser = FileChooser()
        fileChooser.title = "Open Audio File"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Audio", "*.mp3"))
        val file = fileChooser.showOpenDialog(Stage())

        if (file != null) activatePlayer(file)
    }

    fun activatePlayer(file : File) {
        closePlayer()
        statusPlayer = false
        buttonStartStop.isDisable = false
        ticker.start(file.name, 25, 0.6)
        ticker.tooltip = Tooltip(file.name)

        player.playList.clear()
        player.addToPlayList(file)

        startStop()
    }

    fun closePlayer() { player.stop() }
}
