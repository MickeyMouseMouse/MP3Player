import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import jaco.mp3.player.MP3Player // http://jacomp3player.sourceforge.net/guide.html
import javafx.scene.canvas.Canvas
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
    val labelInfo = Label()
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
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("mp3 files", "*.mp3"))
        val file = fileChooser.showOpenDialog(Stage())

        if (file != null) activatePlayer(file)
    }

    fun activatePlayer(file : File) {
        closePlayer()
        statusPlayer = false
        buttonStartStop.isDisable = false
        labelInfo.text = file.name

        player.playList.clear()
        player.addToPlayList(file)

        startStop()
    }

    fun closePlayer() { player.stop() }
}
