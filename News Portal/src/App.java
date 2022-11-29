import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
               
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("News Portal");
        primaryStage.getIcons().add(new Image("file:icons/news.png"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
 
 public static void main(String[] args) {
        launch(args);
    }
}