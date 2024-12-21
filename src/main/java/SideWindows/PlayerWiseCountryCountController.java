package SideWindows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerWiseCountryCountController {
    @FXML
    public ListView<String> CountryListView;

    public void init(Map<String, Integer> countryCount) {
        for (Map.Entry<String, Integer> entries : countryCount.entrySet()) {
            String country = entries.getKey();
            String count = String.valueOf(entries.getValue());
            CountryListView.getItems().add(country + " has " + count + " players");
        }
    }

    public void startStage(Map<String, Integer> countryCount) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerWiseCountryCountWindow.fxml"));
        Parent root = fxmlLoader.load();
        PlayerWiseCountryCountController controller = fxmlLoader.getController();
        controller.init(countryCount);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Country Wise Player Count");
        stage.showAndWait();
    }
}
