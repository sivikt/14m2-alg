package algs.adt.unionfind.impl;

import algs.adt.unionfind.UF;

public class UFProvider {

    public static UF quickFind(int objectsCount) {
    	return new QuickFindUF(objectsCount);
    }

    public static UF quickUnion(int objectsCount) {
    	return new QuickUnionUF(objectsCount);
    }

    public static UF weightedQuickUnion(int objectsCount) {
    	return new WeightedQuickUnionUF(objectsCount);
    }

} 
