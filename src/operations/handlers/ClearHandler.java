package operations.handlers;

import display.GUI;
import javafx.event.ActionEvent;

public class ClearHandler extends GUI {

    public static void handle(ActionEvent e) {
        clear();
    }

    private static void clear() {
        file = null;
        inputImage = null;
        outputImage = null;
        bufferedImage = null;
        bufferedImageC = null;
        inputImageView.setImage(null);
        outputImageView.setImage(null);
        createNegativeImage.setVisible(false);
        createGrayImage.setVisible(false);
        contrastStretch.setVisible(false);
        createHistogram.setVisible(false);
        saveImage.setVisible(false);
        createMaskingImage.setVisible(false);
        clear.setVisible(false);
    }
}
