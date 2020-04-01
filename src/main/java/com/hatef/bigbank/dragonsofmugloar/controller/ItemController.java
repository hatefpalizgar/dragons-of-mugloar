package com.hatef.bigbank.dragonsofmugloar.controller;

import com.hatef.bigbank.dragonsofmugloar.dto.ItemDTO;
import com.hatef.bigbank.dragonsofmugloar.model.Game;
import com.hatef.bigbank.dragonsofmugloar.model.Item;
import com.hatef.bigbank.dragonsofmugloar.service.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes("game")
@RequestMapping("/{gameId}/shop")
public class ItemController {
    
    List<Item> items = new ArrayList<>();
    
    @Autowired
    private ItemService itemService;
    
    @ApiOperation(value = "See all items available in marketplace")
    @GetMapping("/get")
    public String getAll(Model model,
                         @ApiParam(value = "Id of the game", required = true)
                         @PathVariable String gameId,
                         @SessionAttribute(value = "game", required = false) Game game) {
        items = itemService.getItems(gameId);
        model.addAttribute("items", items).addAttribute("game", game);
        return "itemList";
    }
    
    @ApiOperation(value = "Purchase an item from marketplace")
    @GetMapping("/buy/{itemId}")
    public String buy(
            Model model,
            @ApiParam(value = "Id of the game", required = true)
            @PathVariable String gameId,
            @ApiParam(value = "Id of the item", required = true)
            @PathVariable String itemId,
            @SessionAttribute(value = "game", required = false) Game game) {
        ItemDTO itemPurchased = itemService.purchase(gameId, itemId);
        updateModel(model, game, itemPurchased);
        return "order";
    }
    
    private void updateModel(Model model, Game game, ItemDTO itemPurchased) {
        model.addAttribute("itemPurchased", itemPurchased)
             .addAttribute("game", game);
        game.setLives(itemPurchased.getLives())
            .setLevel(itemPurchased.getLevel())
            .setGold(itemPurchased.getGold())
            .setTurn(itemPurchased.getTurn());
    }
}
