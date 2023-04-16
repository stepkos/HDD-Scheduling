package task;

import java.util.Comparator;
import java.util.List;

// Powiedz ze czytales w internecie i rozszerzanie implementacji klasy java.lang to zla praktyka

public class TaskList {
    private final List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getList() {
        return tasks;
    }

    public List<Task> getTasksToDoList(int time) {
        return tasks.stream()
                .filter(x -> x.exists(time))
                .filter(x -> !x.isDone())
                .toList();
    }

    public TaskList getTasksToDo(int time) {
        return new TaskList(tasks.stream()
                .filter(x -> x.exists(time))
                .filter(x -> !x.isDone())
                .toList());
    }

    public boolean isTasksToDo(int time) {
        return tasks.stream()
                .filter(x -> x.exists(time) && !x.isDone())
                .findFirst().isEmpty();
    }

    public void sort(Comparator<? super Task> comp) {
        tasks.sort(comp);
    }

    public boolean isDone() {
        return tasks.stream().allMatch(Task::isDone);
    }

    public void printAll() {
        tasks.forEach(System.out::println);
    }

}
