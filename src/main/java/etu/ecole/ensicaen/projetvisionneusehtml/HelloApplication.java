package etu.ecole.ensicaen.projetvisionneusehtml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        EditorController editorController = fxmlLoader.getController();
        stage.setScene(scene);
        stage.setTitle("Confirmation avant fermeture");
        stage.setOnCloseRequest(windowEvent -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation avant fermeture");
            alert.setHeaderText("voulez vous vraiment fermer l'application ?");
            alert.setContentText("Cliquez sur oui pour fermer ou sur Non pour annuler.");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){

            System.out.println("Fermeture confirmée.");
        }else{
            
            windowEvent.consume();
            System.out.println("Fermeture annulée");
        }



        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}