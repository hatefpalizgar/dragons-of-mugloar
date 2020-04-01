package com.hatef.bigbank.dragonsofmugloar.controller;

import com.hatef.bigbank.dragonsofmugloar.model.Game;
import com.hatef.bigbank.dragonsofmugloar.model.Reputation;
import com.hatef.bigbank.dragonsofmugloar.service.HomeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/game")
@SessionAttributes("game")
public class HomeController {
    
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    HomeService homeService;
    
    @ApiOperation(value = "Start a new game", response = String.class, notes = "redirects to homepage")
    @GetMapping("/start")
    public String startNewGame(Model model) {
        Game game = homeService.start();
        model.addAttribute("game", game);
        log.info("redirecting... to homepage");
        return "redirect:/game/info";
    }
    
    @ApiOperation(value = "Find out how popular your dragon is", response = String.class)
    @GetMapping("/{gameId}/investigate")
    public String investigateReputation(Model model,
                                        @ApiParam(value = "Id of the game", required = true)
                                        @PathVariable(name = "gameId") String gameId,
                                        @ModelAttribute("game") Game game) {
        Reputation reputation = homeService.investigate(gameId);
        model.addAttribute("reputation", reputation)
             .addAttribute("game", game);
        return "reputation";
    }
    
    @ApiOperation(value = "See the overall statistics", response = String.class)
    @GetMapping("/info")
    public String gameInfo(@SessionAttribute("game") Game game, Model model) {
        model.addAttribute("game", game);
        return "homepage";
    }
    
    @ModelAttribute("game")
    public Game game() {
        return new Game();
    }
}
