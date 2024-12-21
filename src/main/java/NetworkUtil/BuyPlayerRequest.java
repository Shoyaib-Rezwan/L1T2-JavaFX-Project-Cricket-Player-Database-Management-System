package NetworkUtil;

import Database.Player;

import java.io.Serializable;

public class BuyPlayerRequest implements Serializable {
    Player x;

    public BuyPlayerRequest(Player x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Buying Player Request";
    }

    public Player getX() {
        return x;
    }
}
