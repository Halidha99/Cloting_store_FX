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
        stage.setTitle("Clothing-shop");
      stage.close();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/clothingshop_main_form.fxml"))));
        //stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/addCustomer_form.fxml"))));

        stage.show();
    }
}
