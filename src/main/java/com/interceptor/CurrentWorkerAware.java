package com.interceptor;

import com.modules.worker.bean.Worker;

public interface CurrentWorkerAware {

	public void setCurrentWorker(Worker currentWorker);
	
	public Worker getCurrentWorker();
}
