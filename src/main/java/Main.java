import task.TasksGenerator;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        TasksGenerator tasksGenerator = new TasksGenerator(100, 10, 10);
        tasksGenerator.generateTaskList(10, 0).printAll();

    }
}
