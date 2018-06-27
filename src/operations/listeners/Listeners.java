package operations.listeners;

import display.formatting.ButtonDisplay;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Listeners {

    /*CheckBox Listeners for GenerateConvolutionHandler*/
    public static void Listeners(CheckBox checked, CheckBox unChecked, CheckBox extra, Button btn, TextField textfield, Button... button) {
        checked.selectedProperty().addListener(e -> {
            if (checked.isSelected()) {
                ButtonDisplay.ButtonDisplay(false, button);
                unChecked.setSelected(false);
                if (extra.isSelected()) {
                    btn.setDisable(false);
                    textfield.setVisible(true);
                }
            } else if (!checked.isSelected() && !unChecked.isSelected()) {
                ButtonDisplay.ButtonDisplay(true, button);
                btn.setDisable(true);
                textfield.setVisible(false);
            }
        });
    }

    /*When noise is checked and the others are not, then do nothing.
    However, if it is selected and others are selected then set button and textfield visible*/
    public static void Listeners(CheckBox checked, CheckBox extra, CheckBox extra1, Button btn, TextField textfield) {
        checked.selectedProperty().addListener(e -> {
            if (checked.isSelected() && (extra.isSelected() || extra1.isSelected())) {
                btn.setDisable(false);
                textfield.setVisible(true);
            } else {
                btn.setDisable(true);
                textfield.setVisible(false);
            }
        });
    }

    /*For GenerateRotateHandler, when user chooses backward or forward rotation*/
    public static void Listeners(CheckBox checked, CheckBox unchecked, Button button, TextField txtField) {
        checked.selectedProperty().addListener(e -> {
            if (checked.isSelected()) {
                unchecked.setSelected(false);
                txtField.setDisable(false);
                button.setDisable(false);
            } else if (!checked.isSelected() && !unchecked.isSelected()) {
                txtField.setDisable(true);
                button.setDisable(true);
            }
        });
    }
}
