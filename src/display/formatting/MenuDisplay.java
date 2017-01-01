package display.formatting;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuDisplay {

    public static void MenuDisplay(MenuItem... menuItem) {
        for (MenuItem mItem : menuItem) {
            mItem.setDisable(true);
        }
    }

    public static void  MenuDisplay(Boolean bool, MenuItem... menuItem) {
        for (MenuItem mItem : menuItem) {
            mItem.setDisable(bool);
        }
    }

    public static void  MenuDisplay(MenuBar... menuBar) {
        for (MenuBar mBar : menuBar) {
            mBar.setStyle("-fx-background-color: #CC9752;"
                    + "-fx-font-weight: bolder;"
                    + "-fx-border-style: hidden solid solid solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-radius: 0px 0px 30px 30px;"
                    + "-fx-background-radius: 0px 0px 30px 30px;"
            );
        }
    }
}
