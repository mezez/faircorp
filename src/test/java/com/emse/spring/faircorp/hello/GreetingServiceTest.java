package com.emse.spring.faircorp.hello;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
public class GreetingServiceTest {

    String name = "Spring";

    @Test
    public void testGreeting(CapturedOutput output){
        GreetingService greetingService = new ConsoleGreetingService();
        greetingService.greet("Spring");
        Assertions.assertThat(output.getAll()).contains("Hello, "+ name+"!");
    }
}
