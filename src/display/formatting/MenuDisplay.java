package display.formatting;

import javafx.scene.control.MenuItem;

public class MenuDisplay {

    public MenuDisplay(MenuItem... menuItem) {
        for (MenuItem mItem : menuItem) {
            mItem.setDisable(true);
        }
    }
}
