package cc.antx.api;

import cc.antx.api.server.clear.CacheCleaner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApiApplication {

    public static void main(String[] args) {
        CacheCleaner cleaner = new CacheCleaner();
        Thread thread = new Thread(cleaner);
        thread.start();
        SpringApplication.run(StartApiApplication.class, args);
    }

}
