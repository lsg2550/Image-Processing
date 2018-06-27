package operations.handlers;

import display.formatting.MenuDisplay;
import display.gui.GUI;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import operations.operators.ImageIo;

/*
*
* Author: Luis
 */
public class UploadHandler extends GUI {

    public static void handle() {
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

                /*
                BufferedImage bufImageRGB = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);

                Graphics2D graphics = bufImageRGB.createGraphics();
                graphics.drawImage(bufferedImage, 0, 0, null);

                String thumbURL = file.toURI().toURL().toString();
                inputImage = new Image(thumbURL);
                graphics.dispose();
                 */
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
        MenuDisplay.MenuDisplay(false, menuItems);
        inputImageView.setImage(inputImage);
        outputImageView.setImage(null);
        bufferedImageC = null;
        outputImage = null;
    }
}
