package cc.antx.api.server.data.code.query;

import java.util.Date;

import cc.antx.api.server.data.Database;


/**
 * @author zhong
 * @date 2023-03-04 18:52
 */
public class RegisterAddition {
    public static class Register {
        public static void main(String[] args) {
            String[] es = new String[]{"zhong_jia_hao@163.com", "account@gandjax.com"};
            for (String e : es) {
                System.out.println("ID: " + getId(e));
                System.out.println("Email: " + e);
                System.out.println("Code: " + getCode(e));
                System.out.println("Generation Time: " + getGenerationTime(e));
                System.out.println("ExpirationTime: " + getExpirationTime(e));
                System.out.println("Code Is Valid: " + getCodeValid(e));
                System.out.println("----------------------------------");
            }
        }

        public static String getId(String email) {
            return (String) Database.executeQuery("SELECT id FROM code WHERE type='register' AND email='" + email + "'", "id:string");
        }

        public static String getCode(String email) {
            return (String) Database.executeQuery("SELECT verification_code FROM code WHERE type='register' AND email='" + email + "'", "verification_code:string");
        }

        public static Date getGenerationTime(String email) {
            Object date = Database.executeQuery("SELECT generation_time FROM code WHERE type='register' AND email='" + email + "'", "generation_time:datetime");
            return (date != null) ? (Date) date : null;
        }

        public static Date getExpirationTime(String email) {
            Object date = Database.executeQuery("SELECT expiration_time FROM code WHERE type='register' AND email='" + email + "'", "expiration_time:datetime");
            return (date != null) ? (Date) date : null;
        }

        public static Boolean getCodeValid(String email) {
            Date res = getExpirationTime(email);
            if (res == null) return false;
            Date now = new Date();
            return now.before(res);
        }
    }
}
