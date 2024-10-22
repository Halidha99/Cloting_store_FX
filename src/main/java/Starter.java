import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setTitle("Login-Form");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/maindash-board-form.fxml"))));
        //stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/addCustomer_form.fxml"))));

        stage.show();
    }
}
