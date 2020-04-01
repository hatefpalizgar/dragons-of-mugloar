package com.hatef.bigbank.dragonsofmugloar.controller;

import com.hatef.bigbank.dragonsofmugloar.dto.MessageDTO;
import com.hatef.bigbank.dragonsofmugloar.model.Game;
import com.hatef.bigbank.dragonsofmugloar.model.Message;
import com.hatef.bigbank.dragonsofmugloar.service.MessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("{gameId}/messages")
public class MessageController {
    
    List<Message> messages = new ArrayList<>();
    
    @Autowired
    MessageService messageService;
    
    @GetMapping("/")
    @ApiOperation(value = "View all messages")
    public String getAll(Model model,
                         @ApiParam(value = "Id of the game", required = true)
                         @PathVariable String gameId,
                         @SessionAttribute(value = "game", required = false) Game game) {
        messages = messageService.getAllMessages(gameId);
        model.addAttribute("messages", messages)
             .addAttribute("game", game);
        return "messageboard";
    }
    
    @GetMapping("/solve/{adId}")
    @ApiOperation(value = "Solve a task")
    public String solve(Model model, @SessionAttribute("game") Game game,
                        @ApiParam(value = "Id of the game", required = true) @PathVariable String gameId,
                        @ApiParam(value = "Id of the task", required = true) @PathVariable String adId) {
        MessageDTO solvedAd = messageService.solve(gameId, adId);
        updateGameData(game, solvedAd);
        model.addAttribute("result", solvedAd)
             .addAttribute("game", game);
        if (game.getLives() == 0) {
            return "gameOver";
        }
        return "statistics";
    }
    
    private void updateGameData(@SessionAttribute("game") Game game, MessageDTO solvedAd) {
        game.setLives(solvedAd.getLives())
            .setGold(solvedAd.getGold()).setScore(solvedAd.getScore())
            .setHighScore(solvedAd.getHighScore())
            .setTurn(solvedAd.getTurn());
    }
}