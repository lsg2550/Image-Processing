package display;

import javafx.application.Application;
import javafx.stage.Stage;

public class PointOperations extends Application {

    @Override
    public void start(Stage primaryStage) {
        GUI gui = new GUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
