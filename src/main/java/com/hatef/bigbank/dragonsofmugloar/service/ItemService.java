package com.hatef.bigbank.dragonsofmugloar.service;

import com.hatef.bigbank.dragonsofmugloar.dto.ItemDTO;
import com.hatef.bigbank.dragonsofmugloar.model.Item;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class ItemService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ItemService.class);
    
    private final RestTemplate restTemplate;
    
    public ItemService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<Item> getItems(String gameId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String url = String.format("https://dragonsofmugloar.com/api/v2/%s/shop", gameId);
        List<Item> items = null;
        try {
            items = restTemplate.getForObject(url, ArrayList.class);
        } catch (HttpStatusCodeException e) {
            logger.error(
                    String.format("Failed with status code:%s|%s%n", e.getStatusCode(), e.getStatusText()));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return items;
    }
    
    public ItemDTO purchase(String gameId, String itemId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String url = String
                .format("https://dragonsofmugloar.com/api/v2/%s/shop/buy/%s", gameId, itemId);
        ItemDTO itemPurchased = null;
        try {
            itemPurchased = restTemplate.postForObject(url, new ItemDTO(), ItemDTO.class);
        } catch (HttpStatusCodeException e) {
            logger.error(
                    String.format("Failed with status code:%s|%s%n", e.getStatusCode(), e.getStatusText()));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return itemPurchased;
    }
}
