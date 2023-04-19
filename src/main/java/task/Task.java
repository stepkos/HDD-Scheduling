package task;

public class Task {
    private final int id;
    private final int arrivalTime;
    private final int position;
    private final boolean realTime;
    private boolean isDone = false;

    public Task(int id, int arrivalTime, int position) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.position = position;
        this.realTime = false;
    }

    public Task(int id, int arrivalTime, int position, boolean realTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.position = position;
        this.realTime = realTime;
    }

    public void execute() {
        isDone = true;
    }

    public boolean exists(int time) {
        return time >= arrivalTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPosition() {
        return position;
    }

    public boolean isRealTime() {
        return realTime;
    }

    public int getDistance(int headPosition) {
        return Math.abs(headPosition - position);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", position=" + position +
                ", realTime=" + realTime +
                ", isDone=" + isDone +
                '}';
    }

}
