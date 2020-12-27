package com.abhishek.assignment.customthreadpool.threadpools;

class ThreadPool {

	private BlockingQueue<Runnable> taskQueue;

	private boolean poolShutDownInitiated = false;

	public ThreadPool(int nThreads) {
		taskQueue = new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE);

		for (int i = 1; i <= nThreads; i++) {
			ThreadPoolsThread threadPoolsThread = new ThreadPoolsThread(taskQueue, this);
			threadPoolsThread.setName("Thread-" + i);
			threadPoolsThread.start();
		}
	}

	public ThreadPool(int nThreads, int queueSize) {
		taskQueue = new LinkedBlockingQueue<Runnable>(queueSize);

		for (int i = 1; i <= nThreads; i++) {
			ThreadPoolsThread threadPoolsThread = new ThreadPoolsThread(taskQueue, this);
			threadPoolsThread.setName("Thread-" + i);
			threadPoolsThread.start();
		}
	}

	public synchronized void execute(Runnable task) throws Exception {
		if (this.poolShutDownInitiated)
			throw new Exception("ThreadPool has been shutDown, no further tasks can be added");
		this.taskQueue.put(task);
	}

	public boolean isPoolShutDownInitiated() {
		return poolShutDownInitiated;
	}

	public synchronized void shutdown() {
		this.poolShutDownInitiated = true;
		System.out.println("ThreadPool SHUTDOWN initiated.");
	}

}