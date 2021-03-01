/*
 * Copyright (c) 2017. Phasmid Software
 */
package edu.neu.coe.info6205.union_find;

import java.util.Random;
import java.util.function.Consumer;

/**
 * Weighted Quick Union with Path Compression
 */
public class WQUPC implements Consumer<Integer>{
    private final int[] parent;   // parent[i] = parent of i
    private final int[] size;   // size[i] = size of subtree rooted at i
    private int count;  // number of components
    private final int[] height;

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WQUPC(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        height =  new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
            //height[i] = 0;
        }
    }

    public void show() {
        for (int i = 0; i < parent.length; i++) {
            //System.out.printf("%d: %d, %d\n", i, parent[i], size[i]);
            System.out.printf("%d: %d, %d\n", i, parent[i], height[i]);
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        int root = p;
        while (root != parent[root]) {
            parent[root] = parent[parent[root]]; //For one pass 
        	root = parent[root];
        }
//        while (p != root) {
//            int newp = parent[p];
//            parent[p] = root;
//            p = newp;
//        }
        return root;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        // make smaller root point to larger one
        
/**
 * Section for height instead size
 */
//        if      (height[rootP] < height[rootQ]) parent[rootP] = rootQ;
//        else if (height[rootP] > height[rootQ]) parent[rootQ] = rootP;
//        else {
//            parent[rootQ] = rootP;
//            height[rootP]++;
//        }
/**
 * Section for size
 */
               
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
    
    
    public static int count_connections(int n)
    {
    	WQUPC uf = new WQUPC(n);
    	Random rn = new Random();
    	int connections = 0;
    	
    	while(uf.count()>1)
    	{
    		int n1 =rn.nextInt(n);
    		int n2 = rn.nextInt(n);
    		connections++;
    		if(!uf.connected(n1, n2))
    		{
    			uf.union(n1, n2);
    			connections++;
    		}
    	}
		return connections;
    }

	@Override
	public void accept(Integer arg0) {
		// TODO Auto-generated method stub
		count_connections(arg0);
	}

}
