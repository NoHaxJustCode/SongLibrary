package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.ListController;

public class SongLib extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/SongList.fxml"));
        BorderPane root = loader.load();

        ListController listController = loader.getController();
        listController.start(stage);

        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Song Library");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}