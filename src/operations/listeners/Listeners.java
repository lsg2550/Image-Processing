package operations.listeners;

import display.formatting.ButtonDisplay;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Listeners {

    /*CheckBox Listeners for GenerateConvolutionHandler*/
    public Listeners(CheckBox checked, CheckBox unChecked, CheckBox extra, Button btn, TextField textfield, Button... button) {
        checked.selectedProperty().addListener(e -> {
            if (checked.isSelected()) {
                new ButtonDisplay(false, button);
                unChecked.setSelected(false);
                if (extra.isSelected()) {
                    btn.setDisable(false);
                    textfield.setVisible(true);
                }
            } else if (!checked.isSelected() && !unChecked.isSelected()) {
                new ButtonDisplay(true, button);
            }
        });
    }

    public Listeners(CheckBox checked, CheckBox extra, CheckBox extra1, Button btn, TextField textfield) {
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
}
