package operations.handlers;

import display.gui.GUI;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/*
*
* Author: Luis
 */
public class SaveHandler extends GUI {

    public static void handle() {
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
                BufferedImage bufImageRGB = new BufferedImage(bufferedImageC.getWidth(), bufferedImageC.getHeight(), BufferedImage.OPAQUE);

                Graphics2D graphics = bufImageRGB.createGraphics();
                graphics.drawImage(bufferedImageC, 0, 0, null);

                //String thumbURL = file.toURI().toURL().toString();
                //inputImage = new Image(thumbURL);
                
                ImageIO.write(bufImageRGB, "jpg", saveFile);
                graphics.dispose();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, "Need to have a converted image to save!");
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }
    }
}
