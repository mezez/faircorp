package com.emse.spring.faircorp.hello;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(OutputCaptureExtension.class)
@ExtendWith(SpringExtension.class) //used to link our test to Spring. With this annotation a Spring Context will be loaded when this test will run
public class DummyUserServiceTest {
    @Configuration // to configure how the context is loaded. In our case we added @ComponentScan("com.emse.spring.faircorp.hello") to help Spring to found our classes. In our app this scan is made by SpringBoot, but in our test SpringBoot is not loaded
    @ComponentScan("com.emse.spring.faircorp.hello")
    public static class DummyUserServiceTestConfig{}

    @Autowired
    public DummyUserService dummyUserService; //As our test has is own Spring Context we can inject inside the bean to test

    @Test
    public void testGreetingAll(CapturedOutput output){
        dummyUserService.greetAll();
        Assertions.assertThat(output).contains("Hello, Elodie!", "Hello, Charles!");
    }
}
