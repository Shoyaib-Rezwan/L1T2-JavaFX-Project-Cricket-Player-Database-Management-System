package SideWindows;

import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordWindowController {
    private Main main;
    private String clubName;
    public Text ClubNameText;
    public PasswordField NewPasswordField;
    public PasswordField RetypePasswordFIeld;
    private Stage stage = new Stage();

    public void init(String clubName, Main main) throws Exception {
//        this.clubName = clubName;
//        this.main = main;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangePasswordWindow.fxml"));
        Parent root = fxmlLoader.load();
        ChangePasswordWindowController controller = fxmlLoader.getController();
        controller.ClubNameText.setText(clubName);
        controller.main = main;
        controller.clubName = clubName;
        Scene scene = new Scene(root);
        stage.setTitle("Password change");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void confirmButtonClicked(ActionEvent actionEvent) throws Exception {
        String newPassword = NewPasswordField.getText();
        String retypedPassword = RetypePasswordFIeld.getText();
        if (newPassword == null || retypedPassword == null || newPassword.isBlank() || retypedPassword.isBlank()) {
            new CustomAlerts("Invalid request!", "Password fields can't be empty!").getAlert().showAndWait();
            NewPasswordField.setText(null);
            RetypePasswordFIeld.setText(null);

        } else if (!newPassword.equals(retypedPassword)) {
            new CustomAlerts("Invalid request!", "Retyped password doesn't match!").getAlert().showAndWait();
            RetypePasswordFIeld.setText(null);
        } else {
            main.changePassword(clubName, newPassword);
            NewPasswordField.setText(null);
            RetypePasswordFIeld.setText(null);
            backButtonClicked(actionEvent);
        }
    }

    public void backButtonClicked(ActionEvent actionEvent) throws Exception {
        ((Stage) (((Node) actionEvent.getSource()).getScene().getWindow())).close();
    }
}
