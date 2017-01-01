package display.formatting;

import javafx.scene.text.Text;

public class TextDisplay {

    public static void setStyle(Text... text) {
        for (Text txt : text) {
            txt.setStyle("-fx-fill: black;"
                    + "-fx-font-weight: bold;"
                    + "-fx-font-size: 12px;"
                    + "-fx-font-smoothing-type: lcd;"
                    + "-fx-text-alignment: center;");
        }
    }
}
