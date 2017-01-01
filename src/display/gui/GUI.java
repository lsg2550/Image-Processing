package display.gui;

import display.formatting.ButtonDisplay;
import display.formatting.MenuDisplay;
import operations.handlers.GenerateNegativeImageHandler;
import operations.handlers.UploadImageHandler;
import operations.handlers.GenerateHistogramHandler;
import operations.handlers.GenerateConvolutionHandler;
import operations.handlers.SaveImageHandler;
import operations.handlers.GenerateContrastStretchHandler;
import operations.handlers.GenerateGrayImageHandler;
import operations.handlers.GenerateEqualizedImageHandler;
import operations.handlers.ClearHandler;
import operations.handlers.MoveImageHandler;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operations.handlers.GeneratePixelateHandler;

public class GUI extends BorderPane {

    //Images & ImageViewers
    protected static final Image ICON = new Image("images/icons/icon2.png");
    protected static Image inputImage, outputImage;
    protected static BufferedImage bufferedImage, bufferedImageC; //Image = Original, ImageC = Converted
    protected static ImageView inputImageView = new ImageView(),
            outputImageView = new ImageView();

    //Buttons
    protected static Button clear = new Button("Clear"),
            saveImage = new Button("Save Image"), //Saves Converted Image
            moveImage = new Button("Move Image"), //Moves Converted Image to Original Image
            createHistogram = new Button("Histogram"), //Creates Histograms for Original & Converted Image
            createGrayImage = new Button("Gray Image"), //Creates Gray Image from Original Image
            createMaskingImage = new Button("Mask Image"), //Creates a masked image from Original Image
            createContrastStretch = new Button("Contrast Stretch"), //Creates a contrast stretched image from Original Image
            createNegativeImage = new Button("Negative Image"), //Creates a negative image from Original Image
            createEqualizedImage = new Button("Equalize Image"), //Creates an equalized image from Original Image
            createPixelatedImage = new Button("Pixelate Image"); //Pixelate Image

    //Will move from buttons to menu bar
    protected static MenuItem fClear = new MenuItem("Clear"),
            fSave = new MenuItem("Save Image"),
            fMove = new MenuItem("Move Image"),
            cHistogram = new MenuItem("Histogram"),
            cGrayImage = new MenuItem("Gray Image"),
            cMaskingImage = new MenuItem("Mask Image"),
            cContrastStretch = new MenuItem("Contrast Stretch"),
            cNegativeImage = new MenuItem("Negative Image"),
            cEqualizedImage = new MenuItem("Equalize Image"),
            cPixelatedImage = new MenuItem("Pixelate Image");

    //File
    protected static File file;

    public GUI() {
    }

    public GUI(Stage stage) {
        UI(stage);
    }

    private void UI(Stage stage) {
        //Nodes
        /*
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu imgOp = new Menu("Operations");
        MenuItem fOpen = new MenuItem("Open");
        menuBar.getMenus().addAll(file, imgOp);
        file.getItems().addAll(fOpen, new SeparatorMenuItem(), fMove, fSave, fClear);
        imgOp.getItems().addAll(cHistogram, new SeparatorMenuItem(), cGrayImage, cNegativeImage, cContrastStretch, cEqualizedImage, cMaskingImage);
        new MenuDisplay(fMove, fSave, fClear, cHistogram, cGrayImage, cNegativeImage, cContrastStretch, cEqualizedImage, cMaskingImage);
        fOpen.setOnAction(e ->{
            UploadImageHandler.handle(e);
        });
        */
        VBox inputOutputResults = new VBox();
        VBox imageButtons = new VBox(10);
        VBox tempBottom = new VBox(10);
        VBox tempRight = new VBox(10);
        Scene scene = new Scene(this, 1024, 600);

        //VBox Center
        Text original = new Text("Original Image");
        Text converted = new Text("Converted Image");
        inputOutputResults.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;"
                + "-fx-alignment: center;");
        inputOutputResults.getChildren().addAll(original, inputImageView,
                converted, outputImageView);
        inputOutputResults.setMinSize(0, 0);

        //VBox Left
        Text leftTitle = new Text("Image Operations");
        Button uploadImage = new Button("Upload Image");
        uploadImage.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        imageButtons.setAlignment(Pos.TOP_CENTER);
        imageButtons.setPrefWidth(128);
        imageButtons.getChildren().addAll(leftTitle, uploadImage, createGrayImage,
                createNegativeImage, createContrastStretch, createMaskingImage,
                createEqualizedImage);

        //VBox Right
        Text rightTitle = new Text("Extra Options");
        tempRight.setPrefWidth(128);
        tempRight.setAlignment(Pos.TOP_CENTER);
        tempRight.getChildren().addAll(rightTitle, createHistogram, saveImage,
                moveImage, clear);

        //VBox Bottom
        tempBottom.setPrefSize(0, 0);

        //Text: Left VBox/Center VBox/Right VBox
        setStyle(leftTitle);
        setStyle(rightTitle);
        setStyle(original);
        setStyle(converted);

        //Image: Input & OutPut
        inputImageView.fitHeightProperty().bind(this.heightProperty().multiply(0.44));
        inputImageView.fitWidthProperty().bind(inputOutputResults.widthProperty().multiply(0.50));
        outputImageView.fitHeightProperty().bind(this.heightProperty().multiply(0.44));
        outputImageView.fitWidthProperty().bind(inputOutputResults.widthProperty().multiply(0.50));

        //Buttons & Button Events
        new ButtonDisplay(createContrastStretch, createEqualizedImage,
                createPixelatedImage, createNegativeImage, createMaskingImage, createGrayImage,
                createHistogram, saveImage, moveImage, clear);
        uploadImage.setOnAction(e -> {
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
        createContrastStretch.setOnAction(e -> {
            GenerateContrastStretchHandler.handle(e);
        });
        createHistogram.setOnAction(e -> {
            GenerateHistogramHandler.handle(e);
        });
        createMaskingImage.setOnAction(e -> {
            GenerateConvolutionHandler.handle(e);
        });
        createEqualizedImage.setOnAction(e -> {
            GenerateEqualizedImageHandler.handle(e);
        });
        createPixelatedImage.setOnAction(e -> {
            GeneratePixelateHandler.handle(e);
        });
        moveImage.setOnAction(e -> {
            MoveImageHandler.handle(e);
        });
        clear.setOnAction(e -> {
            ClearHandler.handle(e);
        });

        //BorderPane
        //this.setTop(menuBar);
        this.setCenter(inputOutputResults);
        this.setLeft(imageButtons);
        this.setRight(tempRight);
        this.setBottom(tempBottom);
        this.setStyle("-fx-background-color: #629377");
        this.prefHeightProperty().bind(scene.heightProperty());
        this.prefWidthProperty().bind(scene.widthProperty());

        //Scene & Stage
        //scene.setCursor(new ImageCursor(ICON));
        stage.setScene(scene);
        stage.setTitle("Digital Image Processing");
        stage.getIcons().add(ICON);
        stage.show();
    }

    private Text setStyle(Text text) {
        text.setStyle("-fx-fill: black;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 12px;"
                + "-fx-font-smoothing-type: lcd;"
                + "-fx-text-alignment: center;");
        return text;
    }
}
