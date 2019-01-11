package com.interceptor;

import com.modules.worker.bean.Worker;

public abstract class BaseCurrentWorkerAware implements CurrentWorkerAware {
	
	@SuppressWarnings("rawtypes")
	private ThreadLocal currentWorker = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public void setCurrentWorker(Worker currentWorker) {
		this.currentWorker.set(currentWorker);
	}

	public Worker getCurrentWorker() {
		return (Worker) this.currentWorker.get();
	}
}