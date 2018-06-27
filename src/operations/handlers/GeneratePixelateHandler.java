package operations.handlers;

import display.gui.GUI;
import java.awt.image.BufferedImage;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import operations.operators.ImageIo;
import operations.operators.Pixelate;

/*
*
* Author: Luis
* Pixelate Code from https://stackoverflow.com/questions/15777821/how-can-i-pixelate-a-jpg-with-java
 */
public class GeneratePixelateHandler extends GUI {

    public static void handle() {
        pixelate();
    }

    private static void pixelate() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Pixelate does not currently work, "
                + "please use it at your own risk.");
        alert.getButtonTypes().setAll(new ButtonType("Continue"), ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        BufferedImage temp = bufferedImage;

        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        } else {
            Object[] byteArrays = ImageIo.getColorByteImageArray2DFromBufferedImage(temp);
            byte[][] rByteData = (byte[][]) byteArrays[0];
            byte[][] gByteData = (byte[][]) byteArrays[1];
            byte[][] bByteData = (byte[][]) byteArrays[2];

            Pixelate pixel = new Pixelate();
            bufferedImageC = temp = pixel.pixelate(temp, 100);
            outputImage = SwingFXUtils.toFXImage(temp, null);
            outputImageView.setImage(outputImage);
        }
    }
}
