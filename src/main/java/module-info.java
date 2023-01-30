module com.example.datalogin {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;


    opens com.example.datalogin to javafx.fxml;
    exports com.example.datalogin;
}