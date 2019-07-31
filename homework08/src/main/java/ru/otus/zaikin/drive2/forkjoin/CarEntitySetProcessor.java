package ru.otus.zaikin.drive2.forkjoin;

import lombok.extern.log4j.Log4j2;
import ru.otus.zaikin.framework.AppProperties;

import java.net.MalformedURLException;
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
        if (task.size <= Integer.parseInt(AppProperties.getInstance().getProperty("app.scan.threshold"))) {
            int compute = 0;
            try {
                compute = task.compute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return compute;
        } else {
            int mid = task.size / 2;
            RecursiveTask<Integer> recursiveTask = new CarEntitySetProcessor(task.createSubTask(0, mid));
            recursiveTask.fork();
            return new CarEntitySetProcessor(task.createSubTask(mid, task.size)).compute() + recursiveTask.join();
        }
    }

    public void startProcessing() {
        ForkJoinPool fjPool = new ForkJoinPool(Integer.parseInt(AppProperties.getInstance().getProperty("app.scan.threads")));
        int objectProcessed = fjPool.invoke(this);
        log.debug("Processed objects:" + objectProcessed);
    }
}