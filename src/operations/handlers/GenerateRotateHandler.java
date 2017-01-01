package operations.handlers;

import display.gui.GUI;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import operations.listeners.Listeners;
import operations.operators.ImageIo;
import operations.operators.Rotate;

public class GenerateRotateHandler extends GUI {

    private static Stage rStage = new Stage();

    public static void handle() {
        rStage.show();
    }

    public static void initialize() {
        rotateGUI();
    }

    private static void rotateGUI() {
        //UI
        VBox vb = new VBox();
        HBox hbLabelTF = new HBox(), hbCheckBox = new HBox(5);
        Scene scene = new Scene(vb, 250, 100);

        //Node
        TextField tfAngle = new TextField();
        Button submitAngle = new Button("Submit");
        CheckBox cbBackward = new CheckBox("Backward Rotate"), cbForward = new CheckBox("Forward Rotate");
        hbLabelTF.getChildren().addAll(new Label("Angle (Degrees):"), tfAngle);
        hbCheckBox.getChildren().addAll(cbBackward, cbForward);
        vb.getChildren().addAll(hbCheckBox, hbLabelTF, submitAngle);
        hbLabelTF.setAlignment(Pos.CENTER);
        hbCheckBox.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        cbBackward.setIndeterminate(false);
        cbForward.setIndeterminate(false);
        tfAngle.setDisable(true);
        submitAngle.setDisable(true);

        //Listeners
        Listeners.Listeners(cbBackward, cbForward, submitAngle, tfAngle);
        Listeners.Listeners(cbForward, cbBackward, submitAngle, tfAngle);

        //Handler
        submitAngle.setOnAction(e -> {
            if (cbBackward.isSelected()) {
                genRotate(Float.parseFloat(tfAngle.getText()), 0);
            } else {
                genRotate(Float.parseFloat(tfAngle.getText()), 1);
            }
            rStage.close();
        });

        //Stage
        rStage.setResizable(false);
        rStage.setScene(scene);
        rStage.setTitle("Rotate");
        rStage.getIcons().add(ICON);
    }

    private static void genRotate(float angle, int index) {
        BufferedImage temp = ImageIo.toGray(bufferedImage);

        byte[][] inputArray = ImageIo.getGrayByteImageArray2DFromBufferedImage(temp);
        byte[][] outputArray = new byte[inputArray.length][inputArray[0].length];

        if (index == 0) {
            outputArray = Rotate.rotateBackward(inputArray, outputArray, angle);
        } else {
            outputArray = Rotate.rotateForward(inputArray, outputArray, angle);
        }

        bufferedImageC = temp = ImageIo.setGrayByteImageArray2DToBufferedImage(outputArray);
        outputImage = SwingFXUtils.toFXImage(temp, null);
        outputImageView.setImage(outputImage);
    }
}
