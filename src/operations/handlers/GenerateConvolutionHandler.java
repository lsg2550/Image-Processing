package operations.handlers;

import display.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operations.Convolution;
import operations.ImageIo;

public class GenerateConvolutionHandler extends GUI {

    public static void handle(ActionEvent e) {
        convoGUI();
    }

    private static void convoGUI() {
        //Nodes
        VBox vb = new VBox();
        TilePane tp = new TilePane(Orientation.HORIZONTAL);
        Scene scene = new Scene(vb, 500, 300);
        Stage cnvStage = new Stage();
        Button maskBlur = new Button("Blur");
        Button maskDerivativeH = new Button("Differentiation(Horizontal)");
        Button maskDerivativeV = new Button("Differentiation(Vertical)");
        Button noise = new Button("Light Noise");
        Button noiseHeavy = new Button("Heavy Noise");

        //UI
        maskBlur.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        maskDerivativeH.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        maskDerivativeV.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        noise.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        noiseHeavy.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tp.getChildren().addAll(maskBlur, maskDerivativeH, maskDerivativeV, noise, noiseHeavy);
        vb.getChildren().addAll(new Text("Mask Type"), tp);
        vb.setAlignment(Pos.CENTER);
        tp.setAlignment(Pos.CENTER);

        //Handlers
        maskBlur.setOnAction(e -> {
            convolution(new float[][]{
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f}});
            cnvStage.close();
        });
        maskDerivativeH.setOnAction(e -> {
            convolution(new float[][]{
                {-1.0f, -2.0f, -1.0f},
                {0.0f, 0.0f, 0.0f},
                {1.0f, 2.0f, 1.0f}});
            cnvStage.close();
        });
        maskDerivativeV.setOnAction(e -> {
            convolution(new float[][]{
                {-1.0f, 0, 1.0f},
                {-2.0f, 0, 2.0f},
                {-1.0f, 0, 1.0f}});
            cnvStage.close();
        });
        noise.setOnAction(e -> {
            convolution(new float[][]{
                {0f, -1.0f, 0f},
                {-1.0f, 5.0f, -1.0f},
                {0f, -1.0f, 0f}});
            cnvStage.close();
        });
        noiseHeavy.setOnAction(e -> {
            convolution(new float[][]{
                {-1.0f, -1.0f, -1.0f},
                {-1.0f, 9.0f, -1.0f},
                {-1.0f, -1.0f, -1.0f}});
            cnvStage.close();
        });
        //Stage
        cnvStage.setTitle("Convolution");
        cnvStage.setResizable(false);
        cnvStage.getIcons().add(ICON);
        cnvStage.setScene(scene);
        cnvStage.show();
    }

    private static void convolution(float[][] hMask) {
        BufferedImage temp = ImageIo.toGray(bufferedImage);

        byte[][] input = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);
        byte[][] output = new byte[input.length][input[0].length]; //Not an inplace operation so we need to define output

        Convolution cnv = new Convolution();
        cnv.convolve(input, output, hMask);

        temp = ImageIo.setGrayByteImageArray2DToBufferedImage(output);
        bufferedImageC = temp;
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }
}
