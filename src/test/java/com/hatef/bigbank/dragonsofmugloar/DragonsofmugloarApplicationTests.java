package com.hatef.bigbank.dragonsofmugloar;

import com.hatef.bigbank.dragonsofmugloar.controller.HomeController;
import com.hatef.bigbank.dragonsofmugloar.controller.ItemController;
import com.hatef.bigbank.dragonsofmugloar.controller.MessageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class DragonsofmugloarApplicationTests {
    @Autowired
    private HomeController homeController;
    
    @Autowired
    private ItemController itemController;
    
    @Autowired
    private MessageController messageController;
    
    @Test
    public void contextLoads() throws Exception {
        assertThat(homeController).isNotNull();
        assertThat(itemController).isNotNull();
        assertThat(messageController).isNotNull();
        
    }
    
}
