package com.hatef.bigbank.dragonsofmugloar.service;

import com.hatef.bigbank.dragonsofmugloar.model.Game;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.notNullValue;


@WebMvcTest(ItemService.class)
public class ItemServiceTest {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    ItemService itemService;
    
    Game game;
    
    @Test
    public void givenGameExists_whenListAllItems_thenReturnItemsList() {
        String url = "https://dragonsofmugloar.com/api/v2/game/start";
        game = restTemplate.postForObject(url, new Game(), Game.class);
        Assert.assertThat(itemService.getItems(game.getGameId()), notNullValue());
    }
}
