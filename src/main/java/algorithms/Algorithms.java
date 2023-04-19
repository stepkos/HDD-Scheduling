package algorithms;

import statistics.Statistic;
import task.Task;
import task.TaskList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Algorithms {

    private final int maxHeadPosition;
    private final int realTimeDeadline;

    public Algorithms(int maxHeadPosition, int realTimeDeadline) {
        this.maxHeadPosition = maxHeadPosition;
        this.realTimeDeadline = realTimeDeadline;
    }

    public Statistic FIFO(TaskList taskList) {
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
                stats.addToSeekTime(actualTask.getDistance(headPosition));
                headPosition = actualTask.getPosition();

                List<Task> actualTasks = actualList.stream().filter(task -> task.getPosition() == actualTask.getPosition()).toList();
                actualTasks.forEach(task -> {
                    if (task.isRealTime() && stats.getSeekTime() + stats.getBreakTime() > task.getArrivalTime() + realTimeDeadline)
                        stats.addToStarvedRealTimeTasks(1);
                    task.execute();
                    taskList.getList().remove(task);
                });
            }
        }

        return stats;
    }

    public Statistic FIFO_EDF(TaskList taskList) {
        Statistic stats = new Statistic("FIFO_EDF");
        int listSize = taskList.getList().size();
        int headPosition = new Random(listSize).nextInt(maxHeadPosition);
        taskList.sort(Comparator.comparingInt(Task::getArrivalTime));

        while (!taskList.isDone()) {
            List<Task> actualList = taskList.getTasksToDoList(stats.getSeekTime() + stats.getBreakTime());
            if (actualList.isEmpty())
                stats.addToBreakTime(1);
            else {

                // EDF Strategy
                Optional<Task> edfTaskOpt = actualList.stream()
                        .filter(Task::isRealTime)
                        .min(Comparator.comparingInt(task ->
                                task.getDistance(stats.getSeekTime() + stats.getBreakTime())));

                if (edfTaskOpt.isPresent()) {
                    Task edfTask = edfTaskOpt.get();
                    stats.addToSeekTime(edfTask.getDistance(headPosition));
                    headPosition = edfTask.getPosition();

                    if (stats.getSeekTime() + stats.getBreakTime() > edfTask.getArrivalTime() + realTimeDeadline)
                        stats.addToStarvedRealTimeTasks(1);

                    edfTask.execute();
                    taskList.getList().remove(edfTask);
                    continue;
                }
                // End of EDF Strategy


                Task actualTask = actualList.get(0);
                stats.addToSeekTime(actualTask.getDistance(headPosition));
                headPosition = actualTask.getPosition();

                List<Task> actualTasks = actualList.stream().filter(task -> task.getPosition() == actualTask.getPosition()).toList();
                actualTasks.forEach(task -> {
                    if (task.isRealTime() && stats.getSeekTime() + stats.getBreakTime() > task.getArrivalTime() + realTimeDeadline)
                        stats.addToStarvedRealTimeTasks(1);
                    task.execute();
                    taskList.getList().remove(task);
                });
            }
        }

        return stats;
    }

    public Statistic SSTF(TaskList taskList) {
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
                stats.addToSeekTime(closestTask.getDistance(headPosition));
                headPosition = closestTask.getPosition();

                List<Task> actualTasks = actualList.stream().filter(task -> task.getPosition() == closestTask.getPosition()).toList();
                actualTasks.forEach(task -> {
                    if (task.isRealTime() && stats.getSeekTime() + stats.getBreakTime() > task.getArrivalTime() + realTimeDeadline)
                        stats.addToStarvedRealTimeTasks(1);
                    task.execute();
                    taskList.getList().remove(task);
                });
            }
        }

        return stats;
    }

    public Statistic SCAN(TaskList taskList) {
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
                        if (task.isRealTime() && stats.getSeekTime() + stats.getBreakTime() > task.getArrivalTime() + realTimeDeadline)
                            stats.addToStarvedRealTimeTasks(1);
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
    public Statistic C_SCAN(TaskList taskList) {
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
                        if (task.isRealTime() && stats.getSeekTime() + stats.getBreakTime() > task.getArrivalTime() + realTimeDeadline)
                            stats.addToStarvedRealTimeTasks(1);
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
