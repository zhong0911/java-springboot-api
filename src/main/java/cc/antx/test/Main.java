package cc.antx.test;

import cc.antx.utils.qrcode.QRCodeUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.*;


public class Main {
    public static String getLocation(String ip) throws IOException {
        String dbPath = "src/main/resources/static/ip2region.xdb";
        Searcher searcher = null;
        String res = null;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            res = String.format("{\"success\": false, \"message\": \"Failed to create searcher with: %s\"}", e);
        }
        try {
            String location = searcher.search(ip);
            res = String.format("{\"success\": true, \"location\": \"%s\"}", location);
        } catch (Exception e) {
            res = String.format("{\"success\": false, \"message\": \"Failed to search: %s\"}", e);
        }
        searcher.close();
        return res;
    }


    public static void main(String[] args) {
        System.out.println(QRCodeUtils.parseQRCode("C:\\Users\\Administrator\\Desktop\\qr.png"));
    }
}