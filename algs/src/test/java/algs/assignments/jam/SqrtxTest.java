package algs.assignments.jam;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SqrtxTest {

    public Sqrtx alg = new Sqrtx();

    @Test
    public void check() {
        assertThat(
            alg.mySqrt(2),
            is(1)
        );

        assertThat(
            alg.mySqrt(1),
            is(1)
        );

        assertThat(
            alg.mySqrt(0),
            is(0)
        );

        assertThat(
            alg.mySqrt(5),
            is(2)
        );

        assertThat(
            alg.mySqrt(9),
            is(3)
        );

        assertThat(
            alg.mySqrt(42352),
            is(205)
        );
    }

    @Test
    public void check_v2() {
        assertThat(
            alg.mySqrt_v2(2),
            is(1)
        );

        assertThat(
            alg.mySqrt_v2(1),
            is(1)
        );

        assertThat(
            alg.mySqrt_v2(0),
            is(0)
        );

        assertThat(
            alg.mySqrt_v2(5),
            is(2)
        );

        assertThat(
            alg.mySqrt_v2(9),
            is(3)
        );

        assertThat(
            alg.mySqrt_v2(42352),
            is(205)
        );
    }

    @Test
    public void check_v3() {
        assertThat(
            alg.mySqrt_v3(2),
            is(1)
        );

        assertThat(
            alg.mySqrt_v3(1),
            is(1)
        );

        assertThat(
            alg.mySqrt_v3(0),
            is(0)
        );

        assertThat(
            alg.mySqrt_v3(5),
            is(2)
        );

        assertThat(
            alg.mySqrt_v3(9),
            is(3)
        );

        assertThat(
            alg.mySqrt_v3(42352),
            is(205)
        );
    }
}
