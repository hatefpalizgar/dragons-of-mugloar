package com.hatef.bigbank.dragonsofmugloar.controller;

import com.hatef.bigbank.dragonsofmugloar.dto.ItemDTO;
import com.hatef.bigbank.dragonsofmugloar.model.Game;
import com.hatef.bigbank.dragonsofmugloar.model.Item;
import com.hatef.bigbank.dragonsofmugloar.service.ItemService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    
    @MockBean
    ItemService itemService;
    
    @Autowired
    MockMvc mockMvc;
    
    static Game testGame;
    static ItemDTO testPurchasedItem;
    static Map<String, Object> sessionAttributes;
    static Item testItem;
    
    @BeforeAll
    public static void setup() {
        testGame = new Game("G01", 3, 5, 0, 0, 1000, 1, null);
        testPurchasedItem = new ItemDTO("success", 4, 3, 0, 1);
        sessionAttributes = new HashMap<>();
        sessionAttributes.put("game", testGame);
        sessionAttributes.put("itemPurchased", testPurchasedItem);
        testItem = new Item("I01","knife", 12);
    }
    
    @Test
    public void givenGameCreated_whenAskedForAllItems_thenShowListOfItems() throws Exception {
        when(itemService.getItems("G01")).thenReturn(singletonList(testItem));
        MvcResult mvcResult = this.mockMvc.perform(get("/G01/shop/get").sessionAttr("game", testGame))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("itemList")).andReturn();
    }
    
    @Test
    public void givenGameCreated_whenPurchaseItem_thenDeductCurrentGolds() throws Exception {
        when(itemService.purchase("G01", "I02")).thenReturn(testPurchasedItem);
        this.mockMvc.perform(get("/G01/shop/buy/I02")
                                     .sessionAttrs(sessionAttributes))
                    .andExpect(model().attribute("game", hasProperty("gold", equalTo(4))))
                    .andExpect(status().isOk())
                    .andExpect(view().name("order"));
    }
}
