package com.hatef.bigbank.dragonsofmugloar.service;

import com.hatef.bigbank.dragonsofmugloar.model.Game;
import com.hatef.bigbank.dragonsofmugloar.model.Reputation;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Service
public class HomeService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HomeService.class);
    
    private final RestTemplate restTemplate;
    
    public HomeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public Game start() {
        logger.info("starting a new game...");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String url = "https://dragonsofmugloar.com/api/v2/game/start";
        Game game = null;
        try {
            game = restTemplate.postForObject(url, new Game(), Game.class);
            logger.info("game created successfully");
        } catch (HttpStatusCodeException e) {
            logger.error(
                    String.format("Failed with status code:%s|%s%n", e.getStatusCode(), e.getStatusText()));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return game;
    }
    
    public Reputation investigate(String gameId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String url = "https://dragonsofmugloar.com/api/v2/" + gameId + "/investigate/reputation";
        Reputation reputation = null;
        try {
            reputation = restTemplate.postForObject(url, new Reputation(), Reputation.class);
        } catch (HttpStatusCodeException e) {
            logger.error(
                    String.format("Failed with status code:%s|%s%n", e.getStatusCode(), e.getStatusText()));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return reputation;
    }
}
