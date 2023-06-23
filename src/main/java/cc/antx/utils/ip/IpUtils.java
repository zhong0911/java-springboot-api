package cc.antx.utils.ip;

import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong
 * @date 2023-04-02 14:51
 */

public class IpUtils {
    public static final String dbPath = "src/main/resources/static/ip2region.xdb";

    public static Map<String, Object> getInfo(String ip) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> location = getLocation(ip);
            result.put("success", true);
            result.put("location", location);
        } catch (NumberFormatException e) {
            result.put("success", false);
            result.put("message", "IP address error");
            result.put("exception", e);
        }
        return result;
    }

    public static Map<String, Object> getLocation(String ip) {
        Map<String, Object> location = new HashMap<>();
        String country = getCountry(ip);
        String province = getProvince(ip);
        String city = getCity(ip);
        String operation = getOperation(ip);
        location.put("country", country);
        location.put("province", province);
        location.put("city", city);
        location.put("operation", operation);
        return location;
    }

    public static String getString(String ip) {
        Searcher searcher = null;
        String location = null;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            location = searcher.search(ip);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            searcher.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return location;
    }

    public static String getCountry(String ip) {
        String location = getString(ip);
        String[] info = location.split("\\|");
        return (info[0].equals("0")) ? "null" : info[0];
    }

    public static String getProvince(String ip) {
        String location = getString(ip);
        String[] info = location.split("\\|");
        return (info[2].equals("0")) ? "null" : info[2];
    }

    public static String getCity(String ip) {
        String location = getString(ip);
        String[] info = location.split("\\|");
        return (info[3].equals("0")) ? "null" : info[3];
    }

    public static String getOperation(String ip) {
        String location = getString(ip);
        String[] info = location.split("\\|");
        return (info[4].equals("0")) ? "null" : info[4];
    }

    public static void main(String[] args) {
        String ip = "39.156.66.10";
        System.out.println(getCountry(ip));
        System.out.println(getProvince(ip));
        System.out.println(getCity(ip));
        System.out.println(getOperation(ip));
        System.out.println(getLocation(ip));
    }
}








