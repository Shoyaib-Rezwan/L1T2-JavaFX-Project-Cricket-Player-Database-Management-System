package Main;

import Database.Club;
import SideWindows.CustomAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginWindowController {
    @FXML
    public ChoiceBox<String> ClubChoiceBox;
    @FXML
    public PasswordField PasswordBox;
    @FXML
    public Button LoginButton;
    @FXML
    public Text GuestButton;
    @FXML
    public Button ResetButton;
    public ImageView IPLLogoImageView;
    public ImageView IntroImageView;
    Main main;
    Club club;
    String picPath;
    String introPath;

    public void setMain(Main main) {
        this.main = main;
    }

    public void init(String[] clubNames) {
        InitClubNames(clubNames);
        picPath = main.getPath("IPL");
        introPath = main.getPath("Intro");
        IPLLogoImageView.setImage(new Image(getClass().getResourceAsStream(picPath)));
        IntroImageView.setImage(new Image(getClass().getResourceAsStream(introPath)));
        IntroImageView.setPreserveRatio(true);
    }

    @FXML
    public void guestClicked(MouseEvent mouseEvent) throws Exception {
        resetClicked(null);
        main.showMainMenuWindow();
    }

    @FXML
    public void mouseEnteredGuestText(MouseEvent mouseEvent) {
        GuestButton.setFill(Color.BLUE);
        GuestButton.setFont(Font.font("System", FontWeight.BOLD, 21));
    }

    @FXML
    public void loginClicked(ActionEvent actionEvent) throws Exception {
        String clubName = ClubChoiceBox.getValue();
        String password = PasswordBox.getText();
        if (clubName == null || password == null || password.isBlank() || password.isEmpty()) {
            new CustomAlerts("Login not successful!", "club name or password can't be empty.").getAlert().showAndWait();
        } else {
            try {
                if (main.checkLoginInfo(clubName, password)) {
                    String temp = clubName;
                    resetClicked(actionEvent);
                    main.showClubHomeWindow(temp, true);
                } else
                    new CustomAlerts("Login not successful!", "Password Incorrect").getAlert().showAndWait();
            } catch (NullPointerException e) {
                new CustomAlerts("Login not successful!", "club name or password can't be empty.").getAlert().showAndWait();
            }
        }
        resetClicked(actionEvent);
    }

    @FXML
    public void mouseExitedGuestText(MouseEvent mouseEvent) {
        GuestButton.setFill(Color.BLACK);
        GuestButton.setFont(Font.font("System", FontWeight.BOLD, 20));
    }

    //this method will load all clubs from the file to the choice box
    void InitClubNames(String[] clubNames) {
        for (String x : clubNames) {
            ClubChoiceBox.getItems().add(x);
        }
    }

    @FXML
    public void resetClicked(ActionEvent actionEvent) {
        ClubChoiceBox.setValue(null);
        PasswordBox.setText(null);
    }
}
