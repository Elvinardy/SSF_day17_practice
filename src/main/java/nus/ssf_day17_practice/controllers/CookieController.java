package nus.ssf_day17_practice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import nus.ssf_day17_practice.service.FortuneCookie;

@RestController
@RequestMapping(path="/cookies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CookieController {

    @Autowired
    private FortuneCookie fortuneCookie;

    @GetMapping
    public ResponseEntity<String> getCookie (
        @RequestParam(defaultValue = "1") Integer count) {
/*         JsonObjectBuilder cookieObject = Json.createObjectBuilder();
        JsonArrayBuilder cookieArray = Json.createArrayBuilder(); */

        if ((count < 1) || (count > 10)) 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Json.createObjectBuilder()
                    .add("error", "count must be between 1 to 10")
                    .build()
                    .toString()                 
            );
        List<String> cookies = this.fortuneCookie.getCookies(count);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        cookies.stream()    // adding cookies into the array builder
            .forEach(v -> arrBuilder.add(v));   // same as a For Each loop, best to use a stream
            /*  for (String c : cookies) {
                arrBuilder.add(c);
            } */
        JsonObjectBuilder jsonObj = Json.createObjectBuilder()
                    .add("cookies", arrBuilder.build())
                    .add("timestamp", System.currentTimeMillis()); 
/* 
        final JsonObject resp = Json.createObjectBuilder().add(
            "getting cookie..",fortuneCookie.getCookie(count),System.currentTimeMillis()); */
    
            return ResponseEntity.ok(jsonObj.build().toString());
    
        }
    }

    

