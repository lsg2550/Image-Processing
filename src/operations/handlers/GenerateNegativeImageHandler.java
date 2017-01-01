package operations.handlers;

import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import operations.operators.ImageIo;
import operations.operators.ImageNegative;

public class GenerateNegativeImageHandler extends GUI {

    public static void handle(ActionEvent event) {
        generateNegative();
    }

    private static void generateNegative() {
        BufferedImage temp = ImageIo.toGray(bufferedImage);
        byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);

        for (int i = 0; i < grayByteData.length; i++) {
            for (int j = 0; j < grayByteData[0].length; j++) {
                grayByteData[i][j] = (byte) (255 - (grayByteData[i][j] & 0xff));
            }
        }
        ImageNegative.createNegative(grayByteData);

        temp = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
        bufferedImageC = temp;
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }
}
