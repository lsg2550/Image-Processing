package operations.handlers;

import display.GUI;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class SaveImageHandler extends GUI {

    public static void handle(ActionEvent e) {
        saveImage();
    }

    private static void saveImage() {
        final FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Save Image");
        filechooser.setInitialDirectory(
                new File(System.getProperty("user.home")));
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        //Where to save image
        File saveFile = filechooser.showSaveDialog(null);
        if (saveFile != null) {
            try {
                ImageIO.write(bufferedImageC, "jpg", saveFile);
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, "Need to have a converted image to save!");
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }
    }
}
