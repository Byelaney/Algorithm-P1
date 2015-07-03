import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] items;
   private int N;
	
   	
   @SuppressWarnings("unchecked")
public RandomizedQueue(){
	   // construct an empty randomized queue
	   items = (Item[]) new Object[1];
	   N = 0;
   }
   public boolean isEmpty(){
	   // is the queue empty?
	   return N == 0;
   }
   public int size(){
	   // return the number of items on the queue
	   return N;
   }
   
   public void enqueue(Item item){
	   // add the item
	   if(item == null) throw new java.lang.NullPointerException();
	   if (N == items.length)  resize(2 * items.length);
	   items[N++] = item;
   }
   
   @SuppressWarnings("unchecked")
   private void resize(int capacity)
   {
      Item[] copy = (Item[]) new Object[capacity];
      for (int i = 0; i < N; i++)
         copy[i] = items[i];
      items = copy;
   }
   
   public Item dequeue(){
	   // remove and return a random item
	   if(N == 0) throw new java.util.NoSuchElementException();
	   
	   int i = StdRandom.uniform(N);  
	   Item item = items[i];
	   if(i!=N-1){
		   items[i] = items[N-1];
	   }
	   N--;
	   items[N] = null;
	   if (N > 0 && N == items.length/4) resize(items.length/2);
	   return item;
   }
   public Item sample(){
	   // return (but do not remove) a random item
	   if(N == 0) throw new java.util.NoSuchElementException();
	   
	   return items[StdRandom.uniform(N)];
   }
   public Iterator<Item> iterator(){
	   // return an independent iterator over items in random order
	   return new RandomizedQueueIterator();   
   }
   
   private class RandomizedQueueIterator implements Iterator<Item>{
	   
	   @SuppressWarnings("unchecked")
	private Item[] copyArray = (Item[]) new Object[items.length];  
       private int copyN = N;  
         
       public RandomizedQueueIterator(){  
           for(int i = 0;i < items.length;i++){  
               copyArray[i] = items[i];  
           }  
       }  
         
       public boolean hasNext(){  
           return copyN != 0;  
       }  
         
       public void remove(){   
           throw new UnsupportedOperationException();  
       }  
         
       public Item next(){  
           if (!hasNext()) throw new java.util.NoSuchElementException();  
           int offset = StdRandom.uniform(copyN);  
           Item item = copyArray[offset];  
           if(offset != copyN-1){  
               copyArray[offset] = copyArray[copyN-1];  
           }  
           copyArray[copyN-1] = null;  
           copyN--;  
           return item;  
       }  
	   
   }
   
   public static void main(String[] args){
	   // unit testing
   }
}