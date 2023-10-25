package com.example.ziptest;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@AllArgsConstructor
@Controller
public class TestController {

    @GetMapping("/")
    public String getMain() {
        return "main";
    }
}
