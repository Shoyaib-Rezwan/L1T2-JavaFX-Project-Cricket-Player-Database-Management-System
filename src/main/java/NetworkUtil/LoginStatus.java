package NetworkUtil;

import java.io.Serializable;

public class LoginStatus implements Serializable {
    boolean loginStatus;

    public LoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }
}
