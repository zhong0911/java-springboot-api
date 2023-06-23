package cc.antx.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhong
 * @date 2023-03-25 23:36
 */
@Controller
public class RootController {
    @RequestMapping("/")
    public String rootPage(HttpServletResponse response) throws IOException {
        return "index.html";
    }
}

