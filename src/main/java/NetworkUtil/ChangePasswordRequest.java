package NetworkUtil;

import java.io.Serializable;

public class ChangePasswordRequest implements Serializable {
    private String clubName, newPassword;

    @Override
    public String toString() {
        return "Change password request";
    }

    public ChangePasswordRequest(String clubName, String newPassword) {
        this.clubName = clubName;
        this.newPassword = newPassword;
    }

    public String getClubName() {
        return clubName;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
