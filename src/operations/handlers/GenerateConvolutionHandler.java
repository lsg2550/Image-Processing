package operations.handlers;

import operations.listeners.Listeners;
import display.formatting.ButtonDisplay;
import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operations.parsing.TryParse;
import operations.operators.Convolution;
import operations.operators.ImageIo;
import operations.operators.Noise;

/*
*
* Author: Luis
 */
public class GenerateConvolutionHandler extends GUI {

    private static Stage cnvStage = new Stage();
    private static final float[][][] MASKS = {
        //[0] == Blur
        {{0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
        {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
        {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
        {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
        {0.04f, 0.04f, 0.04f, 0.04f, 0.04f}},
        //[1] == Derivative Horizontal
        {{-1.0f, -2.0f, -1.0f},
        {0.0f, 0.0f, 0.0f},
        {1.0f, 2.0f, 1.0f}},
        //[2] == Dervative Vertical
        {{-1.0f, 0, 1.0f},
        {-2.0f, 0, 2.0f},
        {-1.0f, 0, 1.0f}},
        //[3] == Integral
        {{0f, -1.0f, 0f},
        {-1.0f, 5.0f, -1.0f},
        {0f, -1.0f, 0f}},
        //[4] == Integral Heavy
        {{-1.0f, -1.0f, -1.0f},
        {-1.0f, 9.0f, -1.0f},
        {-1.0f, -1.0f, -1.0f}},
        //[5] == Even Unweighted Mask (1/4)
        {{0.25f, 0.25f},
        {0.25f, 0.25f}},
        //[6] == Even Unweighted Mask (1/16)
        {{0.0625f, 0.0625f, 0.0625f, 0.0625f},
        {0.0625f, 0.0625f, 0.0625f, 0.0625f},
        {0.0625f, 0.0625f, 0.0625f, 0.0625f},
        {0.0625f, 0.0625f, 0.0625f, 0.0625f}},
        //[7] == Even Unweighted Mask (1/36)
        {{0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f},
        {0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f},
        {0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f},
        {0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f},
        {0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f},
        {0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f, 0.02777777777f}},
        //[8] == Do Nothing
        {{0, 0, 0},
        {0, 1, 0},
        {0, 0, 0}}};

    private static final CheckBox CBGN = new CheckBox("Add Noise"),
            CBC = new CheckBox("Color"),
            CBG = new CheckBox("Gray");
    private static final TextField VARIANCE = new TextField("Variance");

    public static void handle() {
        cnvStage.show();
    }

    public static void initialize() {
        convoGUI();
    }

    private static void convoGUI() {
        /*Nodes*/
        VBox vb = new VBox();
        HBox hb = new HBox(5);
        TilePane tp = new TilePane(Orientation.HORIZONTAL);
        Scene scene = new Scene(vb, 575, 225);
        Button maskBlur = new Button("Blur (Smoothing)"),
                maskDerivativeH = new Button("Differentiation(Horizontal)"),
                maskDerivativeV = new Button("Differentiation(Vertical)"),
                maskIntegral = new Button("Integral"),
                maskIntegralHeavy = new Button("Integral (Heavy)"),
                maskNothing = new Button("Add Noise"),
                mask2x2 = new Button("Smoothing 2x2"),
                mask4x4 = new Button("Smoothing 4x4"),
                mask6x6 = new Button("Smoothing 6x6");
        CBGN.setIndeterminate(false);
        CBC.setIndeterminate(false);
        CBG.setIndeterminate(false);

        /*UI*/
        hb.getChildren().addAll(CBC, CBG, CBGN, VARIANCE);
        tp.getChildren().addAll(maskBlur, maskDerivativeH, maskDerivativeV,
                maskIntegral, maskIntegralHeavy, mask2x2, mask4x4, mask6x6,
                maskNothing);
        vb.getChildren().addAll(hb, new Text("Mask Type"), tp);
        vb.setAlignment(Pos.CENTER);
        tp.setAlignment(Pos.CENTER);
        hb.setAlignment(Pos.CENTER);

        /*TextField*/
        VARIANCE.setTooltip(new Tooltip("Variance"));
        VARIANCE.setMaxWidth(75);

        /*Buttons*/
        ButtonDisplay.ButtonDisplay(maskBlur, maskDerivativeH, maskDerivativeV, maskIntegral,
                maskIntegralHeavy, mask2x2, mask4x4, mask6x6, maskNothing);
        ButtonDisplay.ButtonDisplay(true, maskBlur, maskDerivativeH, maskDerivativeV, maskIntegral,
                maskIntegralHeavy, mask2x2, mask4x4, mask6x6, maskNothing);

        /*Listener*/
        Listeners.Listeners(CBC, CBG, CBGN, maskNothing, VARIANCE, maskBlur, maskDerivativeH, maskDerivativeV, maskIntegral,
                maskIntegralHeavy, mask2x2, mask4x4, mask6x6);
        Listeners.Listeners(CBG, CBC, CBGN, maskNothing, VARIANCE, maskBlur, maskDerivativeH, maskDerivativeV, maskIntegral,
                maskIntegralHeavy, mask2x2, mask4x4, mask6x6);
        Listeners.Listeners(CBGN, CBG, CBC, maskNothing, VARIANCE);

        /*Handlers*/
        setOnAction(cnvStage, maskBlur, maskDerivativeH, maskDerivativeV, maskIntegral,
                maskIntegralHeavy, mask2x2, mask4x4, mask6x6, maskNothing);

        /*Stage*/
        cnvStage.setTitle("Convolution");
        cnvStage.setResizable(false);
        cnvStage.getIcons().add(ICON);
        cnvStage.setScene(scene);
    }

    private static void convolution(float[][] hMask) {
        Convolution cnv = new Convolution();
        BufferedImage temp = null;
        if (CBC.isSelected()) {
            temp = bufferedImage;

            Object[] object = ImageIo.getColorByteImageArray2DFromBufferedImage(temp);

            if (CBGN.isSelected()) {
                gaussianNoiseConvolveC(object);
            }

            byte[][] inputColorR = (byte[][]) object[0];
            byte[][] inputColorG = (byte[][]) object[1];
            byte[][] inputColorB = (byte[][]) object[2];
            byte[][] outputColorR = new byte[inputColorR.length][inputColorR[0].length]; //Not an inplace operation so we need to define output
            byte[][] outputColorG = new byte[inputColorG.length][inputColorG[0].length];
            byte[][] outputColorB = new byte[inputColorB.length][inputColorB[0].length];
            cnv.chooseHandler(inputColorR, outputColorR, hMask);
            cnv.chooseHandler(inputColorG, outputColorG, hMask);
            cnv.chooseHandler(inputColorB, outputColorB, hMask);

            temp = ImageIo.setColorByteImageArray2DToBufferedImage(outputColorR, outputColorG, outputColorB);
        } else {
            temp = ImageIo.toGray(bufferedImage);

            byte[][] inputGray = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);
            byte[][] outputGray = new byte[inputGray.length][inputGray[0].length]; //Not an inplace operation so we need to define output

            if (CBGN.isSelected()) {
                gaussianNoiseConvolveG(inputGray);
            }

            cnv.chooseHandler(inputGray, outputGray, hMask);

            temp = ImageIo.setGrayByteImageArray2DToBufferedImage(outputGray);
        }
        bufferedImageC = temp;
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }

    private static void setOnAction(Stage stage, Button... button) {
        for (int i = 0; i < button.length; i++) {
            int temp = i;
            button[i].setOnAction(e -> {
                if (CBGN.isSelected()) {
                    convolution(MASKS[temp]);
                } else {
                    convolution(MASKS[temp]);
                }
                CBGN.setSelected(false);
                CBC.setSelected(false);
                CBG.setSelected(false);
                VARIANCE.setVisible(false);
                stage.close();
            });
        }
    }

    private static Object[] gaussianNoiseConvolveC(Object[] object) {
        return Noise.addGaussianNoise_3(object, genGaussianNoise());
    }

    private static byte[][] gaussianNoiseConvolveG(byte[][] inputGray) {
        return Noise.addGaussianNoise(inputGray, genGaussianNoise());
    }

    private static byte[][] genGaussianNoise() {
        if (TryParse.tryIntParse(VARIANCE.getText()) == false) {
            return Noise.createGaussionNoise(0, 500, bufferedImage.getWidth(), bufferedImage.getHeight());
        } else {
            return Noise.createGaussionNoise(0, Integer.parseInt(VARIANCE.getText()), bufferedImage.getWidth(), bufferedImage.getHeight());
        }
    }
}
