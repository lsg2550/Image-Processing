package display.gui;

import javafx.application.Application;
import javafx.stage.Stage;

/*
*
* Author: Luis
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        GUI gui = new GUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
