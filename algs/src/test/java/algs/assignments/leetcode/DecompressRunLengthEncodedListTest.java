package algs.assignments.leetcode;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DecompressRunLengthEncodedListTest {

    public DecompressRunLengthEncodedList alg = new DecompressRunLengthEncodedList();

    @Test
    public void check() {
        assertThat(
            alg.decompressRLElist(new int[] {1,2,3,4}),
            equalTo(new int[] {2,4,4,4})
        );
    }

}
