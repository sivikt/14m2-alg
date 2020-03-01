package algs.adt.symboltable.impl.searchtree;

import java.io.*;

/**
 * Represents binary tree as string.
 *
 * @author Serj Sintsov
 */
public class TreePrinter {

    public static <Key, Value, Node extends TreeNode<Key, Value, Node>> void
    paddedText(Node root, Writer out) throws IOException {
        paddedText(root, 4, out);
    }

    public static <Key, Value, Node extends TreeNode<Key, Value, Node>> void
    paddedText(Node root, int padding, Writer out) throws IOException {
        paddedText(root, padding, 0, new BufferedWriter(out));
    }

    private static <Key, Value, Node extends TreeNode<Key, Value, Node>> void
    paddedText(Node x, int padding, int lvl, Writer out) throws IOException {
        if (x == null) return;
        out.write(padding(' ', lvl * padding));
        out.write(x.toString());
        out.write('\n');
        paddedText(x.left, padding, lvl + 1, out);
        paddedText(x.right, padding, lvl+1, out);
        out.flush();
    }

    private static String padding(char c, int count) {
        StringBuilder buf = new StringBuilder(count);
        while (buf.length() < count) buf.append(c);
        return buf.toString();
    }

}
