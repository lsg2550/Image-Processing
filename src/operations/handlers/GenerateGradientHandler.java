package operations.handlers;

import display.formatting.ButtonDisplay;
import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operations.operators.Gradient;
import operations.operators.ImageIo;

/*
*
* Author: Luis
 */
public class GenerateGradientHandler extends GUI {

    private static Stage gradStage = new Stage();

    public static void handle() {
        gradStage.show();
    }

    public static void initialize() {
        gradGUI();
    }

    private static void gradGUI() {
        //UI
        VBox vb = new VBox();
        TilePane tp = new TilePane(Orientation.HORIZONTAL);
        Button basicGrad = new Button("Basic Gradient"),
                basicGrad2 = new Button("Basic Gradient 2"),
                robertGrad = new Button("Robert Gradient"),
                sobelGrad = new Button("Sobel Gradient"),
                prewittGrad = new Button("Prewitt Gradient");

        tp.getChildren().addAll(basicGrad, basicGrad2, robertGrad, sobelGrad, prewittGrad);
        vb.getChildren().addAll(new Text("Gradients"), tp);
        tp.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);

        //Button
        ButtonDisplay.ButtonDisplay(basicGrad, basicGrad2, robertGrad, sobelGrad, prewittGrad);
        setOnAction(gradStage, basicGrad, basicGrad2, robertGrad, sobelGrad, prewittGrad);

        //Stage
        Scene scene = new Scene(vb, 450, 200);
        gradStage.setTitle("Gradient");
        gradStage.setResizable(false);
        gradStage.getIcons().add(ICON);
        gradStage.setScene(scene);
    }

    private static void genGradient(int i) {
        BufferedImage temp = ImageIo.toGray(bufferedImage);
        byte[][] inputArray = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);
        byte[][] outputArray = new byte[inputArray.length][inputArray[0].length];

        switch (i) {
            case 0:
                Gradient.basicGradient(inputArray, outputArray);
                break;
            case 1:
                Gradient.basicGradient2(inputArray, outputArray);
                break;
            case 2:
                Gradient.robertGradient(inputArray, outputArray);
                break;
            case 3:
                Gradient.sobelGradient(inputArray, outputArray);
                break;
            case 4:
                Gradient.prewittGradient(inputArray, outputArray);
                break;
            default:
                break;
        }

        bufferedImageC = temp = ImageIo.setGrayByteImageArray2DToBufferedImage(outputArray);
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }

    private static void setOnAction(Stage stage, Button... button) {
        for (int i = 0; i < button.length; i++) {
            int temp = i;
            button[i].setOnAction(e -> {
                genGradient(temp);
                stage.close();
            });
        }
    }
}
