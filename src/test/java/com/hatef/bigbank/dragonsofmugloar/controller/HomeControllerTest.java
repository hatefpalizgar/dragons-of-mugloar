package com.hatef.bigbank.dragonsofmugloar.controller;

import com.hatef.bigbank.dragonsofmugloar.dto.ItemDTO;
import com.hatef.bigbank.dragonsofmugloar.model.Game;
import com.hatef.bigbank.dragonsofmugloar.model.Item;
import com.hatef.bigbank.dragonsofmugloar.model.Reputation;
import com.hatef.bigbank.dragonsofmugloar.service.HomeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    HomeService homeService;
    
    static Game testGame;
    static ItemDTO testPurchasedItem;
    static Map<String, Object> sessionAttributes;
    static Item testItem;
    static Reputation testReputation;
    
    @BeforeAll
    public static void setup() {
        testGame = new Game("G01", 3, 5, 0, 0, 1000, 1, null);
        testPurchasedItem = new ItemDTO("success", 4, 3, 0, 1);
        sessionAttributes = new HashMap<>();
        sessionAttributes.put("game", testGame);
        sessionAttributes.put("itemPurchased", testPurchasedItem);
        testItem = new Item("I01","knife", 12);
        testReputation = new Reputation("tetsPeople", "testState", "testUnderworld");
    }
    
    @Test
    public void startGame_shouldBuildGame_and_RedirectToHomePage() throws Exception {
        when(homeService.start()).thenReturn(testGame);
        mockMvc.perform(get("/game/start")).andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/game/info"))
        ;
    }
    
    @Test
    public void givenGameCreated_whenInvestigated_thenReturnGameStats() throws Exception {
        when(homeService.investigate("G01")).thenReturn(testReputation);
        mockMvc.perform(get("/game/G01/investigate"))
               .andExpect(status().isOk())
               .andExpect((model().attributeExists("reputation")))
               .andExpect(model().attribute("reputation", is(testReputation)))
               .andExpect((model().attributeExists("game")));
    }
    
    @Test
    public void givenGameCreated_whenRetrieveGameInfo_thenReturnGame() throws Exception {
        mockMvc.perform(get("/game/info").sessionAttr("game", testGame))
               .andExpect(model().attributeExists("game"))
               .andExpect(status().isOk());
    }
}
