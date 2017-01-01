package display.formatting;

import javafx.scene.control.Button;

public class ButtonDisplay {

    //Init for most buttons from GUI and GenerateConvolutionHandler
    public static void ButtonDisplay(Button... button) {
        for (Button btn : button) {
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }
    }

    //Changes for GUI and GenerateConvolutionHandler
    public static void ButtonDisplay(Boolean bool, Button... button) {
        for (Button btn : button) {
            btn.setDisable(bool);
        }
    }
}
