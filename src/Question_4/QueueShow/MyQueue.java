package Question_4.QueueShow;

import Question_4.LinkedListShow.MyLinkedList;

public class MyQueue<E> {
	MyLinkedList<E> list = null;
	
	public MyQueue() {
		list = new MyLinkedList<>();
	}
	
	public void enqueue(E e){
		list.addLast(e);
	}
	
	public E dequeue(){
		return list.removeFirst();
	}
	
	public int getSize(){
		return list.size();
	}
	
	public boolean isEmpty(){
		return this.getSize() == 0;
	}
	
	public String toString(){
		return "Queue: " + list.toString();
	}
	
	public MyQueue<E> clone(){
		MyQueue<E> queue = new MyQueue<>();
		
		for(int i = 0; i < list.size(); i++)
			queue.enqueue(list.get(i));
		
		
		return queue;
	}

}
