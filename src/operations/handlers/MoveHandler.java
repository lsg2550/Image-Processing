package operations.handlers;

import display.gui.GUI;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/*
*
* Author: Luis
 */
public class MoveHandler extends GUI {

    public static void handle() {
        move();
    }

    private static void move() {
        if (bufferedImageC != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("You're about to "
                    + "set the converted image as the original image, "
                    + "if you want to save this image please do so first!");
            alert.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.APPLY);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.APPLY) {
                outputImageView.setImage(null);
                bufferedImage = bufferedImageC;
                inputImage = outputImage;
                inputImageView.setImage(inputImage);
            } else {
                alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Need to have a converted image to move!");
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }
    }
}
