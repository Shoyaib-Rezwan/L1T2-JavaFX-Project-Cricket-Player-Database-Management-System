package Main;

import Database.Player;
import Database.PlayerList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

public class TransferWindowController {
    public Text ClubNameText;
    public VBox PlayerListVBox;
    public ImageView ClubImageView;
    public ToggleButton AutoRefreshToggleButton;
    private String clubName;
    private PlayerList stockPlayers;
    Main main;
    private Timeline autoRefreshTimeLine;

    public void init(Main main, String clubName) throws Exception {
        this.main = main;
        this.clubName = clubName;
        ClubNameText.setText(clubName);
        loadPlayers();
        String imagePath = main.getPath(clubName);
        ClubImageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
//        autoRefreshStart();
    }

    public void loadPlayers() throws Exception {
        stockPlayers = main.getStockPlayerList();
        loadPlayers(stockPlayers.getPlayers());
    }

    public void loadPlayers(List<Player> players) throws Exception {
        //clean the VBox if its already containing some child nodes
        PlayerListVBox.getChildren().clear();
        for (Player x : players) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerScene.fxml"));
            Parent child = fxmlLoader.load();
            PlayerSceneController childController = fxmlLoader.getController();
            childController.setData(x);
            childController.setBuyingClub(clubName);
            childController.ClubTitle.setText("Price:");
            childController.ClubNameText.setText(String.valueOf(x.getPrice()));
            childController.init(main, this);
            childController.SaleButton.setVisible(false);
            PlayerListVBox.getChildren().add(child);
        }
    }

    public void refreshButtonClicked(ActionEvent actionEvent) throws Exception {
        loadPlayers();
    }

    public void backButtonClicked(ActionEvent actionEvent) throws Exception {
        main.showClubHomeWindow(clubName, true);
        if (autoRefreshTimeLine != null && AutoRefreshToggleButton.isSelected()) {
            autoRefreshTimeLine.stop();
        }
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
}
