package com.abhishek.assignment.customthreadpool.threadpools;

class ThreadPoolsThread extends Thread {

	private BlockingQueue<Runnable> taskQueue;
	private ThreadPool threadPool;

	public ThreadPoolsThread(BlockingQueue<Runnable> queue, ThreadPool threadPool) {
		taskQueue = queue;
		this.threadPool = threadPool;

	}

	public void run() {
		try {
			while (true) {
				Runnable runnable = taskQueue.take();
				runnable.run();
				if (this.threadPool.isPoolShutDownInitiated() && this.taskQueue.size() == 0) {
					this.interrupt();
					Thread.sleep(1);
				}
			}
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + " has been STOPPED.");
		}
	}
}