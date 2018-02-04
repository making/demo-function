package functions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GreeterTest {

    @Test
    public void apply() {
        Greeter greeter = new Greeter();
        assertThat(greeter.apply("World")).isEqualTo("Hello World");
    }
}