package com.abhishek.assignment.customthreadpool.threadpools;

import java.util.LinkedList;
import java.util.List;

class LinkedBlockingQueue<E> implements BlockingQueue<E> {

	private List<E> queue;
	private int maxSize; 

	public LinkedBlockingQueue(int maxSize) {
		this.maxSize = maxSize;
		queue = new LinkedList<E>();
	}

	public synchronized void put(E item) throws InterruptedException {

		if (queue.size() == maxSize) {
			this.wait();
		}

		queue.add(item);
		this.notifyAll();
	}

	public synchronized E take() throws InterruptedException {

		if (queue.size() == 0) {
			this.wait();
		}

		this.notifyAll();
		return queue.remove(0);

	}

	public synchronized int size() {
		return queue.size();
	}

}
