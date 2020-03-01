package algs.adt.symboltable.impl.searchtree.bst;

import static algs.adt.symboltable.impl.SymbolTableTest.*;

/**
 * Bottom-up implementation of insert and delete methods.
 * In B-Up insertion, "ordinary" BST insertion is used, followed by correction
 * of the tree on the way back up to the root. This is most easily done recursively.
 *
 * Supports only unique keys.
 * Does not thread-safe.
 *
 * @see {@link RBTree}.
 *
 * @author Serj Sintsov
 */
public class BottomUpRBTree<Key extends Comparable<? super Key>, Value>
        extends RBTree<Key, Value>
{

    private static class BooleanFlag {
        private boolean state;

        public static BooleanFlag flag() {
            BooleanFlag flag = new BooleanFlag();
            flag.off();
            return flag;
        }

        public void on() {
            state = true;
        }

        public void off() {
            state = false;
        }

        public boolean isOn() {
            return state;
        }
    }

    @Override
    public void put(Key key, Value val) {
        checkKeyNotNull(key);
        root = put(root, key, val, false); // assume root is right link of virtual node
        root.colorBlack();             // satisfy rule 2
        assertIsRbBst();
    }

    private Node<Key, Value> put(Node<Key, Value> p, Key k, Value v, boolean isLeft) {
        if (p == null) {
            size += 1;
            return new Node<>(k, v, RED);
        }

        // Propagate red up. It's safe to keep both of the children black and parent right
        // (no red or black violation at current level, but may violate red rule in parent of p).
        // Also it reduces the number of cases for next steps (simplifies attaching of new red nodes
        // red violation).
        if (isRed(p.left) && isRed(p.right))
            p.flipRedColors();

        int cmp = k.compareTo(p.key);
        if      (cmp == 0) p.value = v;
        else if (cmp < 0)  p = putLeft(p, k, v,  isLeft);
        else               p = putRight(p, k, v, isLeft);

        return p;
    }

    private Node<Key, Value> putLeft(Node<Key, Value> p, Key k, Value v, boolean isLeft) {
        p.left = put(p.left, k, v, true);

        // To simplify rotation (double rotation) keep child outside its grandparent (if there's red violation)
        //   avoid:       accept:   accept:
        //     G              G       G
        //    /  \           / \     / \
        //   a   P-r        P   a   a   P
        //      /  \       / \         / \
        //     C-r  b     C   b       b   C
        //
        if (isRed(p) && isRed(p.left) && !isLeft)
            p = p.rotateRight();

        // Now parent and child is outside (left) of grandparent
        //
        //       G  <-p
        //      /  \
        //     P-r  a
        //    /  \
        //   C-r  b
        //  /  \
        // d    v
        //
        // Fix P-r:C-r violation. Subtrees d and v are OK since (they are leaves or modified bottom-up before). Subtrees
        // a and b is OK since we modified only left subtree.
        // If G is red will fix this violation on recursion up-way.
        if (isRed(p.left) && isRed(p.left.left)) {
            p = p.rotateRight();
            p.colorBlack();
            p.right.colorRed();
        }

        return p;
    }

    /**
     * Mirrors {@link #putLeft(Node, Comparable, Object, boolean)}
     */
    private Node<Key, Value> putRight(Node<Key, Value> p, Key k, Value v, boolean isLeft) {
        p.right = put(p.right, k, v, false);

        if (isRed(p) && isRed(p.right) && isLeft)
            p = p.rotateLeft();

        if (isRed(p.right) && isRed(p.right.right)) {
            p = p.rotateLeft();
            p.colorBlack();
            p.left.colorRed();
        }

        return p;
    }

    @Override
    public void delete(Key k) {
        checkKeyNotNull(k);
        root = delete(root, k, BooleanFlag.flag());
        if (!isEmpty()) root.colorBlack();
        assertIsRbBst();
    }

    private Node<Key, Value> delete(Node<Key, Value> p, Key k, BooleanFlag doneFlag) {
        if (p == null) return null;

        int cmp = k.compareTo(p.key);
        if (cmp > 0) {
            p.right = delete(p.right, k, doneFlag);
            return fixDeletion(p, false, doneFlag);
        }
        else if (cmp < 0) {
            p.left  = delete(p.left,  k, doneFlag);
            return fixDeletion(p, true, doneFlag);
        }
        else
            return replaceAndDelete(p, doneFlag);
    }

    private Node<Key, Value> replaceAndDelete(Node<Key, Value> p, BooleanFlag doneFlag) {
        // delete node with at most one child
        if (p.left == null || p.right == null) {
            size -= 1;

            Node<Key, Value> replacement = p.child(p.left != null);

            if (isRed(p)) {                // just replace red node with its child (no violations)
                doneFlag.on();
                return replacement;
            }
            else if (isRed(replacement)) { // replace deleted black node with its
                replacement.colorBlack();  // repainted into black red child       (no violations)
                doneFlag.on();
                return replacement;
            }
            else return replacement;       // otherwise fix black violation
        }
        // delete node with exactly two children
        else {
            // find predecessor of p and assign its value to p
            // and continue removing predecessor
            Node<Key, Value> predecessor = max(p.left);
            p.key   = predecessor.key;
            p.value = predecessor.value;
            p.left  = delete(p.left, predecessor.key, doneFlag);
            return p;
        }
    }

    private Node<Key, Value> fixDeletion(Node<Key, Value> p, boolean isLeft, BooleanFlag doneFlag) {
        if (doneFlag.isOn()) return p;

        Node<Key, Value> virginSibling = p.child(!isLeft);

        //     p.*
        //   /    \
        //  s.b   c.b
        //
        if (virginSibling != null && !isRed(virginSibling)) {
            // if two sibling's children are black
            // then correct black height in s and c subtrees
            if (!isRed(virginSibling.left) && !isRed(virginSibling.right)) {

                //       p.r             p.b
                //     /    \          /    \
                //    s.b   c.b  ->   s.r   c.b
                //   /   \           /   \
                // s1.b s2.b       s1.b s2.b
                //
                if (isRed(p))
                    doneFlag.on(); // else fix up black violation in p

                p.colorBlack();
                virginSibling.colorRed();

                return p;
            }
            else {
                Node<Key, Value> outerChild = virginSibling.child(!isLeft);
                Node<Key, Value> oldParent  = p;

                //      p.*               s.*
                //     /   \             /   \
                //    s.b   c.b    ->   s1.b  p.b
                //   /   \                   /  \
                // s1.r  s2.*              s2.* c.b
                //
                if (isRed(outerChild)) {
                    p = p.rotateTo(isLeft);
                }
                //      p.*                p.*              s2.*
                //     /   \             /    \            /    \
                //    s.b   c.b    ->   s2.r  c.b    ->   s.b    p.b
                //   /   \             /                 /        \
                // s1.b s2.r          s.b               s1.b      c.b
                //                   /
                //                  s1.b
                //
                else {
                    virginSibling = virginSibling.rotateTo(!isLeft);
                    p.setChild(!isLeft, virginSibling);
                    p = p.rotateTo(isLeft);
                }

                p.colorTo(oldParent.color());
                oldParent.colorBlack();
                doneFlag.on();
                return p;
            }
        }
        //       p.b
        //     /    \
        //    s.r   c.b
        //   /   \
        // s1.b  s2.b
        //
        else if (virginSibling != null) {
            Node<Key, Value> innerChild = virginSibling.child(isLeft);

            //         p.b                   s.b
            //       /    \                /    \
            //      s.r   c.b             s1.b   p.b
            //     /   \           ->           /   \
            //   s1.b  s2.b                   s2.r   c.b
            //        /    \                 /    \
            //       s21.b s22.b           s21.b  s22.b
            //
            if (!isRed(innerChild.left) && !isRed(innerChild.right)) {
                p = p.rotateTo(isLeft);
                virginSibling.colorBlack();
                innerChild.colorRed();

                doneFlag.on();
                return p;
            }

            //         p.b                   p.b                  s2.b
            //       /    \                /    \              /         \
            //      s.r   c.b             s2.b   c.b          s.r         p.b
            //     /   \           ->    /   \          ->   /   \       /   \
            //   s1.b  s2.b             s.r  s22.*          s1.b s21.b  s22.* c.b
            //        /    \           /   \
            //       s21.r s22.*     s1.b s21.r
            //
            else {
                if (isRed(innerChild.child(isLeft))) {
                    innerChild = innerChild.rotateTo(!isLeft);
                    innerChild.colorBlack();
                }

                virginSibling = virginSibling.rotateTo(!isLeft);
                p.setChild(!isLeft, virginSibling);
                p = p.rotateTo(isLeft);

                doneFlag.on();
                return p;
            }
        }

        return p;
    }

    private Node<Key, Value> max(Node<Key, Value> x) {
        if (x == null) return null;
        while (true)
            if (x.right == null) return x;
            else x = x.right;
    }


    /**
     * Testing
     */
    public static void main(String[] args) throws Exception {
        testSearch(new BottomUpRBTree<>(), 3_000_000, intGen());
        testInsert(new BottomUpRBTree<>(), 3_000_000, intGen());
        testDelete(new BottomUpRBTree<>(), 3_000_000, 0.3, intGen());
    }

}
