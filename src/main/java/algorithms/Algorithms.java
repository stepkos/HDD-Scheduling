package algorithms;

import statistics.Statistic;
import task.Task;
import task.TaskList;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Algorithms {

    // TODO obsloz ze moze byc kilka zadan w jednym miejscu

    public static Statistic FIFO(TaskList taskList, int maxHeadPosition) {
        Statistic stats = new Statistic("FIFO");
        int listSize = taskList.getList().size();
        int headPosition = new Random(listSize).nextInt(maxHeadPosition);
        taskList.sort(Comparator.comparingInt(Task::getArrivalTime));

        while (!taskList.isDone()) {
            List<Task> actualList = taskList.getTasksToDoList(stats.getSeekTime() + stats.getBreakTime());
            if (actualList.isEmpty())
                stats.addToBreakTime(1);
            else {
                Task actualTask = actualList.get(0);
                int distance = actualTask.getDistance(headPosition);
                stats.addToSeekTime(distance);
                actualTask.execute();
                headPosition = actualTask.getPosition();
                taskList.getList().remove(actualTask);
            }
        }

        return stats;
    }

    public static Statistic SSTF(TaskList taskList, int maxHeadPosition) {
        Statistic stats = new Statistic("SSTF");
        int listSize = taskList.getList().size();
        int headPosition = new Random(listSize).nextInt(maxHeadPosition);

        while (!taskList.isDone()) {
            List<Task> actualList = taskList.getTasksToDoList(stats.getSeekTime() + stats.getBreakTime());
            if (actualList.isEmpty())
                stats.addToBreakTime(1);
            else {
                int finalHeadPosition = headPosition;
                Task closestTask = actualList.stream().min(Comparator.comparingInt(task -> task.getDistance(finalHeadPosition))).get();
                int distance = closestTask.getDistance(headPosition);
                stats.addToSeekTime(distance);
                closestTask.execute();
                headPosition = closestTask.getPosition();
                taskList.getList().remove(closestTask);
            }
        }

        return stats;
    }

    public static Statistic SCAN(TaskList taskList, int maxHeadPosition) {
        Statistic stats = new Statistic("SCAN");
        int listSize = taskList.getList().size();
        int headPosition = new Random(listSize).nextInt(maxHeadPosition);
        boolean increaseHead = new Random().nextBoolean();

        while (!taskList.isDone()) {
            // That's only for checking if there is any task to do
            List<Task> actualList = taskList.getTasksToDoList(stats.getSeekTime() + stats.getBreakTime());
            if (actualList.isEmpty())
                stats.addToBreakTime(1);
            else {
                int finalHeadPosition = headPosition;
                List<Task> actualTasks = actualList.stream().filter(task -> task.getPosition() == finalHeadPosition).toList();
                if (!actualTasks.isEmpty()) {
                    actualTasks.forEach(task -> {
                        task.execute();
                        taskList.getList().remove(task);
                    });
                }

                if (increaseHead) {
                    if (headPosition == maxHeadPosition) {
                        increaseHead = false;
                        headPosition--;
                    }
                    else headPosition++;
                }
                else {
                    if (headPosition == 0) {
                        increaseHead = true;
                        headPosition++;
                    }
                    else headPosition--;
                }

                stats.addToSeekTime(1);
            }
        }

        return stats;
    }
    public static Statistic C_SCAN(TaskList taskList, int maxHeadPosition) {
        Statistic stats = new Statistic("C-SCAN");
        int listSize = taskList.getList().size();
        int headPosition = new Random(listSize).nextInt(maxHeadPosition);

        while (!taskList.isDone()) {
            // That's only for checking if there is any task to do
            List<Task> actualList = taskList.getTasksToDoList(stats.getSeekTime() + stats.getBreakTime());
            if (actualList.isEmpty())
                stats.addToBreakTime(1);
            else {
                int finalHeadPosition = headPosition;
                List<Task> actualTasks = actualList.stream().filter(task -> task.getPosition() == finalHeadPosition).toList();
                if (!actualTasks.isEmpty()) {
                    actualTasks.forEach(task -> {
                        task.execute();
                        taskList.getList().remove(task);
                    });
                }

                if (headPosition == maxHeadPosition)
                    headPosition = 0;
                else headPosition++;

                stats.addToSeekTime(1);
            }
        }

        return stats;
    }


}
