package br.com.rafaelbiasi.blog.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/error/")
public class CustomErrorController implements ErrorController {

    private static final Map<Integer, String> ERROR_VIEW_MAP = createErrorViewMap();

    @RequestMapping("/")
    public String handleError(HttpServletRequest request) {
        int statusCode = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        return ERROR_VIEW_MAP.getOrDefault(statusCode, "error/generic");
    }

    private static Map<Integer, String> createErrorViewMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(403, "error/403");
        map.put(404, "error/404");
        map.put(500, "error/500");
        return map;
    }
}
