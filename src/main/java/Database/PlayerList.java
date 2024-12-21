package Database;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.io.Serializable;
import java.util.List;

public class PlayerList implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private final List<Player> players = new ArrayList<>();
    Map<String, Integer> countryMap = new HashMap<>();
    Map<String, Integer> clubTotalSalary = new HashMap<>();
    public boolean newPlayerAdded = false;

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
        String country = newPlayer.getCountry();
        String club = newPlayer.getClub().toLowerCase();
        if (countryMap.get(country) == null) {
            countryMap.put(country, 1);
        } else
            countryMap.put(country, countryMap.get(country) + 1);
        if (clubTotalSalary.get(club) == null) {
            clubTotalSalary.put(club, newPlayer.getSalary());
        } else {
            clubTotalSalary.put(club, clubTotalSalary.get(club) + newPlayer.getSalary());
        }
    }

    public Player searchByName(String name) {
        for (Player x : players) {
            if (x.getName().equalsIgnoreCase(name))
                return x;
        }
        return null;
    }

    public List<Player> searchByCountry(String country) {
        List<Player> result = new ArrayList<>();
        for (Player x : players) {
            if (x.getCountry().equalsIgnoreCase(country))
                result.add(x);
        }
        return result;
    }

    public List<Player> searchByClubAndCountry(String country, String club) {
        List<Player> result = new ArrayList<>();
        for (Player x : players) {
            if (x.getCountry().equalsIgnoreCase(country) && (x.getClub().equalsIgnoreCase(club) || club.equals("ANY")))
                result.add(x);
        }
        return result;
    }

    public List<Player> searchByPosition(String position) {
        List<Player> result = new ArrayList<>();
        for (Player x : players) {
            if (x.getPosition().equalsIgnoreCase(position))
                result.add(x);
        }
        return result;
    }

    public List<Player> searchBySalary(int low, int high) {
        List<Player> result = new ArrayList<>();
        for (Player x : players) {
            if (x.getSalary() >= low && x.getSalary() <= high)
                result.add(x);
        }
        return result;
    }

    public Map<String, Integer> playerCount() {
        return countryMap;
    }

    public List<Player> searchMaxSalary(String club) {
        List<Player> result = new ArrayList<>();
        int maxSalary = 0;
        for (Player x : players) {
            if (x.getClub().equalsIgnoreCase(club) && x.getSalary() > maxSalary) {
                maxSalary = x.getSalary();
            }
        }
        for (Player x : players) {
            if (x.getClub().equalsIgnoreCase(club) && x.getSalary() == maxSalary) {
                result.add(x);
            }
        }
        return result;
    }

    public List<Player> searchMaxAge(String club) {
        List<Player> result = new ArrayList<>();
        int maxAge = 0;
        for (Player x : players) {
            if (x.getClub().equalsIgnoreCase(club) && x.getAge() > maxAge) {
                maxAge = x.getAge();
            }
        }
        for (Player x : players) {
            if (x.getClub().equalsIgnoreCase(club) && x.getAge() == maxAge) {
                result.add(x);
            }
        }
        return result;
    }

    public List<Player> searchMaxHeight(String club) {
        List<Player> result = new ArrayList<>();
        float maxHeight = 0;
        for (Player x : players) {
            if (x.getClub().equalsIgnoreCase(club) && x.getHeight() > maxHeight) {
                maxHeight = x.getHeight();
            }
        }
        for (Player x : players) {
            if (x.getClub().equalsIgnoreCase(club) && x.getHeight() == maxHeight) {
                result.add(x);
            }
        }
        return result;
    }

    public long getTotalSalary(String club) {
        String temp = club.toLowerCase();
        if (clubTotalSalary.get(temp) == null) return -1;
        return (long) clubTotalSalary.get(temp) * (long) 52;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> searchByCountryAndSalaryRange(String country, int lower, int upper) {
        List<Player> result = new ArrayList<>();
        for (Player x : players) {
            if (x.getCountry().equalsIgnoreCase(country) && lower <= x.getSalary() && upper >= x.getSalary()) {
                result.add(x);
            }
        }
        return result;
    }

    public void removePlayer(Player x) {
        int i = 0;
        for (Player y : players) {
            if (y.getName().equals(x.getName()))
                break;
            i++;
        }
        players.remove(i);
    }

    public boolean containsPlayer(Player x) {
        for (Player y : players) {
            if (y.getName().equalsIgnoreCase(x.getName()))
                return true;
        }
        return false;
    }
}
