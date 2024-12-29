package Main;

import Database.*;
import NetworkUtil.*;
import Sever.MainServer;
import SideWindows.CustomAlerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;
import java.util.*;
//import java.net.Socket;


public class Main extends Application {
    public Stage stage;
    private SocketWrapper socketWrapper;
    private PlayerList players;
    private PlayerList stockPlayers;
    private Map<String, String> map = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            socketWrapper = new SocketWrapper("localhost", 22222);
        } catch (IOException e) {
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            return;
        }
        loadPlayers();
        loadPicPaths();
        this.stage = primaryStage;
        showLoginWindow();//throws exception
    }

    //method for showing Login Window
    public void showLoginWindow() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginWindow.fxml"));
        Parent root = fxmlLoader.load();//throws exception
        LoginWindowController controller = fxmlLoader.getController();
        controller.setMain(this);
        controller.init(getClubNames());
        Scene scene = new Scene(root, 800, 500);//width*height;
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    //window for home of a club
    public void showClubHomeWindow(String clubName, boolean loginAsAdmin) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ClubHomeWindow.fxml"));
        Parent root = fxmlLoader.load();//throws exception
        ClubHomeWindowController controller = fxmlLoader.getController();
        controller.setMain(this);
        controller.init(clubName, loginAsAdmin);
        Scene scene = new Scene(root);//width*height;
        stage.setScene(scene);
        stage.setTitle(clubName);
        stage.show();
    }//window for Main menu

    public void showMainMenuWindow() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenuWindow.fxml"));
        Parent root = fxmlLoader.load();//throws exception
        MainMenuWindowController controller = fxmlLoader.getController();
        controller.setMain(this);
//        controller.init(clubName);
        Scene scene = new Scene(root, 800, 500);//width*height;
        stage.setScene(scene);
//        stage.setTitle(clubName);
        stage.show();
    }

    public void showTransferWindow(String clubName) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TransferWindow.fxml"));
        Parent root = fxmlLoader.load();//throws exception
        TransferWindowController controller = fxmlLoader.getController();
        controller.init(this, clubName);
//        controller.init(clubName);
        Scene scene = new Scene(root);//width*height;
        stage.setScene(scene);
//        stage.setTitle(clubName);
        stage.show();
    }

    public boolean checkLoginInfo(String club, String password) throws Exception {
        boolean loginStatus = false;
        try {
            socketWrapper.write(new LoginRequest(club, password));
            Object obj = socketWrapper.read();

            if (obj instanceof LoginStatus) {
                LoginStatus temp = (LoginStatus) obj;
                loginStatus = temp.getLoginStatus();
            }
            return loginStatus;
        } catch (SocketException e) {
            stage.close();
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            System.exit(-1);
        }
        return false;
    }

    public String[] getClubNames() throws Exception {
        List<String> clubNames = new ArrayList<>();
        List<Player> temp = players.getPlayers();
        for (Player X : temp) {
            if (!clubNames.contains(X.getClub())) {
                clubNames.add(X.getClub());
            }
        }
        return clubNames.toArray(new String[0]);
    }

    public PlayerList getPlayerList() throws Exception {
        loadPlayers();
        return players;
    }

    public PlayerList getStockPlayerList() throws Exception {
        loadStockPlayers();
        return stockPlayers;
    }

    public void loadPlayers() throws Exception {
        try {
            socketWrapper.write(new LoadPlayerRequest());
            players = null;
            players = (PlayerList) socketWrapper.read();
        } catch (SocketException e) {
            stage.close();
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            System.exit(-1);
        }
    }

    public void loadStockPlayers() throws Exception {
        try {
            socketWrapper.write(new LoadStockRequest());
            stockPlayers = (PlayerList) socketWrapper.read();
        } catch (SocketException e) {
            stage.close();
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            System.exit(-1);
        }
    }

    public void addPlayer(Player x) throws Exception {
        try {
            socketWrapper.write(new AddPlayerRequest(x));
        } catch (SocketException e) {
            stage.close();
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            System.exit(-1);
        }
    }

    public void sellPlayer(Player x) throws IOException {
        try {
            socketWrapper.write(new SellPlayerRequest(x));
        } catch (SocketException e) {
            stage.close();
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            System.exit(-1);
        }
    }

    public void buyPlayer(Player x) throws Exception {
        try {
            socketWrapper.write(new BuyPlayerRequest(x));
        } catch (SocketException e) {
            stage.close();
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            System.exit(-1);
        }
    }

    //a private method for loading the playerList
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void loadPicPaths() throws Exception {
        try {
            socketWrapper.write(new PicPathsRequest());
            PicPathSendToClient temp = (PicPathSendToClient) socketWrapper.read();
            this.map = temp.getMap();
        } catch (SocketException e) {
            stage.close();
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            System.exit(-1);
        }
    }

    public String getPath(String name) {
        if (map.get(name) != null) {
            return map.get(name);
        } else
            return map.get("General");
    }

    public void changePassword(String clubName, String newPassword) throws Exception {
        try {
            socketWrapper.write(new ChangePasswordRequest(clubName, newPassword));
        } catch (SocketException e) {
            stage.close();
            new CustomAlerts("Connection Error", "Server not found!!!!!").getAlert().showAndWait();
            System.exit(-1);
        }
    }
}
