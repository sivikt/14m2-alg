package algs.adt.symboltable.impl.hashing;

/**
 * @author Serj Sintsov
 */
@FunctionalInterface
public interface HashFunction<Key> {

    int hash(Key key);

}
