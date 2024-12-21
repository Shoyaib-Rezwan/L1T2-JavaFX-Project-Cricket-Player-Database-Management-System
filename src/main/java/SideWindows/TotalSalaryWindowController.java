package SideWindows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class TotalSalaryWindowController {
    @FXML
    public Text SalaryText;

    public void getStage(String salary) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TotalSalaryWindowController.class.getResource("TotalSalaryWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 330, 180);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Total Salary");
        TotalSalaryWindowController controller = fxmlLoader.getController();
        controller.SalaryText.setText(salary);
        stage.showAndWait();
    }
}
