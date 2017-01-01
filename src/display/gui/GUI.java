package display.gui;

import display.formatting.MenuDisplay;
import display.formatting.TextDisplay;
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
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operations.handlers.GenerateGradientHandler;
import operations.handlers.GeneratePixelateHandler;
import operations.handlers.GenerateRotateHandler;

public class GUI extends BorderPane {

    //Images & ImageViewers
    protected static final Image ICON = new Image("images/icons/icon2.png");
    protected static Image inputImage, outputImage;
    protected static BufferedImage bufferedImage, bufferedImageC; //Image = Original, ImageC = Converted
    protected static ImageView inputImageView = new ImageView(),
            outputImageView = new ImageView();

    //MenuItems
    protected static MenuItem fClear = new MenuItem("Clear"),
            fSave = new MenuItem("Save Image"),
            fMove = new MenuItem("Move Image"),
            cHistogram = new MenuItem("Histogram"),
            cGrayImage = new MenuItem("Gray Image"),
            cMaskingImage = new MenuItem("Mask Image"),
            cContrastStretch = new MenuItem("Contrast Stretch"),
            cNegativeImage = new MenuItem("Negative Image"),
            cEqualizedImage = new MenuItem("Equalize Image"),
            cPixelatedImage = new MenuItem("Pixelate Image"),
            cGradientImage = new MenuItem("Gradient Image"),
            cRotateImage = new MenuItem("Rotate Image");

    //File
    protected static File file;

    public GUI() {
    }

    public GUI(Stage stage) {
        UI(stage);
    }

    private void UI(Stage stage) {
        //Nodes
        VBox inputOutputResults = new VBox(0);
        MenuBar menuBar = new MenuBar();
        Menu mFile = new Menu("_File"),
                mImgOp = new Menu("_Operations"),
                mAbout = new Menu("_Help");
        MenuItem fOpen = new MenuItem("Open Image"),
                hAbout = new MenuItem("Help");
        mFile.setMnemonicParsing(true);
        mImgOp.setMnemonicParsing(true);
        mAbout.setMnemonicParsing(true);
        menuBar.getMenus().addAll(mFile, mImgOp, mAbout);
        mFile.getItems().addAll(fOpen, new SeparatorMenuItem(), fMove, fSave, fClear);
        mImgOp.getItems().addAll(cHistogram, new SeparatorMenuItem(), cGrayImage,
                cNegativeImage, cContrastStretch, cEqualizedImage, cMaskingImage,
                cGradientImage, cRotateImage);
        mAbout.getItems().add(hAbout);

        //VBox Center
        Text original = new Text("Original Image"),
                converted = new Text("Converted Image");
        inputOutputResults.setStyle(
                "-fx-border-style: solid;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;"
                + "-fx-alignment: center;"
        );
        inputOutputResults.getChildren().addAll(original, inputImageView, converted, outputImageView);
        inputOutputResults.setMinSize(0, 0);
        inputOutputResults.maxHeightProperty().bind(this.heightProperty().subtract(menuBar.getHeight()));
        inputOutputResults.maxWidthProperty().bind(this.widthProperty());
        TextDisplay.setStyle(original, converted);

        //Image: Input & OutPut
        inputImageView.fitHeightProperty().bind(inputOutputResults.heightProperty().multiply(0.45));
        inputImageView.fitWidthProperty().bind(inputOutputResults.widthProperty().multiply(0.50));
        outputImageView.fitHeightProperty().bind(inputOutputResults.heightProperty().multiply(0.45));
        outputImageView.fitWidthProperty().bind(inputOutputResults.widthProperty().multiply(0.50));

        //MenuBar & Menus
        MenuDisplay.MenuDisplay(true, fMove, fSave, fClear, cHistogram, cGrayImage,
                cNegativeImage, cContrastStretch, cEqualizedImage, cMaskingImage,
                cGradientImage, cRotateImage);
        MenuDisplay.MenuDisplay(menuBar);

        //Handlers
        fOpen.setOnAction(e -> {
            UploadImageHandler.handle();
        });
        cGrayImage.setOnAction(e -> {
            GenerateGrayImageHandler.handle();
        });
        cNegativeImage.setOnAction(e -> {
            GenerateNegativeImageHandler.handle();
        });
        fSave.setOnAction(e -> {
            SaveImageHandler.handle();
        });
        cContrastStretch.setOnAction(e -> {
            GenerateContrastStretchHandler.handle();
        });
        cHistogram.setOnAction(e -> {
            GenerateHistogramHandler.handle();
        });
        cMaskingImage.setOnAction(e -> {
            GenerateConvolutionHandler.handle();
        });
        cEqualizedImage.setOnAction(e -> {
            GenerateEqualizedImageHandler.handle();
        });
        cPixelatedImage.setOnAction(e -> {
            GeneratePixelateHandler.handle();
        });
        cGradientImage.setOnAction(e -> {
            GenerateGradientHandler.handle();
        });
        cRotateImage.setOnAction(e -> {
            GenerateRotateHandler.handle();
        });
        fMove.setOnAction(e -> {
            MoveImageHandler.handle();
        });
        fClear.setOnAction(e -> {
            ClearHandler.handle();
        });
        hAbout.setOnAction(e -> {
            ReadmeGUI.handle();
        });

        GenerateContrastStretchHandler.initialize();
        GenerateConvolutionHandler.initialize();
        GenerateGradientHandler.initialize();
        GenerateRotateHandler.initialize();
        ReadmeGUI.initialize();

        //BorderPane
        this.setTop(menuBar);
        this.setCenter(inputOutputResults);
        this.setStyle("-fx-background-color: #0F3B5F");
        this.prefHeightProperty().bind(stage.heightProperty());
        this.prefWidthProperty().bind(stage.widthProperty());

        //Scene & Stage
        //scene.setCursor(new ImageCursor(ICON));
        Scene scene = new Scene(this, 1024, 600);
        stage.setScene(scene);
        stage.setTitle("Digital Image Processing");
        stage.getIcons().add(ICON);
        stage.show();
    }
}
