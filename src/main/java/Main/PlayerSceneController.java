package Main;

import Database.Player;
import Database.PlayerList;
import SideWindows.CustomAlerts;
import SideWindows.ImagePopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.IOException;
import java.util.Optional;

public class PlayerSceneController {
    public Button SaleButton;
    public ImageView PlayerImageView;
    public Text JerseyText;
    public Button BuyButton;
    public Text ClubTitle;
    private Player x;
    @FXML
    public Text ClubNameText;
    @FXML
    public Text AgeText;
    @FXML
    public Text HeightText;
    @FXML
    public Text CountryText;
    @FXML
    public Text PositionText;
    @FXML
    public Text SalaryText;
    @FXML
    public Text NameText;
    Main main;
    private String buyingClub;
    private ClubHomeWindowController clubHomeWindowController;
    private TransferWindowController transferWindowController;

    //    @FXML
    public void init(Main main, ClubHomeWindowController clubHomeWindowController) throws Exception {
        this.main = main;
        this.clubHomeWindowController = clubHomeWindowController;
    }

    public void init(Main main, TransferWindowController transferWindowController) {
        this.main = main;
        this.transferWindowController = transferWindowController;
    }

    public void init(Main main) throws Exception {
        this.main = main;
    }

    public void setData(Player x) throws IOException {
        this.x = x;
        ClubNameText.setText(x.getClub());
        AgeText.setText(String.valueOf(x.getAge()));
        SalaryText.setText(String.valueOf(x.getSalary()));
        HeightText.setText(String.valueOf(x.getHeight()));
        NameText.setText(x.getName());
        CountryText.setText(x.getCountry());
        PositionText.setText(x.getPosition());
        if (x.getJersey() != -1)
            JerseyText.setText(String.valueOf(x.getJersey()));
        else {
            JerseyText.setText("N/A");
            JerseyText.setFill(Color.RED);
        }
    }

    public void saleButtonClicked(ActionEvent actionEvent) throws Exception {
        if (!main.getPlayerList().containsPlayer(x)) {
            new CustomAlerts("Invalid Selling Request", "Player not found!").getAlert().showAndWait();
            return;
        }
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Player selling confirmation");
        textInputDialog.setHeaderText("Enter price of the player");
        textInputDialog.setContentText("Price:");
        textInputDialog.getDialogPane().setPrefSize(475, 250);
        Button okButton =
                (Button) textInputDialog.getDialogPane().lookupButton(textInputDialog.getDialogPane().getButtonTypes().getFirst());
        okButton.setText("Confirm");
        Optional<String> price = textInputDialog.showAndWait();
        if (price.isPresent()) {
            x.setClub("None");
            x.setPrice(Long.parseLong(price.get()));
            main.sellPlayer(x);
        }
        clubHomeWindowController.RefreshButtonClicked(null);
    }

    public void viewImageButtonClicked(ActionEvent actionEvent) {
        String picPath = main.getPath(x.getName());
        ImagePopUp imagePopUp = new ImagePopUp(getClass().getResource(picPath).toExternalForm());
        Popup popup = imagePopUp.getPopUp();
        popup.show(main.stage);
    }

    public void setBuyingClub(String buyingClub) {
        this.buyingClub = buyingClub;
    }

    public void buyButtonClicked(ActionEvent actionEvent) throws Exception {
        if (main.getPlayerList().containsPlayer(x)) {
            new CustomAlerts("Invalid Buying Request", "Player not found!").getAlert().showAndWait();
            return;
        }
        Alert a =
                new CustomAlerts("Player Buying Confirmation", "Are you sure about buying this player?").getConfirmationAlert();
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get().getText().equals("Confirm")) {
            x.setPrice(0);
            x.setClub(buyingClub);
            main.buyPlayer(x);
            transferWindowController.refreshButtonClicked(null);
        }
    }
}
