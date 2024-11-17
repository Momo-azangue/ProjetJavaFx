package etu.ecole.ensicaen.projetvisionneusehtml;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

import java.io.*;


public class MenuController {

    @FXML
    private TabPane tabPane;
    @FXML
    private void handleSave(){
      Tab tab = tabPane.getSelectionModel().getSelectedItem();

      if (tab != null && tabPane.getTabs().contains(tab)){
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Enregister");

      }

    }

    @FXML
    private void handleOpenFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier HTML");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("fichier HTML", "*.html", "*.htm", "*.txt")
        );
        File file = fileChooser.showOpenDialog(null);

        if(file != null){

        }
    }



    // Méthode pour enregistrer le contenu dans un fichier
    private void saveFile(File file, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content); // Sauvegarder le contenu de la TextArea dans le fichier
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
