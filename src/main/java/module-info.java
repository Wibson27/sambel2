module com.sambel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.sambel to javafx.fxml;
    exports com.sambel;
}
