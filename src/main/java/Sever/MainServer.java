package Sever;

import Database.Club;
import Database.FileOperations;
import Database.Player;
import Database.PlayerList;
import NetworkUtil.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MainServer {
    private ServerSocket ss;
    private int clientCount = 0;
    private final PlayerList players = new PlayerList();
    private final PlayerList stockPlayers = new PlayerList();
    private final FileOperations fops =
            new FileOperations("src/main/resources/Files/players.txt");
    private final FileOperations fops2 =
            new FileOperations("src/main/resources/Files/StockPlayers.txt");
    private final Club clubs = new Club();
    final List<Socket> clients = Collections.synchronizedList(new ArrayList<>());
    private final Map<String, String> picPaths = new HashMap<>();

    public MainServer() throws Exception {
        loadFiles();
        ss = new ServerSocket(22222);
        while (true) {
            Socket clientSocket = ss.accept();
            synchronized (clients) {
                clients.add(clientSocket);
            }
            System.out.println("Client- " + (++clientCount) + " accepted");
            serve(clientSocket);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        SocketWrapper helper = new SocketWrapper(clientSocket);
//        Thread clientThread = new Thread(new ServerOperator(this, helper));
//        clientThread.start();
        new ServerOperator(this, helper);
    }

    public void loadFiles() throws Exception {
        fops.readPlayers(players);
        clubs.modifyClubDatabase(players);
        fops.readPassWords(clubs);
        fops2.readPlayers(stockPlayers);
        fops.readPicPaths(picPaths);
    }

    public List<Player> getPlayerList() {
        return players.getPlayers();
    }

    synchronized public PlayerList getPlayers() {
        return players;
    }

    synchronized public PlayerList getStockPlayers() {
        return stockPlayers;
    }

    public synchronized Club getClub() {
        return clubs;
    }

    public static void main(String[] args) throws Exception {
        MainServer server = new MainServer();
    }

    synchronized public void addPlayer(Player x) throws Exception {
        players.addPlayer(x);
        fops.writePlayers(players);
    }

    synchronized public void addPlayerInStock(Player x) throws IOException {
        stockPlayers.addPlayer(x);
        players.removePlayer(x);
        fops.writePlayers(players);
        fops2.writePlayers(stockPlayers);
    }

    synchronized public void buyPlayer(Player x) throws Exception {
        stockPlayers.removePlayer(x);
        fops2.writePlayers(stockPlayers);
        players.addPlayer(x);
        fops.writePlayers(players);
    }

    synchronized public Map<String, String> getPicPaths() {
        return picPaths;
    }

    synchronized public void changePassword(String clubName, String newPassword) throws Exception {
        clubs.getAllPassWords().put(clubName, newPassword);
        fops.writePasswords(clubs);
    }
}
