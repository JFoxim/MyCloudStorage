import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent authRoot = FXMLLoader.load(getClass().getResource("AuthView.fxml"));
        primaryStage.setTitle("Авторизация");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(new Scene(authRoot, 300, 150));
        primaryStage.getIcons().add(new Image(MainClient.class.getResourceAsStream("images/key.png")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
