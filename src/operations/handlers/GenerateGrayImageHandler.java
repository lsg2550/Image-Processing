package operations.handlers;

import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import operations.operators.ImageIo;

public class GenerateGrayImageHandler extends GUI {

    public static void handle() {
        generateGray();
    }

    private static void generateGray() {
        BufferedImage temp = ImageIo.toGray(bufferedImage);
        bufferedImageC = temp;
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }
}
