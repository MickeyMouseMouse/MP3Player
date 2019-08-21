import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.TransferMode

class GUI : Application() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(GUI::class.java)
        }
    }

    private val controller = Controller()
    private val simpleStyleOfButtonOpenFile = "-fx-padding:5 10 8 10;-fx-background-radius:20;-fx-text-fill:Black;-fx-font-size:25px;"
    private val hoverStyleOfButtonOpenFile = "-fx-background-color:linear-gradient(Purple 0%, SkyBlue 50%, SlateGray 100%);"

    override fun start(stage: Stage) {
        stage.icons.add(Image("icon.png"))

        stage.title = "MP3Player"
        stage.height = 134.0
        stage.width = 315.0
        stage.isResizable = false
        stage.centerOnScreen()

        controller.buttonOpenFile.layoutX = 15.0
        controller.buttonOpenFile.layoutY = 15.0
        controller.buttonOpenFile.graphicProperty().value = ImageView(Image("openImage.png"))
        controller.buttonOpenFile.style = simpleStyleOfButtonOpenFile

        controller.buttonStartStop.layoutX = 270.0
        controller.buttonStartStop.layoutY = 21.0
        controller.buttonStartStop.graphicProperty().value = ImageView(Image("startImage.png"))
        controller.buttonStartStop.style = "-fx-padding:0 0 0 0;-fx-background-radius:90;"
        controller.buttonStartStop.isDisable = true

        controller.ticker.layoutX = 15.0
        controller.ticker.layoutY = 75.0
        controller.ticker.style = "-fx-font-size:18px"

        val root = Group()
        root.children.add(controller.buttonOpenFile)
        root.children.add(controller.buttonStartStop)
        root.children.add(controller.ticker)
        stage.scene = Scene(root, 315.0, 134.0, Color.LIGHTBLUE)
        stage.show()

        controller.player.isRepeat = true

        // start DragAndDrop
        root.setOnDragOver {
            if (it.dragboard.hasFiles())
                it.acceptTransferModes(*TransferMode.ANY)
        }

        // event on drop new file
        root.setOnDragDropped {
            val files = it.dragboard.files

            if (files.size == 1 && Regex("(.*)\\.(mp3|MP3)").matches(files[0].name))
                controller.activatePlayer(files[0])
        }

        controller.buttonOpenFile.setOnMouseEntered { controller.buttonOpenFile.style = simpleStyleOfButtonOpenFile + hoverStyleOfButtonOpenFile }
        controller.buttonOpenFile.setOnMouseExited { controller.buttonOpenFile.style = simpleStyleOfButtonOpenFile }
        controller.buttonOpenFile.setOnMouseClicked { controller.chooseFile() }
        controller.buttonStartStop.setOnMouseClicked { controller.startStop() }

        stage.setOnCloseRequest { controller.closePlayer() }
    }
}