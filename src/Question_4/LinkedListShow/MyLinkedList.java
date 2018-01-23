package Question_4.LinkedListShow;

import Question_4.MyAbstractList;

public class MyLinkedList<E> extends MyAbstractList<E> {
	private Node<E> head, tail;

	public MyLinkedList() {

	}

	public MyLinkedList(E[] objects) {
		super(objects);
	}

	public E getFirst() {
		if (size == 0)
			return null;
		else
			return head.element;
	}

	public E getLast() {
		if (size == 0)
			return null;
		else
			return tail.element;

	}

	public void addFirst(E e) {
		Node<E> newNode = new Node<E>(e);
		newNode.next = head;
		head = newNode;
		size++;
		if (tail == null)
			tail = head;
	}

	public void addLast(E e) {
		Node<E> newNode = new Node<>(e);

		if (tail == null)
			head = tail = newNode;
		else {
			tail.next = newNode;
			tail = tail.next;
		}

		size++;
	}

	@Override
	public void add(int index, E e) {
		if (index == 0)
			addFirst(e);

		else if (index >= size)
			addLast(e);

		else {
			Node<E> current = head;
			for (int i = 1; i < index; i++) {
				current = current.next;
			}
			Node<E> temp = current.next;
			current.next = new Node<E>(e);
			(current.next).next = temp;
			size++;

		}
	}

	public E removeFirst() {
		if (size == 0)
			return null;
		else if (size == 1) {
			Node<E> temp = head;
			head = tail = null;
			size = 0;
			return temp.element;
		} else {
			Node<E> temp = head;
			head = head.next;
			temp.next = null;
			size--;

			return temp.element;
		}
	}

	public E removeLast() {
		if (size == 0)
			return null;
		else if (size == 1) {
			Node<E> temp = head;
			head = tail = null;
			size = 0;
			return temp.element;
		} else {
			Node<E> current = head;

			for (int i = 0; i < size - 2; i++)
				current = current.next;

			Node<E> temp = tail;
			tail = current;
			tail.next = null;
			size--;

			return temp.element;
		}
	}

	public E remove(int index) {
		if (index < 0 || index >= size)
			return null;
		else if (index == 0)
			return removeFirst();
		else if (index == size - 1)
			return removeLast();
		else {
			Node<E> previous = head;

			for (int i = 1; i < index; i++) {
				previous = previous.next;
			}

			Node<E> current = previous.next;
			previous.next = current.next;
			size--;
			return current.element;
		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder("[");

		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			result.append(current.element);
			current = current.next;
			if (current != null) {
				result.append(", ");
			} else
				result.append("]");
		}

		return result.toString();
	}

	public void clear() {
		head = tail = null;
	}

	public boolean contains(E e) {
		Node<E> temp = head;
		if (temp.element.equals(e))
			return true;
		while (temp.next != null) {
			temp = temp.next;

			if (temp.element.equals(e))
				return true;
		}

		return false;
	}

	public E get(int index) {
		if (index == 0)
			return head.element;
		else if (index == size - 1)
			return tail.element;
		else if (index >= size)
			return null;
		else {
			Node<E> temp = head;

			for (int i = 0; i < index; i++)
				temp = temp.next;

			return temp.element;
		}
	}

	public int indexOf(E e) {
		int index = 0;

		Node<E> temp = head;
		if (temp.element.equals(e))
			return index;
		while (temp.next != null) {
			temp = temp.next;
			index++;

			if (temp.element.equals(e))
				return index;
		}

		return -1;
	}

	public int lastIndexOf(E e) {
		int index = -1;

		Node<E> temp = head;
		if (temp.element.equals(e))
			index = 0;
		for (int i = 1; i < size; i++) {
			temp = temp.next;

			if (temp.element.equals(e))
				index = i;
		}

		return index;
	}

	public E set(int index, E e) {
		if (index == 0) {
			Node<E> newNode = new Node<E>(e);
			Node<E> oldNode = head;
			newNode.next = head.next;
			head = newNode;
			oldNode.next = null;
			return oldNode.element;
		} else if (index >= size) {
			return null;
		} else {
			Node<E> current = head;
			for (int i = 1; i < index; i++)
				current = current.next;

			Node<E> newNode = new Node<E>(e);
			Node<E> oldNode = current.next;
			current.next = newNode;
			current.next.next = oldNode.next;
			oldNode.next = null;
			return oldNode.element;
		}
	}

	private static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element) {
			this.element = element;
		}
	}
}
