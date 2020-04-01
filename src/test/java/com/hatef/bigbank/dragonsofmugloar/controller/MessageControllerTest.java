package com.hatef.bigbank.dragonsofmugloar.controller;

import com.hatef.bigbank.dragonsofmugloar.dto.ItemDTO;
import com.hatef.bigbank.dragonsofmugloar.dto.MessageDTO;
import com.hatef.bigbank.dragonsofmugloar.model.Game;
import com.hatef.bigbank.dragonsofmugloar.model.Item;
import com.hatef.bigbank.dragonsofmugloar.model.Message;
import com.hatef.bigbank.dragonsofmugloar.service.MessageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MessageController.class)
class MessageControllerTest {
    
    @MockBean
    MessageService messageService;
    
    @Autowired
    MockMvc mockMvc;
    
    static Game testGame;
    static ItemDTO testPurchasedItem;
    static Map<String, Object> sessionAttributes;
    static Item testItem;
    static Message testMessage;
    
    @BeforeAll
    public static void setup() {
        testGame = new Game("G01", 3, 5, 0, 0, 1000, 1, null);
        testPurchasedItem = new ItemDTO("success", 4, 3, 0, 1);
        sessionAttributes = new HashMap<>();
        sessionAttributes.put("game", testGame);
        sessionAttributes.put("itemPurchased", testPurchasedItem);
        testItem = new Item("I01","knife", 12);
        testMessage = new Message("M01", "test message", "test reward", 10, "possible");
    }
    @Test
    public void givenGameAndAdExist_whenGetAllMessages_thenShowMessageBoard() throws Exception {
        when(messageService.getAllMessages("G01")).thenReturn(singletonList(testMessage));
        mockMvc.perform(get("/G01/messages/").sessionAttr("game", testGame))
               .andExpect(status().isOk())
               .andExpect(model().attribute("messages", notNullValue()))
               .andExpect(view().name("messageboard"));
    }
    
    @Test
    public void givenGameAndAdExist_whenSolveAd_thenReturnSuccessAndShowStats() throws Exception {
        MessageDTO solvedAd = new MessageDTO(true, "G01", 3, 50, 10, 10, 3, "solve it");
        when(messageService.solve("G01", "ad01")).thenReturn(solvedAd);
        mockMvc.perform(get("/G01/messages/solve/ad01")
                                .sessionAttr("game", testGame))
               .andExpect(status().isOk())
               .andExpect(model().attribute("result", solvedAd))
               .andExpect(view().name("statistics"));
    }
    
    @Test
    public void givenSingleLive_whenFailedSolvingAd_thenReturnGameOver() throws Exception {
        MessageDTO failedAd = new MessageDTO(true, "G01", 0, 0, 0, 1000, 2, "failed");
        when(messageService.solve("G01", "ad01")).thenReturn(failedAd);
        mockMvc.perform(get("/G01/messages/solve/ad01")
                                .sessionAttr("game", testGame))
               .andExpect(status().isOk())
               .andExpect(model().attribute("result", failedAd))
               .andExpect(view().name("gameOver"));
    }
}