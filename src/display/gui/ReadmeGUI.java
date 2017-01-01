package display.gui;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ReadmeGUI extends GUI {

    private static Stage hStage = new Stage();
    
    public static void initialize(){
        readme();
    }
    
    public static void handle() {
        hStage.show();
    }

    private static void readme() {
        //UI
        TextArea ta = new TextArea();
        Scene scene = new Scene(ta, 800, 600);
        //Read from File
        try {
            Scanner s = new Scanner(new File("src/display/help/about.txt"));
            while (s.hasNext()) {
                ta.appendText(s.nextLine() + "\n");
            }
        } catch (IOException ex) {
            ta.appendText("Whoops! I can't find the file :(");
        }

        //TextArea
        ta.setEditable(false);
        
        //Stage
        hStage.setScene(scene);
        hStage.setTitle("Help");
        hStage.getIcons().add(ICON);
    }
}
