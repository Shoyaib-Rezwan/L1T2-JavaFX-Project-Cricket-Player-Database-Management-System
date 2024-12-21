package NetworkUtil;

import Database.Player;

import java.io.Serializable;

public class AddPlayerRequest implements Serializable {
    Player x;

    public AddPlayerRequest(Player x) {
        this.x = x;
    }

    public Player getPlayer() {
        return x;
    }
}
