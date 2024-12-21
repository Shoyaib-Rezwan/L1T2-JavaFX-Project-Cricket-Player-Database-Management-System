package Main;

import Database.Club;
import Database.Player;
import Database.PlayerList;
import SideWindows.AddPlayerWindowController;
import SideWindows.ChangePasswordWindowController;
import SideWindows.CustomAlerts;
import SideWindows.TotalSalaryWindowController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClubHomeWindowController {
    public ChoiceBox ClubSearchChiceBox;
    @FXML
    public Button AddPlayerButton;
    public Button BuyPlayerButton;
    public ImageView ClubImageView;
    public Text ChangePasswordText;
    public ToggleButton AutoRefreshToggleButton;
    PlayerList playerList;
    List<Player> players = new ArrayList<>();
    private PlayerSceneController controller;
    @FXML
    public Button PlayerSearchNameButton;
    @FXML
    public VBox PlayerListVBox;
    @FXML
    public TextField PlayerNameSearchFIeld;
    @FXML
    public Text ClubNameText;
    @FXML
    public ListView<Rectangle> PlayerList;
    private Main main;
    private String clubName;
    @FXML
    public Button LogOutButton;
    private boolean loginAsAdmin;
    private Timeline autoRefreshTimeLine;

    //boolean data loginAsAdmin is true when an admin logins but false for a guest
    public void init(String clubName, boolean loginAsAdmin) throws Exception {
        this.loginAsAdmin = loginAsAdmin;
        this.clubName = clubName;
        ClubNameText.setText(clubName);
        controller = new PlayerSceneController();
        playerList = main.getPlayerList();//temporarily holds all the players for once
        //store the players only once
        List<Player> temp = playerList.getPlayers();
        for (Player x : temp) {
            if (x.getClub().equalsIgnoreCase(clubName)) {
                players.add(x);
            }
        }
        loadPlayers(players);
        loadClubSearchChoiceBox();
        //set the logoutbutton text as the type of login
        if (loginAsAdmin) {
            LogOutButton.setText("LOG OUT");
        } else {
            LogOutButton.setText("Back");
            AddPlayerButton.setVisible(false);
        }

        String imagePath = main.getPath(clubName);
        ClubImageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        ChangePasswordText.setVisible(loginAsAdmin);
//        autoRefreshStart();
    }

    @FXML
    public void logoutButtonClicked(ActionEvent actionEvent) throws Exception {
        if (AutoRefreshToggleButton.isSelected())
            autoRefreshTimeLine.stop();
        if (loginAsAdmin)
            main.showLoginWindow();
        else {
            main.showMainMenuWindow();
        }

    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void SearchNameButtonClicked(ActionEvent actionEvent) throws Exception {
        Player x = playerList.searchByName(PlayerNameSearchFIeld.getText());
        PlayerNameSearchFIeld.setText(null);
        List<Player> temp = new ArrayList<>();
        if (x != null)
            temp.add(x);
        loadPlayers(temp);
    }

    //this will load all players in the list to the VBox
    public void loadPlayers(List<Player> players) throws Exception {
        //clean the VBox if its already containing some child nodes
        PlayerListVBox.getChildren().clear();
        for (Player x : players) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerScene.fxml"));
            Parent child = fxmlLoader.load();
            PlayerSceneController childController = fxmlLoader.getController();
            childController.setData(x);
            childController.init(main, this);
            childController.BuyButton.setVisible(false);
            if (!loginAsAdmin) {
                childController.SaleButton.setVisible(false);
                BuyPlayerButton.setDisable(true);
            }
            PlayerListVBox.getChildren().add(child);
        }
    }

    public void RefreshButtonClicked(ActionEvent actionEvent) throws Exception {
        playerList = main.getPlayerList();
        List<Player> temp = playerList.getPlayers();
        players.clear();
        for (Player x : temp) {
            if (x.getClub().equalsIgnoreCase(clubName)) {
                players.add(x);
            }
        }
        loadPlayers(players);
        ClubSearchChiceBox.setValue("Apply search filters");
    }

    public void loadClubSearchChoiceBox() {
        ClubSearchChiceBox.getItems().addAll("Player with max salary", "Player with max age", "Player with max height",
                "Total Salary of the club");
        ClubSearchChiceBox.setValue("Apply search filters");

    }

    public void applyFilterButtonClicked(ActionEvent actionEvent) throws Exception {
        String chosenFilter = (String) ClubSearchChiceBox.getValue();
        if (chosenFilter.equals("Player with max salary")) {
            loadPlayers(playerList.searchMaxSalary(clubName));
        } else if (chosenFilter.equals("Player with max age")) {
            loadPlayers(playerList.searchMaxAge(clubName));
        } else if (chosenFilter.equals("Player with max height")) {
            loadPlayers(playerList.searchMaxHeight(clubName));
        } else if (chosenFilter.equals("Total Salary of the club")) {
            long maxSalary = playerList.getTotalSalary(clubName);
            if (maxSalary == -1) maxSalary = 0;
            new TotalSalaryWindowController().getStage(String.valueOf(maxSalary));
//            stage.showAndWait();
        } else return;
    }

    public void buyPlayerButtonClicked(ActionEvent actionEvent) throws Exception {
        if (AutoRefreshToggleButton.isSelected())
            autoRefreshTimeLine.stop();
        main.showTransferWindow(clubName);
    }

//    public void changePasswordButtonClicked(ActionEvent actionEvent) throws Exception {
//
//    }

    public void mouseEnteredPassChangeText(MouseEvent mouseEvent) {
        ChangePasswordText.setUnderline(true);
    }

    public void mouseExitedPassChangeText(MouseEvent mouseEvent) {
        ChangePasswordText.setUnderline(false);
    }

    public void changePasswordTextClicked(MouseEvent mouseEvent) throws Exception {
        ChangePasswordWindowController changePasswordWindowController = new ChangePasswordWindowController();
        changePasswordWindowController.init(clubName, main);
    }

    public void autoRefreshToggleButtonClicked(ActionEvent actionEvent) {
        if (AutoRefreshToggleButton.isSelected()) {
            AutoRefreshToggleButton.setText("ON");
            AutoRefreshToggleButton.setTextFill(Color.GREEN);
            autoRefreshStart();
        } else {
            AutoRefreshToggleButton.setText("OFF");
            AutoRefreshToggleButton.setTextFill(Color.RED);
            autoRefreshTimeLine.stop();
        }
    }

    //this a wrapper class for Player class
    public interface PlayerWrapper {
        List<Player> getPlayers();

        default void addPlayer(Player y) {
            getPlayers().add(y);
        }
    }


    class PlayerWrapper2 implements PlayerWrapper {
        private List<Player> list = new ArrayList<>();

        @Override
        public List<Player> getPlayers() {
            return list;
        }
    }


    @FXML
    public void addPlayerButtonClicked(ActionEvent actionEvent) throws Exception {
        PlayerWrapper obj = new PlayerWrapper2();
        new AddPlayerWindowController().init(clubName, obj);
        if (!obj.getPlayers().isEmpty()) {
            Player x = obj.getPlayers().getFirst();
            if (playerList.containsPlayer(x)) {
                new CustomAlerts("Multi-Player Addition", "Player with this name already exists!!!").getAlert().showAndWait();
                return;
            }
            x.setClub(clubName);
            main.addPlayer(x);
            //refresh the page
            RefreshButtonClicked(null);
        }
    }

    private void autoRefreshStart() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    RefreshButtonClicked(null);
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
