package operations.handlers;

import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import operations.operators.Histogram;
import operations.operators.ImageIo;

public class GenerateEqualizedImageHandler extends GUI {

    public static void handle(ActionEvent e) {
        equalize();
    }

    private static void equalize() {
        //Initializing
        Histogram equalizeHist = new Histogram();
        BufferedImage temp = ImageIo.toGray(bufferedImage);

        byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);

        //Give Image and Calc Histograms
        equalizeHist.setImageGray(grayByteData);
        equalizeHist.calcHist();
        equalizeHist.calcHistN();
        equalizeHist.calcHistE();

        //Give This Class the Histograms
        int[] histogramE = equalizeHist.getLut();
        
        for (int i = 0; i < grayByteData.length; i++) {
            for (int j = 0; j < grayByteData[0].length; j++) {
                grayByteData[i][j] = (byte) histogramE[(grayByteData[i][j] & 0xFF)];
            }
        }

        temp = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
        bufferedImageC = temp;
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }
}
