package NetworkUtil;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    String club;
    String passWord;

    public LoginRequest(String club, String passWord) {
        this.club = club;
        this.passWord = passWord;
    }

    public String getClub() {
        return club;
    }

    public String getPassWord() {
        return passWord;
    }
}
