package etu.ecole.ensicaen.projetvisionneusehtml;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
            Enregistrer.setDisable(false);
            Enregistrersous.setDisable(false);
            Saveasbtn.setDisable(false);
            Savebtn.setDisable(false);
            Undobtn.setDisable(false);
            Redobtn.setDisable(false);
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
            Enregistrer.setDisable(false);
            Enregistrersous.setDisable(false);
            Savebtn.setDisable(false);
            Saveasbtn.setDisable(false);
            Undobtn.setDisable(false);
            Redobtn.setDisable(false);
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


    private void CompteDesCarateres(TextArea textArea, Label caracteres, Label lignes){

             textArea.textProperty().addListener((observable, oldValue, newValue) -> {

                 int carateresCount = newValue.length();
                 caracteres.setText(""+ carateresCount);

                 int lignesCount = newValue.isEmpty() ? 0 : newValue.split("\n").length;

                 lignes.setText(""+ lignesCount);
             });


    }







}