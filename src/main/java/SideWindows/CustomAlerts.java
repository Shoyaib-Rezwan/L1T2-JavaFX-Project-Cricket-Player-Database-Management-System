package SideWindows;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class CustomAlerts {
    String header, body;

    public CustomAlerts(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public Alert getAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERROR!");
        a.setHeaderText(header);
        a.setContentText(body);
        return a;
    }

    public Alert getConfirmationAlert() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmation");
        a.setHeaderText(header);
        a.setContentText(body);
        ButtonType confirmationButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");
        a.getButtonTypes().setAll(confirmationButton, cancelButton);
        return a;
    }
}
