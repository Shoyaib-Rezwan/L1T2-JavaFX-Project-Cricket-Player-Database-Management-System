package SideWindows;

import Database.Player;
import Main.ClubHomeWindowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPlayerWindowController {
    @FXML
    public TextField NameField;
    @FXML
    public TextField AgeField;
    @FXML
    public TextField PositionField;
    @FXML
    public TextField SalaryField;
    @FXML
    public TextField JerseyNoField;
    @FXML
    public TextField CountryField;
    @FXML
    public TextField HeightField;
    private String clubName;
    private Player x = new Player();
    public Player y;
    Stage stage;
    ClubHomeWindowController.PlayerWrapper obj;

    @FXML
    public void confirmButtonClicked(ActionEvent actionEvent) {
        if (NameField.getText().isEmpty()) {
            new CustomAlerts("Incomplete information", "Enter a name").getAlert().showAndWait();
            return;
        } else {
            x.setName(NameField.getText());
        }
        if (AgeField.getText().isEmpty()) {
            new CustomAlerts("Incomplete information", "Enter age").getAlert().showAndWait();
            return;
        } else {
            try {
                x.setAge(Integer.parseInt(AgeField.getText()));
            } catch (NumberFormatException e) {
                new CustomAlerts("Incomplete information", "Enter valid age").getAlert().showAndWait();
                return;
            }
        }
        if (PositionField.getText().isEmpty()) {
            new CustomAlerts("Incomplete information", "Enter a position").getAlert().showAndWait();
            return;
        } else {
            x.setPosition(PositionField.getText());
        }
        if (SalaryField.getText().isEmpty()) {
            new CustomAlerts("Incomplete information", "Enter salary").getAlert().showAndWait();
            return;
        } else {
            try {
                x.setSalary(Integer.parseInt(SalaryField.getText()));
            } catch (NumberFormatException e) {
                new CustomAlerts("Incomplete information", "Enter valid salary").getAlert().showAndWait();
                return;
            }
        }
        if (JerseyNoField.getText().isEmpty()) {
            x.setJersey(-1);
        } else {
            try {
                x.setJersey(Integer.parseInt(JerseyNoField.getText()));
            } catch (NumberFormatException e) {
                new CustomAlerts("Incomplete information", "Enter valid jersey no.").getAlert().showAndWait();
                return;
            }
        }
        if (CountryField.getText().isEmpty()) {
            new CustomAlerts("Incomplete information", "Enter a country").getAlert().showAndWait();
            return;
        } else {
            x.setCountry(CountryField.getText());
        }
        if (HeightField.getText().isEmpty()) {
            new CustomAlerts("Incomplete information", "Enter height").getAlert().showAndWait();
        } else {
            try {
                x.setHeight(Float.parseFloat(HeightField.getText()));
            } catch (NumberFormatException e) {
                new CustomAlerts("Incomplete information", "Enter valid height").getAlert().showAndWait();
                return;
            }
        }
        obj.addPlayer(x);
        Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void init(String clubName, ClubHomeWindowController.PlayerWrapper obj) throws IOException {
        this.clubName = clubName;
        startStage(obj);
    }

    public void startStage(ClubHomeWindowController.PlayerWrapper obj) throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPlayerWindow.fxml"));
        Parent root = fxmlLoader.load();
        AddPlayerWindowController controller = fxmlLoader.getController();
        controller.obj = obj;
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Add Player Window");
        stage.showAndWait();
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
