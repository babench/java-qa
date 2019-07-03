package ru.otus.zaikin.drive2.forkjoin;

import lombok.extern.log4j.Log4j2;
import ru.otus.zaikin.drive2.Drive2Config;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Log4j2
public class CarEntitySetProcessor extends RecursiveTask<Integer> {
    private TaskCarEntitySetPopulateDetails task;

    public CarEntitySetProcessor(TaskCarEntitySetPopulateDetails task) {
        this.task = task;
    }

    @Override
    protected Integer compute() {
        if (task.size <= Drive2Config.THRESHOLD) return task.compute();
        else {
            int mid = task.size / 2;
            RecursiveTask<Integer> recursiveTask = new CarEntitySetProcessor(task.createSubTask(0, mid, task.getDao()));
            recursiveTask.fork();
            return new CarEntitySetProcessor(task.createSubTask(mid, task.size, task.getDao())).compute() + recursiveTask.join();
        }
    }

    public void startProcessing() {
        ForkJoinPool fjPool = new ForkJoinPool(Drive2Config.THREADS_NUMBER);
        int objectProcessed = fjPool.invoke(this);
        log.debug("Processed objects:" + objectProcessed);
    }
}