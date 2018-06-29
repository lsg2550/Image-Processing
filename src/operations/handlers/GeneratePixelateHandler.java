package operations.handlers;

import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
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
        BufferedImage temp = bufferedImage;
        Object[] byteArrays = ImageIo.getColorByteImageArray2DFromBufferedImage(temp);
        byte[][] rByteData = (byte[][]) byteArrays[0];
        byte[][] gByteData = (byte[][]) byteArrays[1];
        byte[][] bByteData = (byte[][]) byteArrays[2];

        Pixelate pixel = new Pixelate();
        bufferedImageC = temp = pixel.pixelate(temp, 10);
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }
}
