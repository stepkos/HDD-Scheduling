import algorithms.Algorithms;
import statistics.Statistic;
import task.TasksGenerator;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int maxArrivalTime = 10000;
        int maxHeadPosition = 1000;
        int realTimePercent = 10;
        int amountOfTasks = 10000;
        int realTimeDeadline = 1000;

        TasksGenerator tasksGenerator = new TasksGenerator(maxArrivalTime, maxHeadPosition, realTimePercent);
        Algorithms algorithms = new Algorithms(maxHeadPosition, realTimeDeadline);

        Statistic fifoStats = algorithms.FIFO(tasksGenerator.generateTaskList(amountOfTasks, 0));
        Statistic ssftStats = algorithms.SSTF(tasksGenerator.generateTaskList(amountOfTasks, 0));
        Statistic scanStats = algorithms.SCAN(tasksGenerator.generateTaskList(amountOfTasks, 0));
        Statistic c_scanStats = algorithms.C_SCAN(tasksGenerator.generateTaskList(amountOfTasks, 0));

        System.out.println(fifoStats);
        System.out.println(ssftStats);
        System.out.println(scanStats);
        System.out.println(c_scanStats);

    }
}
