package Question_4.StackShow;

import Question_4.ArrayListShow.MyArrayList;
 
public class MyStack<E> {
	MyArrayList<E> list = null;
	
	
	public MyStack() {
		list = new MyArrayList<>();
	}

	public void push(E e){
		list.add(e);
	}
	
	public E pop(){
		return list.remove(list.size() - 1);
	}
	
	public E peek(){
		return list.get(list.size() - 1);
	}
	
	public int getSize(){
		return list.size();
	}
	
	public boolean isEmpty(){
		return list.size() == 0;
	}
	
	public MyStack<E> clone(){
		MyStack<E> temp = new MyStack<>();	
		MyArrayList<E> list = this.list.clone();
		temp.list = list;
		
		return temp;
	}
}
