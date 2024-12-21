package Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Club {
    Map<String, List<Player>> clubDatabase = new HashMap<>();
    Map<String, String> passWords = new HashMap<>();

    public void modifyClubDatabase(PlayerList players) {
        for (Player x : players.getPlayers()) {
            if (clubDatabase.get(x.getClub()) == null) {
                List<Player> temp = new ArrayList<>();
                temp.add(x);
                clubDatabase.put(x.getClub(), temp);
            } else
                clubDatabase.get(x.getClub()).add(x);
        }
    }

    public String[] getClubs() {
        String[] clubs = new String[clubDatabase.size()];
        int i = 0;
        for (Map.Entry<String, List<Player>> entries : clubDatabase.entrySet()) {
            clubs[i++] = entries.getKey();
        }
        return clubs;
    }

    public void setPassWords(String club, String password) {
        passWords.put(club, password);
    }

    public String getPassWord(String club) {
        return passWords.get(club);
    }

    public Map<String, String> getAllPassWords() {
        return passWords;
    }
}
