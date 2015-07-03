## ELEMENTARY SORTS

### Selection Sort
> * In iteration i, find index min of smallest remaining entry.
> * Swap a[i] and a[min].
> * Scans from left to right.

```java
public class Selection
{
   public static void sort(Comparable[] a)
   {
      int N = a.length;
      for (int i = 0; i < N; i++)
      {
         int min = i;
         for (int j = i+1; j < N; j++)
            if (less(a[j], a[min]))
               min = j;
         exch(a, i, min);
      }
   }
   private static boolean less(Comparable v, Comparable w)
   {  return v.compareTo(w) < 0;  }
    ￼
   private static void exch(Comparable[] a, int i, int j)
   {
      Comparable swap = a[i];
      a[i] = a[j];
      a[j] = swap;
   }
}
```

### Selection Sort Proposition.
Selection sort uses (N–1) + (N–2) + ... + 1 + 0 ~ N^2/2 compares and N exchanges.


### Insertion Sort
> * In iteration i, swap a[i] with each larger entry to its left.
> * Scans from left to right.

```java
￼
public class Insertion
{
   public static void sort(Comparable[] a)
   {
      int N = a.length;
      for (int i = 0; i < N; i++)
         for (int j = i; j > 0; j--)
            if (less(a[j], a[j-1]))
               exch(a, j, j-1);
            else break;
   }
   private static boolean less(Comparable v, Comparable w)
   {  /* as before */  }
   private static void exch(Comparable[] a, int i, int j)
   {  /* as before */  }
}
```

### Insertion Sort Proposition.
To sort a randomly-ordered array with distinct keys, insertion sort uses ~ 1⁄4 N^2 compares and ~ 1⁄4 N^2 exchanges on average.
(best case N - 1 compares, worst case 1⁄2 N^2 compares). For partially-sorted arrays, insertion sort runs in linear time. Pf. Number of exchanges equals the number of inversions.

### ShellSort
Idea. Move entries more than one position at a time by h-sorting the array.
Shell sort. [Shell 1959] h-sort array for decreasing sequence of values of h.

```java
￼
￼
public class Shell
{
   public static void sort(Comparable[] a)
   {
      int N = a.length;
      int h = 1;

      while(h < N/3) h = 3*h + 1;//Knuth' choice

      while(h >= 1)
      {
        for(int i = h;i<N;i++){
          for(int j = i;j>=h&&less(a[j],a[j-h]);j-=h)
            exch(a,j,j-h);
        }
        h = h/3;
      }
 }

   private static boolean less(Comparable v, Comparable w)
   { /* as before */ }
   private static void exch(Comparable[] a, int i, int j)
   { /* as before */ }
}
```

### Shell Sort Proposition.
The worst-case number of compares used by shellsort with the 3x+1 increments is O(N^(3/2)).
Number of compares used by shell sort with the 3x+1 increments is at most by a small multiple of N times the # of increments used.
