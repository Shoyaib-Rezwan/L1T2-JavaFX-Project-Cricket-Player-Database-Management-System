package NetworkUtil;

import Database.Player;

import java.io.Serializable;

public class SellPlayerRequest implements Serializable {
    Player x;

    public SellPlayerRequest(Player x) {
        this.x = x;
    }

    public Player getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Sell Player Request";
    }
}
