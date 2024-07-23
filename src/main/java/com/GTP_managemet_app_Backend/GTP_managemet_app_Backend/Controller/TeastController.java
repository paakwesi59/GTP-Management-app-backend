package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeastController {
    @GetMapping("/hello")
    public ResponseEntity<String> teast() {
        return new ResponseEntity<>("fgjdhgkj", HttpStatus.OK);
    }
}
