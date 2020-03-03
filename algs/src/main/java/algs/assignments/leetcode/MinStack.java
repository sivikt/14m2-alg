package algs.assignments.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Design a stack that supports push, pop, top, and
 * retrieving the minimum element in constant time.
 *
 * push(x)  -- Push element x onto stack.
 * pop()    -- Removes the element on top of the stack.
 * top()    -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 *
 *
 * Example:
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> Returns -3.
 * minStack.pop();
 * minStack.top();      --> Returns 0.
 * minStack.getMin();   --> Returns -2.
 *
 *
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
public class MinStack {
	private Deque<Integer> stack = new ArrayDeque<>();
	private Deque<Integer> minstack = new ArrayDeque<>();

	public void push(int x) {
		stack.push(x);
		if (minstack.isEmpty())
			minstack.push(x);
		else
			minstack.push(Math.min(x, getMin()));
	}

	public void pop() {
		stack.pop();
		minstack.pop();
	}

	public int top() {
		return stack.peekFirst();
	}

	public int getMin() {
		return minstack.peekFirst();
	}

}

/* How to deal with int type overflow? */
class MinStack2 {
	private Deque<Long> stack = new ArrayDeque<>();
	private Long min;

	public void push(int x) {
		if (stack.isEmpty()) {
			stack.push(0L);
			min = (long)x;
		}
		else {
			stack.push(((long)x)-min);
			min = Math.min(x, min);
		}
	}

	public void pop() {
		long d = stack.pop();
		min = (d < 0) ? (min - d) : min;
	}

	public int top() {
		return (stack.peekFirst() < 0L) ? min.intValue() : (int)(stack.peekFirst() + min);
	}

	public int getMin() {
		return min.intValue() ;
	}
}