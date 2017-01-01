package pointoperations;

import java.awt.image.BufferedImage;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class GUI extends BorderPane {

    //Node
    private VBox inputOutputResults = new VBox();

    //Images & ImageViewers
    private Image inputImage;
    private Image outputImage;
    private ImageView inputImageView = new ImageView();
    private ImageView outputImageView = new ImageView();
    private BufferedImage bufferedImage;

    //Buttons
    private final Button uploadImage = new Button("Upload Image");
    private final Button createGrayImage = new Button("Gray Image");
    private final Button createNegativeImage = new Button("Negative Image");
    private final Button saveImage = new Button("Save Image");
    private final Button contrastStretch = new Button("Contrast Stretch");

    //File
    private File file;

    public GUI(Stage stage) {
        UI(stage);
    }

    private void UI(Stage stage) {
        //Nodes
        VBox imageButtons = new VBox(10);
        VBox tempRight = new VBox(10);
        VBox tempBottom = new VBox(10);
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

        //VBox Left
        Text leftTitle = new Text("Image Operations");
        imageButtons.setAlignment(Pos.TOP_CENTER);
        imageButtons.setPrefWidth(128);
        imageButtons.getChildren().addAll(leftTitle, uploadImage, createGrayImage, createNegativeImage, contrastStretch);
        uploadImage.setMinWidth(imageButtons.getPrefWidth());
        createGrayImage.setMinWidth(imageButtons.getPrefWidth());
        createNegativeImage.setMinWidth(imageButtons.getPrefWidth());
        contrastStretch.setMinWidth(imageButtons.getPrefWidth());

        //VBox Right
        Text rightTitle = new Text("Extra Options");
        tempRight.setPrefWidth(128);
        tempRight.setAlignment(Pos.TOP_CENTER);
        tempRight.getChildren().addAll(rightTitle, saveImage);
        saveImage.setMinWidth(tempRight.getPrefWidth());

        //VBox Bottom
        tempBottom.setPrefHeight(10);

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
        inputImageView.fitHeightProperty().bind(this.heightProperty().multiply(0.43));
        inputImageView.fitWidthProperty().bind(this.widthProperty().multiply(0.50));
        outputImageView.fitHeightProperty().bind(this.heightProperty().multiply(0.43));
        outputImageView.fitWidthProperty().bind(this.widthProperty().multiply(0.50));

        //Buttons
        uploadImage.setOnAction(new UploadImageHandler());
        createGrayImage.setOnAction(new GenerateGrayImageHandler());
        createNegativeImage.setOnAction(new GenerateNegativeImageHandler());
        saveImage.setOnAction(new SaveImageHandler());
        contrastStretch.setOnAction(new ConstrastStretchHandler());

        //Button Visibility
        createGrayImage.setVisible(false);
        createNegativeImage.setVisible(false);
        contrastStretch.setVisible(false);
        saveImage.setVisible(false);

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
        stage.getIcons().add(new Image("images/icon.png"));
        stage.show();
    }

    class UploadImageHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"));

            //Select an Image
            file = fileChooser.showOpenDialog(null);
            if (file != null && file.isFile()) {
                try {
                    bufferedImage = ImageIo.readImage(file.getPath());
                    inputImage = SwingFXUtils.toFXImage(bufferedImage, null);
                } catch (Exception e) {
                }
                inputImageView.setImage(inputImage);
                createGrayImage.setVisible(true);
                createNegativeImage.setVisible(true);
                contrastStretch.setVisible(true);
                saveImage.setVisible(true);
            }
        }
    }

    class GenerateNegativeImageHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            // Create the output image & attach to the output view
            BufferedImage temp = ImageIo.toGray(bufferedImage);
            byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);

            for (int i = 0; i < grayByteData.length; i++) {
                for (int j = 0; j < grayByteData[0].length; j++) {
                    grayByteData[i][j] = (byte) (255 - (grayByteData[i][j] & 0xff));
                }
            }
            ImageNegative.createNegative(grayByteData);

            temp = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);

            outputImage = SwingFXUtils.toFXImage(temp, null);
            outputImageView.setImage(outputImage);
        }
    }

    class GenerateGrayImageHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            // Create the output image & attach to the output view
            BufferedImage temp = ImageIo.toGray(bufferedImage);
            outputImage = SwingFXUtils.toFXImage(ImageIo.toGray(temp), null);
            outputImageView.setImage(outputImage);
        }
    }

    class SaveImageHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            final FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Save Image");
            filechooser.setInitialDirectory(
                    new File(System.getProperty("user.home")));
            filechooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"));

            //Where to save image
            File saveFile = filechooser.showSaveDialog(null);
            if (saveFile != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(outputImage, null), "jpg", saveFile);
                } catch (Exception e) {
                }
            }
        }
    }

    class ConstrastStretchHandler implements EventHandler<ActionEvent> {

        private double a = 0, b = 0, ya = 0, yb = 0, alpha = 0, beta = 0, gamma = 0;

        @Override
        public void handle(ActionEvent event) {
            dialogUI();
            csNoLUT();
            //csLUT();
        }

        private void dialogUI() {
            //Text/Label/TextField/Button
            Text txt = new Text("Please Enter Contrast Stretching Values:");
            Label lbl1 = new Label("Values for 'a': ");
            Label lbl2 = new Label("Values for 'b': ");
            Label lbl3 = new Label("Values for 'ya': ");
            Label lbl4 = new Label("Values for 'yb': ");
            Label lbl5 = new Label("Values for 'alpha': ");
            Label lbl6 = new Label("Values for 'beta': ");
            Label lbl7 = new Label("Values for 'gamma': ");
            TextField txtF1 = new TextField();
            TextField txtF2 = new TextField();
            TextField txtF3 = new TextField();
            TextField txtF4 = new TextField();
            TextField txtF5 = new TextField();
            TextField txtF6 = new TextField();
            TextField txtF7 = new TextField();
            Button btn = new Button("Submit");

            //UI
            GridPane gp = new GridPane();
            Scene scene = new Scene(gp, 300, 230);
            Stage dStage = new Stage();
            gp.setHgap(10);
            gp.setVgap(3);
            gp.add(txt, 0, 0);
            gp.add(lbl1, 0, 1);
            gp.add(txtF1, 1, 1);
            gp.add(lbl2, 0, 2);
            gp.add(txtF2, 1, 2);
            gp.add(lbl3, 0, 3);
            gp.add(txtF3, 1, 3);
            gp.add(lbl4, 0, 4);
            gp.add(txtF4, 1, 4);
            gp.add(lbl5, 0, 5);
            gp.add(txtF5, 1, 5);
            gp.add(lbl6, 0, 6);
            gp.add(txtF6, 1, 6);
            gp.add(lbl7, 0, 7);
            gp.add(txtF7, 1, 7);
            gp.add(btn, 0, 8);

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            gp.getColumnConstraints().add(column);
            gp.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

            //Handling
            btn.setOnAction(e -> {
                a = Double.parseDouble(txtF1.getText());
                b = Double.parseDouble(txtF2.getText());
                ya = Double.parseDouble(txtF3.getText());
                yb = Double.parseDouble(txtF4.getText());
                alpha = Double.parseDouble(txtF5.getText());
                beta = Double.parseDouble(txtF6.getText());
                gamma = Double.parseDouble(txtF7.getText());
                dStage.close();
            });

            dStage.setTitle("Contrast Stretch Values");
            dStage.getIcons().add(new Image("images/icon.png"));
            dStage.setScene(scene);
            dStage.setResizable(false);
            dStage.showAndWait();
        }

        private void csNoLUT() {
            BufferedImage temp = ImageIo.toGray(bufferedImage);
            byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);
            double m = (yb - ya) / (b - a);

            for (int i = 0; i < grayByteData.length; i++) {
                for (int j = 0; j < grayByteData[0].length; j++) {
                    if (0 <= grayByteData[i][j] && grayByteData[i][j] < a) {
                        grayByteData[i][j] = (byte) ImageIo.clip((float) (alpha * grayByteData[i][j]));
                    } else if (a <= grayByteData[i][j] && grayByteData[i][j] < b) {
                        grayByteData[i][j] = (byte) ImageIo.clip((float) (beta * (grayByteData[i][j] - a) + ya));
                    } else if (b <= grayByteData[i][j] && grayByteData[i][j] < 255) {
                        grayByteData[i][j] = (byte) ImageIo.clip((float) (gamma * (grayByteData[i][j] - b) + yb));
                    }
                }
            }

            temp = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
            outputImage = SwingFXUtils.toFXImage(temp, null);
            outputImageView.setImage(outputImage);
        }

        private void csLUT() {
            BufferedImage temp = ImageIo.toGray(bufferedImage);
            byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);
            byte[] grayByteDataS = ImageIo.getGrayByteImageArray1DFromBufferedImage(temp);
            double m = (yb - ya) / (b - a);

            int[] lut = new int[grayByteDataS.length];
            for (int i = 0; i < lut.length; i++) {
                lut[i] = grayByteDataS[i];
            }

            for (int i = 0; i < grayByteData.length; i++) {
                for (int j = 0; j < grayByteData[0].length; j++) {
                    if (0 <= grayByteData[i][j] && grayByteData[i][j] < a) {
                        grayByteData[i][j] = (byte) ImageIo.clip(lut[i]);
                    } else if (a <= grayByteData[i][j] && grayByteData[i][j] < b) {
                        grayByteData[i][j] = (byte) ImageIo.clip(lut[i]);
                    } else if (b <= grayByteData[i][j] && grayByteData[i][j] < 255) {
                        grayByteData[i][j] = (byte) ImageIo.clip(lut[i]);
                    }
                }

                temp = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
                outputImage = SwingFXUtils.toFXImage(temp, null);
                outputImageView.setImage(outputImage);
            }
        }
    }
}
