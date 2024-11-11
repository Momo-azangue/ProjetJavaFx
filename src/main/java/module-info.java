module etu.ecole.ensicaen.projetvisionneusehtml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens etu.ecole.ensicaen.projetvisionneusehtml to javafx.fxml;
    exports etu.ecole.ensicaen.projetvisionneusehtml;
}