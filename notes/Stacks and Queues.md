## Fundamental data types
> * Value: collection of objects.
> * Operations: insert, remove, iterate, test if empty.
> * Intent is clear when we insert.
> * Which item do we remove?

### Separate interface and implementation
> * Client can't know details of implementation ⇒client has many implementation from which to choose.
> * Implementation can't know details of client needs ⇒ many clients can re-use the same implementation.
> * Design: creates modular, reusable libraries.
> * Performance: use optimized implementation where it matters.

## Stack API
```java
public class StackOfStrings{
    StackOfStrings(){} //create an empty stack
    void push(String item){} //insert a new string onto stack
    String pop(){} //remove and return the string most recently added
    boolean isEmpty(){} //is the stack empty?
    int size(){} //number of strings on the stack
    }
```

### Stack linked-list representation
### stack-linked-list-pop
![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/stack-linked-list-pop.png)
### stack-linked-list-push
![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/stack-linked-list-push.png)


```java
public class LinkedStackOfStrings{
    private Node first = null;

    private Class Node{
      String item;
      Node next;
    }

    public boolean isEmpty(){
      return first == null;
    }

    // just constant time
    public void push(String item){
      Node oldFirst = first;
      first = new Node();
      first.item = item;
      first.next = oldFirst;
    }
    // just constant time
    public String pop(){
      String item = first.item;
      first = first.next;
      return item;
    }
    }
```

### Stack array representation
> * Use array s[] to store N items on stack.
> * push(): add new item at s[N].
> * pop(): remove item from s[N-1].

```java
public class FixedCapacityStackOfStrings{
  private String [] s;
  private int N = 0;

  public FixedCapacityStackOfStrings(int N){
    s = new String[N];
  }

  public boolean isEmpty(){
    return N == 0;
  }

  public void push(String item){
    s[N++] = item;
  }

  public String pop(){
    String item = s[--N];
    s[N] = null;
    return item;
  }
}
```

### Stack: resizing-array implementation
Q: We need to change array size when capacity is not enough.
A: If array is full, create a new array of twice the size, and copy items.

``` java
public class ResizingArrayStackOfStrings{
  private void resize(int capacity){
    String copy = new String[capacity];
    for(int i = 0;i<N;i++)
      copy[i] = s[i];
      s = copy;
  }

  public void push(String item){
    if(s.length == N) resize(N * 2);
    s[N++] = item;
  }
}
```

Q: How to shrink array?
A: pop(): halve size of array s[] when array is one-half full.

This is not a good idea,consider push-pop-push-pop-... sequence when array is full.
So we get this solution: pop(): halve size of array s[] when array is one-quarter full.

``` java
public String pop()
{
   String item = s[--N];
   s[N] = null;
   if (N > 0 && N == s.length/4) resize(s.length/2);
   return item;
}
```

### Time cost
![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/resizing-time-cost.png)


### Linked-list implementation.
> * Every operation takes constant time in the worst case.
> * Uses extra time and space to deal with the links.

### Resizing-array implementation.
> * Every operation takes constant amortized time.
> * Less wasted space.

### Queue

## Queue API
```java
public class QueueOfStrings{
    QueueOfStrings(){}
    void enqueue(String item){}
    String dequeue(){}
    boolean isEmpty(){}
    int size(){} //not recommended
    }
```

### queue-linked-list-dequeue
![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/queue-linked-list-dequeue.png)

### queue-linked-list-enqueue
![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/queue-linked-list-enqueue.png)

```java
public class LinkedQueueOfStrings{
    private Node first,last;
    private class Node{
      String item;
      Node next;
    }

    void enqueue(String item){
      Node oldLast = last;
      last = new Node();
      last.item = item;
      last.next = null;
      if(isEmpty()) first = last;
      else oldLast.next = last;
    }

    String dequeue(){
      String item = first.item;
      first = first.next;
      if(isEmpty()) last = null;
      return item;
    }

    public boolean isEmpty(){
      return first == null;
    }
    int size(){} //not recommended
    }
```

### Queue: resizing array implementation
> * Use array q[] to store items in queue.
> * enqueue(): add new item at q[tail].
> * dequeue(): remove item from q[head].
> * Update head and tail modulo the capacity.
> * Add resizing array.

## Java generics.
> * Avoid casting in client.
> * Discover type mismatch errors at compile-time instead of run-time.

![image](https://github.com/Byelaney/Algorithm-P1/raw/master/notes/pictures/java-generics.png)

## Iteration
Design challenge: Support iteration over stack items by client, without revealing the internal representation of the stack.
Java solution: Make stack implement the java.lang.Iterable interface.

```java
public class Stack<Item> implements Iterable<Item>{
  public Iterator<Item> iterator()
    { return new ReverseArrayIterator(); }

    private class ReverseArrayIterator implements Iterator<Item>
    {
      private int i = N;
      public boolean hasNext() {  return i > 0;        }
      public void remove()     {  /* not supported */  }
      public Item next()       {  return s[--i];       }
    }
}
```

## Stack applications
> * Parsing in a compiler.
> * Java virtual machine.
> * Undo in a word processor.
> * Back button in a Web browser.
> * PostScript language for printers.
> * Implementing function calls in a compiler.

## Two-stack algorithm. [E. W. Dijkstra]
> * Value: push on to the value stack.
> * Operator: push on to the operation stack.
> * Left parenthesis: ignore.
> * Right parenthesis: pop operator and two values;push the result of applying that operator to those values onto the operand stack.

( 1 + (( 2 + 3 ) * ( 4 * 5 ) ) )

| value stack  | operator stack |
| ------------ | --------------:|
|      1       |                |
|      1       |       +        |
|      1       |       +        |
|      1       |       +        |
|     2,1      |       +        |
|     2,1      |      +,+       |
|    3,2,1     |      +,+       |
|     5,1      |       +        |
|     5,1      |      *,+       |
|     5,1      |      *,+       |
|    4,5,1     |      *,+       |
|    4,5,1     |     * , * , +  |
|   5,4,5,1    |     * , * , +  |
|    20,5,1    |      *,+       |
|     100,1    |       +        |
|     101      |                |
