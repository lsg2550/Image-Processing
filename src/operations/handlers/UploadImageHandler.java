package operations.handlers;

import display.GUI;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import operations.ImageIo;

public class UploadImageHandler extends GUI {

    public static void handle(ActionEvent event) {
        upImage();
    }

    private static void upImage() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "No Image Selected/Found!");
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        //Select an Image
        file = fileChooser.showOpenDialog(null);
        if (file != null && file.isFile()) {
            try {
                bufferedImage = ImageIo.readImage(file.getPath());
                inputImage = SwingFXUtils.toFXImage(bufferedImage, null);
                updateGUI();
            } catch (Exception e) {
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        } else {
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }
    }

    private static void updateGUI() {
        inputImageView.setImage(inputImage);
        createNegativeImage.setVisible(true);
        createMaskingImage.setVisible(true);
        createHistogram.setVisible(true);
        createGrayImage.setVisible(true);
        contrastStretch.setVisible(true);
        saveImage.setVisible(true);
        clear.setVisible(true);
    }
}
