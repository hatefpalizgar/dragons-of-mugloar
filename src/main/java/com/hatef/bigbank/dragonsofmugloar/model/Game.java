package com.hatef.bigbank.dragonsofmugloar.model;

import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private String gameId;
    private int lives;
    private int gold;
    private int level;
    private int score;
    private int highScore;
    private int turn;
    private Reputation reputation;
   
}

