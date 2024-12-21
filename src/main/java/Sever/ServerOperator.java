package Sever;

import Database.Player;
import NetworkUtil.*;

public class ServerOperator implements Runnable {
    private MainServer server;
    SocketWrapper helper;

    ServerOperator(MainServer server, SocketWrapper helper) {
        this.server = server;
        this.helper = helper;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = helper.read();
                if (obj == null) {
                    System.err.println("Received null object, terminating connection.");
                    break;
                }

                try {
                    if (obj instanceof LoadPlayerRequest) {
                        helper.write(server.getPlayers());
                    } else if (obj instanceof LoginRequest) {
                        LoginRequest temp = (LoginRequest) obj;
                        boolean loginStatus = checkLoginInfo(temp.getClub(), temp.getPassWord());
                        helper.write(new LoginStatus(loginStatus));
                    } else if (obj instanceof AddPlayerRequest) {
                        AddPlayerRequest temp = (AddPlayerRequest) obj;
                        synchronized (server) {
                            server.addPlayer(temp.getPlayer());
                        }
                    } else if (obj instanceof LoadStockRequest) {
                        helper.write(server.getStockPlayers());
                    } else if (obj instanceof SellPlayerRequest) {
                        SellPlayerRequest temp = (SellPlayerRequest) obj;
                        Player x = temp.getX();
                        server.addPlayerInStock(x);
                    } else if (obj instanceof BuyPlayerRequest) {
                        BuyPlayerRequest temp = (BuyPlayerRequest) obj;
                        Player x = temp.getX();
                        server.buyPlayer(x);
                    } else if (obj instanceof PicPathsRequest) {
                        helper.write(new PicPathSendToClient(server.getPicPaths()));
                    } else if (obj instanceof ChangePasswordRequest) {
                        ChangePasswordRequest temp = (ChangePasswordRequest) obj;
                        server.changePassword(temp.getClubName(), temp.getNewPassword());
                    } else {
                        System.err.println("Received unknown object type: " + obj.getClass().getName());
                    }
                } catch (Exception e) {
                    System.err.println("Error processing request: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }


    //checks when a login attempt is correct or not...
    public boolean checkLoginInfo(String clubName, String password) {
        return server.getClub().getPassWord(clubName).equals(password);
    }

    //returns all available club names as a string array
    public synchronized String[] getClubNames() {
        return server.getClub().getClubs();
    }
}
