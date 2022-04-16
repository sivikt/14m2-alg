package algs.coding_jam;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicCalculatorIITest {

    public BasicCalculatorII alg = new BasicCalculatorII();

    @Test
    public void check() {
        assertThat(
            alg.calculate("3+2*2"),
            is(7)
        );

        assertThat(
            alg.calculate(" 3/2 "),
            is(1)
        );

        assertThat(
            alg.calculate(" 3+5 / 2 "),
            is(5)
        );

        assertThat(
            alg.calculate(" 3+5*2*3*4/4/2*2+1-6"),
            is(13)
        );

        assertThat(
            alg.calculate(" 3+5*2*3*4/4/2*2+1-26"),
            is(-7)
        );
    }

    @Test
    public void check_v2() {
        assertThat(
            alg.calculate_v2("3+2*2"),
            is(7)
        );

        assertThat(
            alg.calculate_v2(" 3/2 "),
            is(1)
        );

        assertThat(
            alg.calculate_v2(" 3+5 / 2 "),
            is(5)
        );

        assertThat(
            alg.calculate_v2(" 3+5*2*3*4/4/2*2+1-6"),
            is(13)
        );

        assertThat(
            alg.calculate_v2(" 3+5*2*3*4/4/2*2+1-26"),
            is(-7)
        );
    }
}
