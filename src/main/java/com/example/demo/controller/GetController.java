package com.example.demo.controller;

import com.example.demo.ApiExample;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.gson.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.GeneralSecurityException;

@RestController
public class GetController {

    @GetMapping("/search")
    public SearchListResponse search(@RequestParam("music") String music) throws GeneralSecurityException, IOException {
        ApiExample apiExample = new ApiExample();
        return apiExample.searchMusic(music);
    }

    @GetMapping("/music")
    public VideoListResponse music(@RequestParam("id") String id) throws GeneralSecurityException, IOException {
        ApiExample apiExample = new ApiExample();
        return apiExample.listMusic(id);
    }

    @GetMapping("/test")
    public String test(@RequestParam("id") String id) throws IOException, InterruptedException {
        String key = "AIzaSyBXbztZDjnuO4h1VLo76WS0ca-jNeL49s4";
        String url = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q=" +
                id + "&key=" + key;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement otherResponse = JsonParser.parseString(response.body());
        String finalResponse = gson.toJson(otherResponse);
        return finalResponse;
    }
}
