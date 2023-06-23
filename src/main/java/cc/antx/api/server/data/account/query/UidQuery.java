package cc.antx.api.server.data.account.query;

import cc.antx.api.server.data.Database;
import cc.antx.utils.string.StringUtils;

/**
 * @author zhong
 * @date 2023-03-04 19:00
 */
public class UidQuery {

    public static int getNewUid() {
        int uid;
        do {
            uid = Integer.parseInt(StringUtils.getRandomNumber(5));
        } while (getExists(uid));
        return uid;
    }

    public static Boolean getExists(int uid) {
        Object res = Database.executeQuery("SELECT uid FROM account WHERE uid='" + uid + "'", "uid:int");
        return res != null && ((int) res) == uid;
    }
}
