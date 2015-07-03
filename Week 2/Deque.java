import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
   private class Node{
	   Item item;
	   Node next;
	   Node pre;
   }

   private Node first,last;
   private int size;

   public Deque(){
	   // construct an empty deque
	   first = null;
	   last = null;
	   size = 0;
   }
   public boolean isEmpty(){
	   // is the deque empty?
	   return size == 0;
   }
   public int size(){
	   // return the number of items on the deque
	   return size;
   }
   public void addFirst(Item item){
	   // add the item to the front
	   if(item == null) throw new java.lang.NullPointerException();

	   if(size == 0){
		   first = new Node();
		   first.next = null;
		   first.pre = null;
		   first.item = item;
		   last = first;
		   size++;
	   }else{
		   Node old = first;
		   first = new Node();
		   first.item = item;
		   first.next = old;
		   first.pre = null;
		   old.pre = first;
		   size++;
	   }

   }
   public void addLast(Item item){
	   // add the item to the end
	   if(item == null) throw new java.lang.NullPointerException();

	   if(size == 0){
		   last = new Node();
		   last.next = null;
		   last.item = item;
		   last.pre = null;
		   first = last;
		   size++;
	   }else{
		   Node old = last;
		   last = new Node();
		   old.next = last;
		   last.item = item;
		   last.next = null;
		   last.pre = old;
		   size++;
	   }

   }
   public Item removeFirst(){
	   // remove and return the item from the front
	   if(size == 0) throw new java.util.NoSuchElementException();

	   if(size == 1){
		   Item item = first.item;
		   first = null;
		   last = null;
		   size--;
		   return item;
	   }else{
		   Node old = first;
		   Item item = first.item;
		   first = old.next;
		   first.pre = null;
		   old = null;
		   size--;
		   return item;
	   }
   }
   public Item removeLast(){
	   // remove and return the item from the end
	   if(size == 0) throw new java.util.NoSuchElementException();

	   if(size == 1){
		   Item item = last.item;
		   first = null;
		   last = null;
		   size--;
		   return item;
	   }else{
		   Node pre = last.pre;
		   Item item = last.item;
		   last = null;
		   pre.next = null;
		   last = pre;
		   size--;
		   return item;
	   }
   }
   public Iterator<Item> iterator(){
	   // return an iterator over items in order from front to end
	   return new DequeIterator();
   }
    private class DequeIterator implements Iterator<Item>{

	Node current = first;
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return current != null;
	}

	@Override
	public Item next() {
		// TODO Auto-generated method stub
		if(current == null) throw new java.util.NoSuchElementException();

		Item item = current.item;
		current = current.next;
		return item;
	}

	public void remove(){
		throw new java.lang.UnsupportedOperationException();
	}

   }

   public static void main(String[] args){
	   // unit testing

   }

}
