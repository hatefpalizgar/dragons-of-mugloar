package com.hatef.bigbank.dragonsofmugloar.model;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String adId;
    private String message;
    private String reward;
    private int expiresIn;
    private String probability;
}
