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
import operations.operators.ImageIo;
import operations.operators.Morphology;

/**
 *
 * @author Luis
 */
public class GenerateMorphHandler extends GUI {

    private static Stage mStage = new Stage();

    public static void handler() {
        mStage.show();
    }

    public static void initialize() {
        morphGUI();
    }

    private static void morphGUI() {
        //UI
        VBox vb = new VBox();
        TilePane tp = new TilePane(Orientation.HORIZONTAL);
        Button dilate = new Button("Dilate (Grow, Expand)"),
                erosion = new Button("Erosion (Shrink, Reduce)"),
                insideOutline = new Button("Inside Outline"),
                outsideOutline = new Button("Outside Outline"),
                middleOutline = new Button("Middle Outline"),
                threshold = new Button("Threshold Image"),
                opening = new Button("Opening (Erode -> Dilate)"),
                closing = new Button("Closing (Dilate -> Erode)");

        //Tilepane
        tp.getChildren().addAll(dilate, erosion, insideOutline, outsideOutline, middleOutline, threshold, opening, closing);
        tp.setAlignment(Pos.CENTER);

        //Vbox
        vb.getChildren().addAll(new Text("Morph Operations"), tp);
        vb.setAlignment(Pos.CENTER);

        //Button Format
        ButtonDisplay.ButtonDisplay(dilate, erosion, insideOutline, outsideOutline, middleOutline, threshold, opening, closing);

        //Handlers
        setOnAction(dilate, erosion, insideOutline, outsideOutline, middleOutline, opening, closing);
        threshold.setOnAction(e -> {
            threshold();
        });

        //Stage
        Scene scene = new Scene(vb, 450, 250);
        mStage.setTitle("Morphology");
        mStage.setResizable(false);
        mStage.getIcons().add(ICON);
        mStage.setScene(scene);
    }

    private static void morph(int i) {
        BufferedImage temp = ImageIo.toGray(bufferedImage);

        byte[][] input = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);
        byte[][] output = new byte[input.length][input[0].length];

        input = ImageIo.threshold(input, 128);

        switch (i) {
            case 0:
                output = Morphology.dilate(input, output);
                break;
            case 1:
                output = Morphology.erosion(input, output);
                break;
            case 2:
                output = Morphology.insideOutline(input, output);
                break;
            case 3:
                output = Morphology.outsideOutline(input, output);
                break;
            case 4:
                output = Morphology.middleOutline(input);
                break;
            case 5:
                output = Morphology.opening(input);
                break;
            case 6:
                output = Morphology.closing(input);
                break;
            default:
                break;
        }

        temp = bufferedImageC = ImageIo.setGrayByteImageArray2DToBufferedImage(output);
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }

    private static void threshold() {
        BufferedImage temp = ImageIo.toGray(bufferedImage);

        byte[][] input = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);

        input = ImageIo.threshold(input, 128);

        temp = bufferedImageC = ImageIo.setGrayByteImageArray2DToBufferedImage(input);
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }

    private static void setOnAction(Button... button) {
        for (int i = 0; i < button.length; i++) {
            int temp = i;
            button[i].setOnAction(e -> {
                morph(temp);
            });
        }
    }
}
