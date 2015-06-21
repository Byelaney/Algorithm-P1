## Steps to developing a useful algorithm.

> * Model the problem.
> * Find an algorithm to solve it.
> * Fast or memory ?
> * Figure out why.
> * Find a way to address the problem.
> * Iterate until satisfied.

## Dynamic Connectivity

### Given a set of N objects.

> * Union command: connect two objects.
> * Find/connected query: is there a path connecting the two objects?

### We assume "is connected to" is an equivalence relation:
> * Reflexive: p is connected to p.
> * Symmetric: if p is connected to q,then q is connected to p.
> * Transitive: if p is connected to q and q is connected to r,then p is connected to r.

### Connected components.
Maximal set of objects that are mutually connected.
![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/connected_components.png)

##Goal:Design efficient data structure for union-find.
> * Number of objects N can be huge.
> * Number of operations M can be huge.
> * Find queries and union commands may be intermixed.


## Quick-find [eager approach]
### Data structure.
> * Integer array id[] of size N.
> * Interpretation: p and q are connected if and only if id[p] = id[q]

```java
  private int[] a;
	public void union(int p, int q) {
		if(connected(p,q))
			return;

		if((p>=0 && p<a.length)&&(q>=0 && q<a.length)){
			int p_idx = a[p];
			for(int i = 0;i<a.length;i++){
				if(a[i] == p_idx)
					a[i] = a[q];
			}
		}
		count--;
	}

	public boolean connected(int p, int q) {
		if((p>=0 && p<a.length)&&(q>=0 && q<a.length))
			return (a[p] == a[q]);

		return false;
	}
```

## Quick-Union [eager approach]
### Data structure.
> * Integer array id[] of length N.
> * Interpretation: id[i] is parent of i.
> * Root of i is id[id[id[...id[i]...]]].

```java
private int[] id;
private int root(int i){
		while(i != id[i]) i = id[i];
		return i;
	}

	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		id[i] = j;

	}

	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}
```

## Improvements 1
### Weighted quick-union.
> * Modify quick-union to avoid tall trees.
> * Keep track of size of each tree (number of objects).
> * Balance by linking root of smaller tree to root of larger tree.

![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/weighted.png)

### Data structure.
Same as quick-union, but maintain extra array sz[i] to count number of objects in the tree rooted at i.

```java
  private int[] id;
	private int[] sz;
	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);

		if(i == j) return;
		if(sz[i] < sz[j]){
			id[i] = j;
			sz[j] += sz[i];
		}
		else{
			id[j] = i;
			sz[i] += sz[j];
		}
	}
```

## Improvements 2
Just after computing the root of p, set the id of each examined node to point to that root.(I think this makes each Node more close to the root)

### Simpler one-pass variant:
Make every other node in path point to its grandparent (thereby halving path length).
```java
   	private int root(int i){
		while(i != id[i]){
			id[i] = id[id[i]];
			i = id[i];
		}
		return i;
	}
```
### Cost model.
| algorithm  | initialize  |  union  | find |
| --------   | ---------:  | :----:  | ---- |
| quick-find | N           |  N      | 1    |
| quick-union| N           |  N      | N    |
| weighted QU| N           |  lg N   | lg N |

### Worst case.
| algorithm  | time |
| --------   | ----:|
| quick-find | M * N|
| quick-union| M * N|
| weighted QU| N + M logN|
| QU + path compression| N + M logN|
| weighted QU + path compression| N + M lg* N|

## Percolation

### A model for many physical systems:
> * N-by-N grid of sites.
> * Each site is open with probability p (or blocked with probability 1 – p).
> * System percolates if and only if top and bottom are connected by open sites.

![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/percolate.png)

### We use Monte Carlo to find p*(the p threshold)
> * initialize N-by-N grid to be blocked.
> * make a random grid open until it percolates.
> * find the percentage estimates p*.

### How to check whether an N-by-N system percolates?
> * Create an object for each site and name them 0 to N^2 - 1
> * Sites are in same component if connected by open sites.
> * Percolates iff any site on bottom row is connected to site on top row.

We could use a clever trick.
> * Percolates iff virtual top site is connected to virtual bottom site.

![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/clever_trick.png)

### How to model opening a new site?
> * Mark new site as open; connect it to all of its adjacent open sites.
