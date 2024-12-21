package Database;

import java.io.*;
import java.util.Map;

public class FileOperations {
    public String inputFile;
    private static final String passwordFile = "src/main/resources/Files/Passwords.txt";
    private static final String picPathFile = "src/main/resources/Files/PicPaths.txt";


    public FileOperations(String filePath) {
        inputFile = filePath;
    }

    public void readPlayers(PlayerList players) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        while (true) {
            String temp = br.readLine();
            if (temp != null) {
                Player newPlayer = new Player();
                String[] playerData = temp.split(",");
                newPlayer.setName(playerData[0]);
                newPlayer.setCountry(playerData[1]);
                newPlayer.setAge(Integer.parseInt(playerData[2]));
                newPlayer.setHeight((Float.parseFloat(playerData[3])));
                newPlayer.setClub(playerData[4]);
                newPlayer.setPosition(playerData[5]);
                if (!playerData[6].isEmpty())
                    newPlayer.setJersey(Integer.parseInt(playerData[6]));
                else
                    newPlayer.setJersey(-1);
                newPlayer.setSalary(Integer.parseInt(playerData[7]));
                newPlayer.setPrice(Long.parseLong(playerData[8]));
                players.addPlayer(newPlayer);
            } else
                break;
        }
        br.close();
    }

    public void writePlayers(PlayerList players) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile));
        for (int i = 0; i < players.getPlayers().size(); i++) {
            Player x = players.getPlayers().get(i);
            bw.write(x.getName() + "," + x.getCountry() + "," + x.getAge() + "," + x.getHeight() + "," + x.getClub() + "," + x.getPosition() + ",");
            if (x.getJersey() != -1)
                bw.write(String.valueOf(x.getJersey()));
            bw.write("," + x.getSalary() + "," + x.getPrice());
            if (i + 1 != players.getPlayers().size())
                bw.write(System.lineSeparator());
        }
        bw.close();
    }

    public void readPassWords(Club clubs) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(passwordFile));
        while (true) {
            String temp = br.readLine();
            if (temp != null) {
                String[] parts = temp.split(",");
                clubs.setPassWords(parts[0], parts[1]);
            } else break;
        }
        br.close();
    }

    public void readPicPaths(Map<String, String> map) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(picPathFile));
        while (true) {
            String temp = br.readLine();
            if (temp != null) {
                String[] temp2 = temp.split(",");
                map.put(temp2[0], temp2[1]);
            } else break;
        }
        br.close();
    }

    public void writePasswords(Club club) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(passwordFile));
        Map<String, String> passwords = club.getAllPassWords();
        int k = 0;
        for (Map.Entry<String, String> entries : passwords.entrySet()) {
            k++;
            String clubName = entries.getKey();
            String password = entries.getValue();
            bw.write(clubName + "," + password);
            if (k < passwords.size()) {
                bw.write(System.lineSeparator());
            }
        }
        bw.close();
    }
}