package etu.ecole.ensicaen.projetvisionneusehtml;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.*;

public class EditorController {


    @FXML
    private TabPane tabPane; // réfrence au Tabpane dans le fichier FXML

    @FXML
    private TextArea textArea;

    @FXML
    private WebView webView;

    @FXML
    private Tab tab;

    @FXML
    private MenuItem Enregistrer;

    @FXML
    private MenuItem Enregistrersous;

    @FXML
    private Button Savebtn;

    @FXML
    private Button Saveasbtn;

    @FXML
    private Button Undobtn;

    @FXML
    private Button Redobtn;

    @FXML
    private Label caracteres;

    @FXML
    private Label lignes;

    private Boolean EncoursdeRedaction = false;
    @FXML
    public void initialize() {

       Enregistrer.setDisable(true);
        Enregistrersous.setDisable(true);
        Saveasbtn.setDisable(true);
        Savebtn.setDisable(true);
        Undobtn.setDisable(true);
        Redobtn.setDisable(true);

        WebEngine webEngine = webView.getEngine(); /* lier la première TextArea Statique à la Webview correspondante */
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            webEngine.loadContent(newValue);
            EncoursdeRedaction = true;
            tab.setText("Untitled Tab 1 (*) ");
            CompteDesCarateres(textArea, caracteres,lignes);
            ControlButton();
        });

        Tab addTab = new Tab("+"); //Créer l'onglet "Ajouter" (avec le symbole "+")
        addTab.setClosable(false); // cet onglet ne peut pas être fermé
        tabPane.getTabs().add(addTab); // Ajouter l'onglet spécial au Tabpane

        //on va observer le comportement de la tabpane "+"  lorsque l'on clique sur +
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == addTab) {
                Tab newTabCreated = CreatNewTab();
                tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTabCreated);// Ajouter avant l'onglet "+"
                tabPane.getSelectionModel().select(newTabCreated); //Sélectionner le nouvel onglet

            }
        });
    }


    private Tab CreatNewTab() {
        Enregistrer.setDisable(true);
        Enregistrersous.setDisable(true);
        Savebtn.setDisable(true);
        Saveasbtn.setDisable(true);
        Undobtn.setDisable(true);
        Redobtn.setDisable(true);
        int tabCount = tabPane.getTabs().size(); //nombre d'onglets actuels

        Tab newTab = new Tab("Untitled Tab " + tabCount );
        newTab.setClosable(true); // permettre la fermeture de cet onglet


        //Création d'un contenant une fois que l'appel a été fait
        AnchorPane anchorPane = new AnchorPane();
        SplitPane splitPane = new SplitPane();
        TextArea textArea = new TextArea();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();



        //méthode pour observer si le contenue change pour mettre à jour le Webview quand le contenu du TextArea changera
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            // charger le contenu Html dans la webview
            webEngine.loadContent(newValue);
            EncoursdeRedaction = true;
            newTab.setText("Untitled Tab " + tabCount + " (*) ");
            ControlButton();
            CompteDesCarateres(textArea, caracteres,lignes);

        });

        //Ajouter les éléments qu Splitpane
        splitPane.getItems().addAll(textArea, webView);

        // Positionner le SpiltPane dans L'anchorPane
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);


        anchorPane.getChildren().add(splitPane);

        newTab.setContent(anchorPane);

        return newTab;


    }





// fonction pour compter les lignes et les caractères
    private void CompteDesCarateres(TextArea textArea, Label caracteres, Label lignes){

             textArea.textProperty().addListener((observable, oldValue, newValue) -> {

                 int carateresCount = newValue.length();
                 caracteres.setText(""+ carateresCount);

                 int lignesCount = newValue.isEmpty() ? 0 : newValue.split("\n").length;

                 lignes.setText(""+ lignesCount);
             });


    }

//fonction de controle de boutton
    private   void ControlButton(){
        if (EncoursdeRedaction = true ){

            Enregistrer.setDisable(false);
            Enregistrersous.setDisable(false);
            Saveasbtn.setDisable(false);
            Savebtn.setDisable(false);
            Undobtn.setDisable(false);
            Redobtn.setDisable(false);

        }


}

    //méthode pour sauvegarder le fichcier actuel (si ouvert )
    @FXML
    private void handleSave() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

        // Vérifier si c'est l'onglet statique
        if (selectedTab != null && selectedTab.getText().equals("Static Tab")) {
            // Sauvegarder le contenu du TextArea1 de l'onglet statique
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer sous");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers HTML", "*.html", "*.htm"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                saveFile(file, textArea.getText());
            }
        } else if (selectedTab != null && selectedTab.getUserData() != null) {
            // Sauvegarder un fichier existant dans un onglet dynamique
            File file = (File) selectedTab.getUserData();
            saveFile(file, textArea.getText());
        } else {
            handleSaveAs();  // Sauvegarder un nouvel onglet dynamique sans fichier
        }
    }
    @FXML
    private void handleSaveAs() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer sous");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers HTML", "*.html", "*.htm"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            if (selectedTab.getText().equals("Static Tab")) {
                // Sauvegarder le contenu de l'onglet statique
                saveFile(file, textArea.getText());
            } else {
                // Sauvegarder le contenu d'un onglet dynamique
                saveFile(file, textArea.getText());
                selectedTab.setText(file.getName()); // Mettre à jour le nom de l'onglet
                selectedTab.setUserData(file);  // Associer le fichier à l'onglet dynamique
            }
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
            ChargerUnFichierDansUneTab(file);
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


    private void ChargerUnFichierDansUneTab(File file){

        try{

            //nous commençons par lire le fichier du fichier HTML
            StringBuilder contentBuilder = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))){
                String line;
                while ((line = reader.readLine()) != null){
                    contentBuilder.append(line).append("\n");
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Créer un nouvel onglet
            Tab newTab = new Tab(file.getName());
            newTab.setClosable(true);

            // Créer un AnchorPane pour contenir la TextArea et la WebView
            AnchorPane anchorPane = new AnchorPane();
            SplitPane splitPane = new SplitPane();
            TextArea textArea = new TextArea();
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            // Charger le contenu HTML dans la TextArea
            String fileContent = contentBuilder.toString();
            textArea.setText(fileContent);

            // Mettre à jour le WebView lorsque le contenu du TextArea change
            textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                webEngine.loadContent(newValue);// Met à jour le rendu HTML en direct
            });

            // Charger initialement le contenu HTML dans le WebView
            webEngine.loadContent(fileContent);

            // Ajouter le TextArea et le WebView dans le SplitPane
            splitPane.getItems().addAll(textArea, webView);
            // Positionner le SplitPane dans l'AnchorPane comme dans le FXML
            AnchorPane.setTopAnchor(splitPane, 3.0);
            AnchorPane.setLeftAnchor(splitPane, 15.0);
            AnchorPane.setRightAnchor(splitPane, 15.0);
            AnchorPane.setBottomAnchor(splitPane, 23.0);

            // Ajouter le SplitPane dans l'AnchorPane
            anchorPane.getChildren().add(splitPane);

            // Définir l'AnchorPane comme contenu de l'onglet
            newTab.setContent(anchorPane);

            // Ajouter l'onglet au TabPane
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab); // Sélectionner automatiquement le nouvel onglet

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }






}