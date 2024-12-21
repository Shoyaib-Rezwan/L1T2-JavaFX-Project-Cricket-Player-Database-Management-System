package Main;

import Database.Player;
import Database.PlayerList;
import SideWindows.CustomAlerts;
import SideWindows.PlayerWiseCountryCountController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenuWindowController {
    private PlayerList players;
    private List<Player> playerList = new ArrayList<>();
    private final List<String> positions = new ArrayList<>();//it will store all the positions
    private final List<String> countries = new ArrayList<>();
    private final List<String> clubs = new ArrayList<>();
    private Main main;
    @FXML
    public TextField LowerRangeText;
    @FXML
    public TextField UpperRangeText;
    @FXML
    public ChoiceBox SearchByPositionBox;
    @FXML
    public ChoiceBox CountryCoiceBox;
    @FXML
    public ChoiceBox ClubChoiceBox;
    @FXML
    public TextField NameText;
    @FXML
    public VBox PlayerListVBox;
    private Timeline autoRefreshTimeLine;

    public void setMain(Main main) throws Exception {
        this.main = main;
        loadPlayersMainMenu();
        loadPlayersVBox(playerList);
        initChoiceBoxes();
//        autoRefreshStart();
    }

    private void loadPlayersMainMenu() throws Exception {
        players = null;
        if (!playerList.isEmpty()) {
            playerList.clear();
        }
        players = main.getPlayerList();
//        new CustomAlerts("a", "b").getAlert().showAndWait();
        playerList = players.getPlayers();
        for (Player x : playerList) {
            if (!positions.contains(x.getPosition())) {
                positions.add(x.getPosition());
            }
            if (!countries.contains(x.getCountry())) {
                countries.add(x.getCountry());
            }
            if (!clubs.contains(x.getClub())) {
                clubs.add(x.getClub());
            }
        }
        SearchByPositionBox.getItems().clear();
        ClubChoiceBox.getItems().clear();
        CountryCoiceBox.getItems().clear();
        for (String x : positions) {
            SearchByPositionBox.getItems().add(x);
        }
        for (String x : clubs) {
            ClubChoiceBox.getItems().add(x);
        }
        ClubChoiceBox.getItems().add("ANY");
        for (String x : countries) {
            CountryCoiceBox.getItems().add(x);
        }
    }

    @FXML
    public void SearchByNameButtonClicked(ActionEvent actionEvent) throws Exception {
        String name = NameText.getText();
        NameText.setText(null);
        if (name.isEmpty()) {
            new CustomAlerts("Search Invalid", "Please input a name").getAlert().showAndWait();
        }
        Player x = players.searchByName(name);
        List<Player> temp = new ArrayList<>();
        temp.add(x);
        loadPlayersVBox(temp);
    }

    @FXML
    public void SearchByCountryandClubButtonClicked(ActionEvent actionEvent) throws Exception {
        String country = (String) CountryCoiceBox.getValue();
        String club = (String) ClubChoiceBox.getValue();
        initChoiceBoxes();
        if (country.equals("Choose country") && (club.equals("Choose club") || club.equals("ANY"))) {
            new CustomAlerts("Search Invalid", "You must choose both country and club").getAlert().showAndWait();
        } else if (country.equals("Choose country")) {
//            autoRefreshTimeLine.stop();
            main.showClubHomeWindow(club, false);
        } else {
            List<Player> temp = players.searchByClubAndCountry(country, club);
            loadPlayersVBox(temp);
        }
    }

    @FXML
    public void SearchByPositionButtonClicked(ActionEvent actionEvent) throws Exception {
        String position = (String) SearchByPositionBox.getValue();
        initChoiceBoxes();
        if (position.equals("Choose Position")) {
            new CustomAlerts("Search Invalid", "You must choose position").getAlert().showAndWait();
        } else {
            List<Player> temp = players.searchByPosition(position);
            loadPlayersVBox(temp);
        }
    }

    @FXML
    public void SearchBySalaryButtonClicked(ActionEvent actionEvent) throws Exception {
        String lowerRange = LowerRangeText.getText();
        String upperRange = UpperRangeText.getText();
        LowerRangeText.setText(null);
        UpperRangeText.setText(null);
        if (lowerRange.isEmpty() || upperRange.isEmpty()) {
            new CustomAlerts("Search Invalid", "You must input both ranges").getAlert().showAndWait();
        } else {
            try {
                List<Player> temp = players.searchBySalary(Integer.parseInt(lowerRange), Integer.parseInt(upperRange));
                loadPlayersVBox(temp);
            } catch (NumberFormatException e) {
                new CustomAlerts("Search Invalid", "You must input integers").getAlert().showAndWait();
            }
        }
    }

    @FXML
    public void backButtonClicked(ActionEvent actionEvent) throws Exception {
//        autoRefreshTimeLine.stop();
        refreshButtonClicked(null);
        main.showLoginWindow();
    }

    private void loadPlayersVBox(List<Player> players) throws Exception {
        //clean the VBox if its already containing some child nodes
//        Image image = new Image(getClass().getResource("/Images/Face.jpg").toExternalForm());
        PlayerListVBox.getChildren().clear();
        for (Player x : players) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerScene.fxml"));
            Parent child = fxmlLoader.load();
            PlayerSceneController childController = fxmlLoader.getController();
            childController.init(main);
            childController.setData(x);
            childController.SaleButton.setVisible(false);
            childController.BuyButton.setVisible(false);
//            childController.PlayerImageView.setImage(image);
            PlayerListVBox.getChildren().add(child);
        }
    }

    public void refreshButtonClicked(ActionEvent actionEvent) throws Exception {
        loadPlayersMainMenu();
        loadPlayersVBox(playerList);
        initChoiceBoxes();
        LowerRangeText.setText(null);
        UpperRangeText.setText(null);
        NameText.setText(null);
    }

    //this method defines the initial texts of all the choice box
    private void initChoiceBoxes() {
        ClubChoiceBox.setValue("Choose club");
        CountryCoiceBox.setValue("Choose country");
        SearchByPositionBox.setValue("Choose Position");
    }

    public void countrywisePlayerCountWindowButtonClicked(ActionEvent actionEvent) throws IOException {
        new PlayerWiseCountryCountController().startStage(players.playerCount());
    }

    private void autoRefreshStart() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    refreshButtonClicked(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(10), event);
        autoRefreshTimeLine = new Timeline(keyFrame);
        autoRefreshTimeLine.setCycleCount(Timeline.INDEFINITE);
        autoRefreshTimeLine.play();
    }

}
