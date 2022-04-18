package algs.coding_jam;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HowManyNumbersAreSmallerThanTheCurrentNumberTest {

    public static HowManyNumbersAreSmallerThanTheCurrentNumber alg = new HowManyNumbersAreSmallerThanTheCurrentNumber();

    @Test
    public void check_v1() {
        assertThat(
            alg.smallerNumbersThanCurrent(new int[] {8,1,2,2,3}),
            equalTo(new int[] {4,0,1,1,3})
        );

        assertThat(
            alg.smallerNumbersThanCurrent(new int[] {7,7,7,7}),
            equalTo(new int[] {0,0,0,0})
        );

        assertThat(
            alg.smallerNumbersThanCurrent(new int[] {6,5,4,8}),
            equalTo(new int[] {2,1,0,3})
        );
    }

    @Test
    public void check_v2() {
        assertThat(
            alg.smallerNumbersThanCurrent_v2(new int[] {8,1,2,2,3}),
            equalTo(new int[] {4,0,1,1,3})
        );

        assertThat(
            alg.smallerNumbersThanCurrent_v2(new int[] {7,7,7,7}),
            equalTo(new int[] {0,0,0,0})
        );

        assertThat(
            alg.smallerNumbersThanCurrent_v2(new int[] {6,5,4,8}),
            equalTo(new int[] {2,1,0,3})
        );
    }

    @Test
    public void check_v3() {
        assertThat(
            alg.smallerNumbersThanCurrent_v3(new int[] {8,1,2,2,3}),
            equalTo(new int[] {4,0,1,1,3})
        );

        assertThat(
            alg.smallerNumbersThanCurrent_v3(new int[] {7,7,7,7}),
            equalTo(new int[] {0,0,0,0})
        );

        assertThat(
            alg.smallerNumbersThanCurrent_v3(new int[] {6,5,4,8}),
            equalTo(new int[] {2,1,0,3})
        );
    }

}
