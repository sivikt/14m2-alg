package algs.assignments.leetcode;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveDuplicatesFromSortedListIITest {

    private RemoveDuplicatesFromSortedListII alg = new RemoveDuplicatesFromSortedListII();

    @Test
    public void check() {
        ListNode n = alg.deleteDuplicates(ListNode.createFrom("[1,2,3,3,4,4,5]"));
        assertThat(
            n != null && n.equalValuesOrder(ListNode.createFrom("[1,2,5]")),
            is(true)
        );

        n = alg.deleteDuplicates(ListNode.createFrom("[1,1,1,2,3]"));
        assertThat(
            n != null && n.equalValuesOrder(ListNode.createFrom("[2,3]")),
            is(true)
        );

        n = alg.deleteDuplicates(ListNode.createFrom("[1,1,1,2]"));
        assertThat(
            n != null && n.equalValuesOrder(ListNode.createFrom("[2]")),
            is(true)
        );

        n = alg.deleteDuplicates(ListNode.createFrom("[1,1,1,2,2]"));
        assertThat(
            n == null,
            is(true)
        );

        n = alg.deleteDuplicates(ListNode.createFrom("[]"));
        assertThat(
            n == null,
            is(true)
        );
    }

}
