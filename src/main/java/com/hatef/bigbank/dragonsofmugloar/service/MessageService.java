package com.hatef.bigbank.dragonsofmugloar.service;

import com.hatef.bigbank.dragonsofmugloar.dto.MessageDTO;
import com.hatef.bigbank.dragonsofmugloar.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    
    private final RestTemplate restTemplate;
    
    public MessageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<Message> getAllMessages(String gameId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String url = String.format("https://dragonsofmugloar.com/api/v2/%s/messages", gameId);
        List<Message> messages = null;
        try {
            messages = restTemplate.getForObject(url, ArrayList.class);
        } catch (HttpStatusCodeException e) {
            logger.error(
                    String.format("Failed with status code:%s|%s%n", e.getStatusCode(), e.getStatusText()));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return messages;
    }
    
    public MessageDTO solve(String gameId, String adId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String url = String.format("https://dragonsofmugloar.com/api/v2/%s/solve/%s", gameId, adId);
        MessageDTO result = null;
        try {
            result = restTemplate.postForObject(url, new MessageDTO(),
                                                MessageDTO.class);
        } catch (Exception ex) {
            logger.error("Error:", ex.getCause());
        }
        return result;
    }
}
