package task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TasksGenerator {
    private final int maxArrivalTime;
    private final int maxTaskPosition;
    private final int realTimePercent;

    public TasksGenerator(int maxArrivalTime, int maxTaskPosition, int realTimePercent) {
        this.maxArrivalTime = maxArrivalTime;
        this.maxTaskPosition = maxTaskPosition;
        this.realTimePercent = realTimePercent;
    }

    public List<Task> generateList(int amount, long seed) {
        Random rand = new Random(seed);
        return IntStream.range(1, amount + 1).mapToObj(i ->
            new Task(i, rand.nextInt(maxArrivalTime), rand.nextInt(maxTaskPosition),
                rand.nextInt(100) < realTimePercent))
            .collect(Collectors.toList());
    }

    public List<Task> generateListOld(int amount, long seed) {
        Random rand = new Random(seed);
        List<Task> taskList = new ArrayList<>();

        for (int i=1; i<=amount; i++) {
            taskList.add(new Task(i,
                    rand.nextInt(0, maxArrivalTime),
                    rand.nextInt(0, maxTaskPosition),
                    rand.nextInt(1, 100) <= realTimePercent
            ));
        }

        return taskList;
    }

    public TaskList generateTaskList(int amount, long seed) {
        return new TaskList(generateList(amount, seed));
    }

}
