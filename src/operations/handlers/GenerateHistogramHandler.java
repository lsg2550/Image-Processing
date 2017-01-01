package operations.handlers;

import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operations.operators.Histogram;
import operations.operators.ImageIo;

public class GenerateHistogramHandler extends GUI {

    public static void handle(ActionEvent e) {
        displayChoiceGUI();
    }

    private static void displayChoiceGUI() {
        //Nodes
        VBox vb = new VBox();
        TilePane tb = new TilePane(Orientation.HORIZONTAL);
        Scene scene = new Scene(vb, 200, 50);
        Stage cStage = new Stage();
        Button orig = new Button("Original"), conv = new Button("Converted");

        //UI
        orig.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        conv.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        conv.setVisible(false);
        if (bufferedImageC != null) {
            conv.setVisible(true);
        }
        tb.getChildren().addAll(orig, conv);
        vb.getChildren().addAll(new Text("Histogram"), tb);
        vb.setAlignment(Pos.CENTER);
        tb.setAlignment(Pos.CENTER);

        //Buttons
        orig.setOnAction(e -> {
            generateHistogram(true);
            cStage.close();
        });
        conv.setOnAction(e -> {
            generateHistogram(false);
            cStage.close();
        });

        //Stage
        cStage.setTitle("Histogram");
        cStage.setResizable(false);
        cStage.getIcons().add(ICON);
        cStage.setScene(scene);
        cStage.show();
    }

    private static void displayHUI(Image img, ImageView iv, boolean choice) {
        //Nodes
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(iv);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: #629377");

        //Stage & Scene
        Scene scene = new Scene(stackPane, img.getWidth(), img.getHeight());
        Stage hStage = new Stage();
        if (choice == true) {
            hStage.setTitle("Original Histogram");
        } else {
            hStage.setTitle("Converted Histogram");
        }
        hStage.getIcons().add(img);
        hStage.setResizable(false);
        hStage.setScene(scene);
        hStage.showAndWait();
    }

    private static void generateHistogram(boolean choice) {
        //Initializing
        Histogram myHist = new Histogram();
        BufferedImage temp;
        if (choice == true) {
            temp = ImageIo.toGray(bufferedImage);
        } else {
            temp = ImageIo.toGray(bufferedImageC);
        }

        byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);

        //Give Image and Calc Histograms
        myHist.setImageGray(grayByteData);
        myHist.calcHist();
        myHist.calcHistN();

        //Scale Normalized Histogram
        myHist.scaleto256();
        int[] grayLVLCount = myHist.getHistogramGrayLevel();
        byte[][] grayLevelData = new byte[256][256];
        for (int i = 0; i < grayLVLCount.length; i++) {
            for (int j = 256 - grayLVLCount[i]; j < 256; j++) {
                grayLevelData[j][i] = (byte) (255);
            }
        }

        temp = ImageIo.setGrayByteImageArray2DToBufferedImage(grayLevelData);
        Image tempIMG = SwingFXUtils.toFXImage(temp, null);
        ImageView tempImageView = new ImageView(tempIMG);
        displayHUI(tempIMG, tempImageView, choice);
    }
}
