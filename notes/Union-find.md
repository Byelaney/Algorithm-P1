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

### We assume "is connected to" is an equivalence relation:(就像离散数学中的一样)
> * Reflexive: p is connected to p.
> * Symmetric: if p is connected to q,then q is connected to p.
> * Transitive: if p is connected to q and q is connected to r,then p is connected to r.

### Connected components.
Maximal set of objects that are mutually connected.

##Goal:
Design efficient data structure for union-find.
> * Number of objects N can be huge.
> * Number of operations M can be huge.
> * Find queries and union commands may be intermixed.


## Quick-find [eager approach]
### Data structure.
> * Integer array id[] of size N.
> * Interpretation: p and q are connected if and only if id[p] = id[q]


### Cost model.
| algorithm  | initialize  |  union  | find |
| --------   | ---------:  | :----:  | ---- |
| quick-find | N           |  N      | 1    |
| quick-union| N           |  N      | N    |
