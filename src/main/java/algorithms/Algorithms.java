package algorithms;

import statistics.Statistic;
import task.Task;
import task.TaskList;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Algorithms {

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
                int distance = Math.abs(headPosition - actualList.get(0).getPosition());
                stats.addToSeekTime(distance);
                actualList.get(0).execute();
                headPosition = actualList.get(0).getPosition();
                taskList.getList().remove(actualList.get(0));
            }
        }

        return stats;
    }

}
