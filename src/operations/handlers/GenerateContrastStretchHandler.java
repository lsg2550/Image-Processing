package operations.handlers;

import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operations.operators.ImageIo;

public class GenerateContrastStretchHandler extends GUI {

    private static Stage dStage = new Stage();

    public static void handle() {
        dStage.show();
    }

    public static void initialize() {
        contrastStretchGUI();
    }

    private static void contrastStretchGUI() {
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
        Scene scene = new Scene(gp, 300, 235);
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
            try {
                csNoLUT(Double.parseDouble(txtF1.getText()),
                        Double.parseDouble(txtF2.getText()),
                        Double.parseDouble(txtF3.getText()),
                        Double.parseDouble(txtF4.getText()),
                        Double.parseDouble(txtF5.getText()),
                        Double.parseDouble(txtF6.getText()),
                        Double.parseDouble(txtF7.getText())
                );
            } catch (Exception ex) {
                csNoLUT(0, 0, 0, 0, 0, 0, 0);
            }
            dStage.close();
        });

        dStage.setTitle("Contrast Stretch Values");
        dStage.getIcons().add(ICON);
        dStage.setScene(scene);
        dStage.setResizable(false);
    }

    private static void csNoLUT(double a, double b, double ya, double yb, double alpha, double beta, double gamma) {
        BufferedImage temp = ImageIo.toGray(bufferedImage);
        byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);

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
        bufferedImageC = temp;
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }

    /*
    private static void csLUT() {
        BufferedImage temp = ImageIo.toGray(bufferedImage);
        byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);
        byte[] grayByteDataS = ImageIo.getGrayByteImageArray1DFromBufferedImage(temp);

        int[] lut = new int[256];
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
            bufferedImageC = temp;
            outputImage = SwingFXUtils.toFXImage(temp, null);
            outputImageView.setImage(outputImage);
        }
    }*/
}
