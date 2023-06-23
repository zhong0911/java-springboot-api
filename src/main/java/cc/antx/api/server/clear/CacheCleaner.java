package cc.antx.api.server.clear;

import java.io.File;

public class CacheCleaner implements Runnable {
    private int count = 0;

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while (true) {
            try {
                File folder = new File("C:/www/temp/api/image/qr/");
                File[] files = folder.listFiles();
                for (File file : files) {
                    if (file.isFile() && file.exists()) file.delete();
                }
                this.count++;
                System.out.println("已完成第" + count + "次清理");
                Thread.sleep(1000 * 60 * 30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        CacheCleaner cleaner = new CacheCleaner();
        Thread thread = new Thread(cleaner);
        thread.start();
    }
}
