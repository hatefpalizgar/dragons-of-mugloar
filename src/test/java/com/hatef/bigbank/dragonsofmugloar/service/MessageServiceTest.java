package com.hatef.bigbank.dragonsofmugloar.service;

import com.hatef.bigbank.dragonsofmugloar.model.Game;
import com.hatef.bigbank.dragonsofmugloar.model.Message;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@WebMvcTest(MessageService.class)
class MessageServiceTest {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    MessageService messageService;
    
    @Test
    public void testMethod_getAllMessage_shouldReturnAllMessages() {
        Game mockGame = createGame();
        String gameId = mockGame.getGameId();
        List<Message> messageList = messageService.getAllMessages(gameId);
        Assert.assertNotNull(messageList);
    }
    
    private Game createGame() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String url = "https://dragonsofmugloar.com/api/v2/game/start";
        return restTemplate.postForObject(url, new Game(), Game.class);
    }
}
