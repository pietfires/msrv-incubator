package com.incubator.vrsa;


import com.incubator.vrsa.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class HelloWorldTest {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Test
    public void testSuccessMessageBeanInjection(){
        // Act
        String successMessage = applicationConfig.successMessage();

        //Assert
        assertEquals("This is an injected success message from a Bean in a config.", successMessage);
    }
}
