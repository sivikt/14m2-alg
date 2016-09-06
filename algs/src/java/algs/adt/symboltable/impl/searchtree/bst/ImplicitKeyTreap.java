package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.symboltable.searchtree.CartesianTree;
import algs.adt.symboltable.searchtree.Mergable;

/**
 *
 * @see <a href="http://habrahabr.ru/post/102364/" />
 *
 * @author Serj Sintsov
 */
public class ImplicitKeyTreap<Value> implements CartesianTree<Integer, Value>, Mergable<ImplicitKeyTreap<Value>> {

    @Override
    public ImplicitKeyTreap<Value> merge(ImplicitKeyTreap<Value> that) {
        return null;
    }

    @Override
    public void put(Integer pos, Value val) {

    }

    @Override
    public Value get(Integer pos) {
        return null;
    }

    @Override
    public void delete(Integer pos) {

    }

    @Override
    public boolean contains(Integer pos) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterable<Integer> keys() {
        return null;
    }

    @Override
    public Iterable<Integer> preorder() {
        return null;
    }

    @Override
    public Iterable<Integer> inorder() {
        return null;
    }

    @Override
    public Iterable<Integer> postorder() {
        return null;
    }

    @Override
    public Iterable<Integer> lvlorder() {
        return null;
    }

}
