package com.intecap.EquiposAPI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String redirectToSwagger() {
        // Redirige la raíz (/) a Swagger UI
        return "redirect:/swagger-ui/index.html";
    }
}
