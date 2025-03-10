module com.example.first_try_of_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;


    opens com.example.first_try_of_fx to javafx.fxml;
    exports com.example.first_try_of_fx;

}