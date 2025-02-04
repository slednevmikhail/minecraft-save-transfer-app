module org.mstapp.mstgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.mstapp.mstgui to javafx.fxml;
    exports org.mstapp.mstgui;
    exports org.mstapp.mstgui.Controller;
    opens org.mstapp.mstgui.Controller to javafx.fxml;
}