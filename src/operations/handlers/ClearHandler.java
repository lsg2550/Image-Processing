package operations.handlers;

import display.formatting.MenuDisplay;
import display.gui.GUI;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ClearHandler extends GUI {

    public static void handle() {
        clear();
    }

    private static void clear() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("You're about to "
                + "clear everything, resetting the whole program! "
                + "If you want to save anything please do so first!");
        alert.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.APPLY);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.APPLY) {
            MenuDisplay.MenuDisplay(true, fMove, fSave, fClear, cHistogram, cGrayImage,
                    cNegativeImage, cContrastStretch, cEqualizedImage, cMaskingImage,
                    cGradientImage, cRotateImage);
            outputImageView.setImage(null);
            inputImageView.setImage(null);
            bufferedImageC = null;
            bufferedImage = null;
            outputImage = null;
            inputImage = null;
            file = null;
        } else {
            alert.close();
        }
    }
}
