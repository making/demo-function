package com.example.demofunction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoFunctionApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        String res = restTemplate.postForObject("/greeter", "World", String.class);
        assertThat(res).isEqualTo("Hello World");
    }

}
