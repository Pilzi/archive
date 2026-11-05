package org.example.twothousandfortyeight;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TwoThousandFortyEightApplication extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TwoThousandFortyEightApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("2048");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        scene.getRoot().requestFocus();
    }


    public static void main(String[] args) {
        launch();
    }
}