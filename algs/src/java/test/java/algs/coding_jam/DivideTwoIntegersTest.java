package algs.coding_jam;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DivideTwoIntegersTest {

    public DivideTwoIntegers alg = new DivideTwoIntegers();

    @Test
    public void check() {
        assertThat(
            alg.divide(23523532, 475),
            is(49523)
        );

        assertThat(
            alg.divide(-2147483648, 2),
            is(-1073741824)
        );

        assertThat(
            alg.divide(-2147483647, 2147483647),
            is(-1)
        );

        assertThat(
            alg.divide(4, 2),
            is(2)
        );

        assertThat(
            alg.divide(5, 2),
            is(2)
        );

        assertThat(
            alg.divide(13, 5),
            is(2)
        );

        assertThat(
            alg.divide(2147483647, -2147483648),
            is(0)
        );

        assertThat(
            alg.divide(0, 147483648),
            is(0)
        );

        assertThat(
            alg.divide(2147483646, 2147483647),
            is(0)
        );

        assertThat(
            alg.divide(3, 3),
            is(1)
        );

        assertThat(
            alg.divide(-3, 3),
            is(-1)
        );

        assertThat(
            alg.divide(3, -3),
            is(-1)
        );
    }

    @Test
    public void check_v2() {
        assertThat(
            alg.divide_v2(23523532, 475),
            is(49523)
        );

        assertThat(
            alg.divide_v2(-2147483648, 2),
            is(-1073741824)
        );

        assertThat(
            alg.divide_v2(-2147483647, 2147483647),
            is(-1)
        );

        assertThat(
            alg.divide_v2(4, 2),
            is(2)
        );

        assertThat(
            alg.divide_v2(5, 2),
            is(2)
        );

        assertThat(
            alg.divide_v2(13, 5),
            is(2)
        );

        assertThat(
            alg.divide_v2(2147483647, -2147483648),
            is(0)
        );

        assertThat(
            alg.divide_v2(0, 147483648),
            is(0)
        );

        assertThat(
            alg.divide_v2(2147483646, 2147483647),
            is(0)
        );

        assertThat(
            alg.divide_v2(3, 3),
            is(1)
        );

        assertThat(
            alg.divide_v2(-3, 3),
            is(-1)
        );

        assertThat(
            alg.divide_v2(3, -3),
            is(-1)
        );
    }
}
