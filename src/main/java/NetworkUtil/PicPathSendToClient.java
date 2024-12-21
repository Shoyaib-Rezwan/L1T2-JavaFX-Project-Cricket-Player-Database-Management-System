package NetworkUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PicPathSendToClient implements Serializable {
    Map<String, String> map = new HashMap<>();

    public PicPathSendToClient(Map<String, String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }
}
