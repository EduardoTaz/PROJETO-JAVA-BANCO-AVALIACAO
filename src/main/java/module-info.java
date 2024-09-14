module com.example.projetojavafxavaliativa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetojavafxavaliativa to javafx.fxml;
    exports com.example.projetojavafxavaliativa;
}