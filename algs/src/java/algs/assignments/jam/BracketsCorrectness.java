package algs.assignments.jam;

import algs.adt.stack.Stack;
import algs.adt.stack.impl.LinkedListStack;

/**
 * Check that input string of brackets sequence is correct.
 * ({}()(([])()))[{}]
 *
 * @author Serj Sintsov
 */
public class BracketsCorrectness {

    public static class BracketEntry {
        private char bracket;
        private int pos;

        public BracketEntry(char bracket, int pos) {
            this.bracket = bracket;
            this.pos = pos;
        }

        public char getBracket() {
            return bracket;
        }

        public int getPos() {
            return pos;
        }
    }

    public static void check(String expr) {
        Stack<BracketEntry> brackets = new LinkedListStack<>();

        for (int i = 0; i < expr.length(); i++) {
            Character c = expr.charAt(i);

            switch (c) {
                case '[':case '(':case '{':case '<':
                    brackets.push(new BracketEntry(c, i+1));
                    break;
                case ']':
                    match('[', brackets.pop().getBracket(), i);
                    break;
                case ')':
                    match('(', brackets.pop().getBracket(), i);
                    break;
                case '}':
                    match('{', brackets.pop().getBracket(), i);
                    break;
                case '>':
                    match('<', brackets.pop().getBracket(), i);
                    break;
            }
        }

        if (brackets.size() != 0)
            throw new RuntimeException("Unclosed bracket '" + brackets.peek().getBracket() + "' at col " + brackets.peek().getPos());
    }

    private static void match(char expected, char actual, int srcPos) {
        if (expected != actual)
            throw new RuntimeException("Incorret bracket '" + actual + "' at col " + srcPos);
    }

    public static void main(String[] args) {
        check("({}()(([])()))[{}]");
        check("((({}()(([])()))[{}])");
    }
}
