package display;

import java.awt.image.BufferedImage;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operations.handlers.*;

public class GUI extends BorderPane {

    //Images & ImageViewers
    protected static final Image ICON = new Image("images/icons/icon3.png");
    protected static Image inputImage, outputImage;
    protected static BufferedImage bufferedImage, bufferedImageC;
    protected static ImageView inputImageView = new ImageView(),
            outputImageView = new ImageView();

    //Buttons
    protected static Button clear = new Button("Clear"),
            saveImage = new Button("Save Image"),
            createHistogram = new Button("Histogram"),
            createGrayImage = new Button("Gray Image"),
            createMaskingImage = new Button("Mask Image"),
            contrastStretch = new Button("Contrast Stretch"),
            createNegativeImage = new Button("Negative Image");

    //File
    protected static File file;

    public GUI() {
    }

    public GUI(Stage stage) {
        UI(stage);
    }

    private void UI(Stage stage) {
        //Nodes
        VBox inputOutputResults = new VBox();
        VBox imageButtons = new VBox(10);
        VBox tempBottom = new VBox(10);
        VBox tempRight = new VBox(10);
        HBox topTitleHB = new HBox();
        Scene scene = new Scene(this, 1024, 600);

        //HBox Top
        Text topTitle = new Text("Digital Image Processing Operations");
        topTitleHB.setAlignment(Pos.CENTER);
        topTitleHB.setPrefHeight(32);
        topTitleHB.getChildren().add(topTitle);

        //VBox Center
        Text original = new Text("Original Image");
        Text converted = new Text("Converted Image");
        inputOutputResults.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;"
                + "-fx-alignment: center;");
        inputOutputResults.getChildren().addAll(original, inputImageView, converted, outputImageView);
        inputOutputResults.setMinSize(0, 0);

        //VBox Left
        Text leftTitle = new Text("Image Operations");
        Button uploadImage = new Button("Upload Image");
        imageButtons.setAlignment(Pos.TOP_CENTER);
        imageButtons.setPrefWidth(128);
        imageButtons.getChildren().addAll(leftTitle, createGrayImage, createNegativeImage, contrastStretch, createHistogram, createMaskingImage);
        createGrayImage.setMinWidth(imageButtons.getPrefWidth());
        createNegativeImage.setMinWidth(imageButtons.getPrefWidth());
        contrastStretch.setMinWidth(imageButtons.getPrefWidth());
        createHistogram.setMinWidth(imageButtons.getPrefWidth());
        createMaskingImage.setMinWidth(imageButtons.getPrefWidth());

        //VBox Right
        Text rightTitle = new Text("Extra Options");
        tempRight.setPrefWidth(128);
        tempRight.setAlignment(Pos.TOP_CENTER);
        tempRight.getChildren().addAll(rightTitle, uploadImage, saveImage, clear);
        uploadImage.setMinWidth(tempRight.getPrefWidth());
        saveImage.setMinWidth(tempRight.getPrefWidth());
        clear.setMinWidth(tempRight.getPrefWidth());

        //VBox Bottom
        tempBottom.setPrefHeight(0);

        //Text: Top HBox
        topTitle.setStyle("-fx-fill: #629377;"
                + "-fx-font-weight: bold;"
                + "-fx-stroke: black;"
                + "-fx-stroke-width: 1.5px;"
                + "-fx-font-size: 30px;"
                + "-fx-font-smoothing-type: lcd;"
        );

        //Text: Left VBox
        leftTitle.setStyle("-fx-fill: black;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 12px;"
                + "-fx-font-smoothing-type: lcd;"
                + "-fx-text-alignment: center;"
        );

        //Text: Right VBox
        rightTitle.setStyle("-fx-fill: black;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 12px;"
                + "-fx-font-smoothing-type: lcd;"
                + "-fx-text-alignment: center;"
        );

        //Text: Center VBox
        original.setStyle("-fx-fill: black;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 12px;"
                + "-fx-font-smoothing-type: lcd;"
                + "-fx-text-alignment: center;");
        converted.setStyle("-fx-fill: black;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 12px;"
                + "-fx-font-smoothing-type: lcd;"
                + "-fx-text-alignment: center;");

        //Image: Input & OutPut
        inputImageView.fitHeightProperty().bind(this.heightProperty());
        inputImageView.fitWidthProperty().bind(this.widthProperty());
        outputImageView.fitHeightProperty().bind(this.heightProperty());
        outputImageView.fitWidthProperty().bind(this.widthProperty());
        //Buttons
        uploadImage.setOnAction(e -> {
            ClearHandler.handle(e);
            UploadImageHandler.handle(e);
        });
        createGrayImage.setOnAction(e -> {
            GenerateGrayImageHandler.handle(e);
        });
        createNegativeImage.setOnAction(e -> {
            GenerateNegativeImageHandler.handle(e);
        });
        saveImage.setOnAction(e -> {
            SaveImageHandler.handle(e);
        });
        contrastStretch.setOnAction(e -> {
            GenerateContrastStretchHandler.handle(e);
        });
        createHistogram.setOnAction(e -> {
            GenerateHistogramHandler.handle(e);
        });
        createMaskingImage.setOnAction(e -> {
            GenerateConvolutionHandler.handle(e);
        });
        clear.setOnAction(e -> {
            ClearHandler.handle(e);
        });

        //Button Visibility
        createNegativeImage.setVisible(false);
        createMaskingImage.setVisible(false);
        createGrayImage.setVisible(false);
        contrastStretch.setVisible(false);
        createHistogram.setVisible(false);
        saveImage.setVisible(false);
        clear.setVisible(false);

        //BorderPane
        this.setTop(topTitleHB);
        this.setCenter(inputOutputResults);
        this.setLeft(imageButtons);
        this.setRight(tempRight);
        this.setBottom(tempBottom);
        this.setStyle("-fx-background-color: #629377");
        this.prefHeightProperty().bind(scene.heightProperty());
        this.prefWidthProperty().bind(scene.widthProperty());

        //Scene & Stage
        stage.setScene(scene);
        stage.setTitle("Digital Image Processing");
        stage.getIcons().add(ICON);
        stage.show();
    }
}
