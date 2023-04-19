import algorithms.Algorithms;
import statistics.Statistic;
import task.TasksGenerator;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int maxArrivalTime = 10000;
        int maxHeadPosition = 100;
        int realTimePercent = 10;
        int amountOfTasks = 1000;

        TasksGenerator tasksGenerator = new TasksGenerator(maxArrivalTime, maxHeadPosition, realTimePercent);

        Statistic fifoStats = Algorithms.FIFO(tasksGenerator.generateTaskList(amountOfTasks, 0), maxHeadPosition);
        Statistic ssftStats = Algorithms.SSTF(tasksGenerator.generateTaskList(amountOfTasks, 0), maxHeadPosition);
        System.out.println(fifoStats);
        System.out.println(ssftStats);

    }
}
