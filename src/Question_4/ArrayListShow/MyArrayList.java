package Question_4.ArrayListShow;

import Question_4.MyAbstractList;


public class MyArrayList<E> extends MyAbstractList<E> {
	public static final int INITIAL_CAPACITY = 16;
	@SuppressWarnings("unchecked")
	private E[] data = (E[])new Object[INITIAL_CAPACITY];

	public MyArrayList() {

	}

	public MyArrayList(E[] objects) {
		for (int i = 0; i < objects.length; i++) {
			add(objects[i]);
		}
	}

	@Override
	public void add(int index, E e) {
		ensureCapacity();

		for (int i = size - 1; i >= index; i--)
			data[i + 1] = data[i];

		data[index] = e;

		size++;
	}

	private void ensureCapacity() {
		if (size >= data.length) {
			@SuppressWarnings("unchecked")
			E[] newData = (E[]) (new Object[size * 2 + 1]);
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		data = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	@Override
	public boolean contains(E e) {
		for (int i = 0; i < size; i++)
			if (e.equals(data[i]))
				return true;
		return false;
	}

	@Override
	public E get(int index) {
		return data[index];
	}

	@Override
	public int indexOf(E e) {
		for (int i = 0; i < size; i++)
			if (e.equals(data[i]))
				return i;

		return -1;
	}

	@Override
	public int lastIndexOf(E e) {
		for (int i = size - 1; i >= 0; i--)
			if (e.equals(data[i]))
				return i;

		return -1;
	}
	
	@Override
	public E remove(int index) {
		E e = data[index];

		for (int j = index; j < size - 1; j++)
			data[j] = data[j + 1];

		data[size - 1] = null;
		
		size--;
		return e;
	}


	public E removeIndex(int index) {
		E e = data[index];

		for (int j = index; j < size - 1; j++)
			data[j] = data[j + 1];

		data[size - 1] = null;
		
		size--;
		return e;
	}

	@Override
	public Object set(int index, E e) {
		E old = data[index];
		data[index] = e;
		return old;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("[");

		for (int i = 0; i < size; i++) {
			result.append(data[i]);
			if (i < size - 1)
				result.append(", ");
		}

		return result.toString() + "]";
	}

	public void trimToSize() {
		if (size != data.length) {
			@SuppressWarnings("unchecked")
			E[] newData = (E[]) (new Object[size]);
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}
	
	public int getCapacity(){
		return data.length;
	}
	
	public MyArrayList<E> clone(){
		MyArrayList<E> list = new MyArrayList<>();
		
		list.data = data.clone();
		list.size = this.size;
		
		return list;
	}
}
