package Question_6;

public class mazeStack<E> {
	java.util.LinkedList<E> list = new java.util.LinkedList<>();
	
	public void push(E e){
		list.addLast(e);
	}
	
	public void pushAll(mazeStack<E> stack){
		E[] array = stack.toArray();
		
		for(E e: array)
			push(e);
	}
	
	public E peek(){
		return list.getLast();
	}
	
	public E pop(){
		return list.removeLast();
	}
	
	public int Size(){
		return list.size();
	}
	
	@SuppressWarnings("unchecked")
	public E[] toArray(){
		return (E[])list.toArray();
	}
	
	public String toString(){
		return list.toString();
	}
	
	public boolean isEmpty(){
		return list.size() == 0;
	}
}
