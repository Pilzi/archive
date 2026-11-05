module org.example.twothousandfortyeight {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.twothousandfortyeight to javafx.fxml;
    exports org.example.twothousandfortyeight;
}