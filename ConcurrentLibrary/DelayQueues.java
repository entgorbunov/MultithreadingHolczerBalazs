import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueues {

	public static void main(String[] args) {
		BlockingQueue<DelayedWorker> blockingQueue = new DelayQueue<>();

		try {
			blockingQueue.put(new DelayedWorker(1000, "Task 1", () -> System.out.println("Executing Task 1")));
			blockingQueue.put(new DelayedWorker(10000, "Task 2", () -> System.out.println("Executing Task 2")));
			blockingQueue.put(new DelayedWorker(4000, "Task 3", () -> System.out.println("Executing Task 3")));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (!blockingQueue.isEmpty()) {
			try {
				blockingQueue.take().executeTask();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class DelayedWorker implements Delayed {

	private long duration;
	private String message;
	private Runnable task; // Добавлен Runnable для выполнения задачи

	public DelayedWorker(long duration, String message, Runnable task) {
		this.duration = System.currentTimeMillis() + duration;
		this.message = message;
		this.task = task;
	}

	@Override
	public int compareTo(Delayed otherDelayed) {
		return Long.compare(this.duration, ((DelayedWorker) otherDelayed).getDuration());
	}

	@Override
	public long getDelay(TimeUnit timeUnit) {
		return timeUnit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	public void executeTask() {
		System.out.println(message);
		task.run();
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Runnable getTask() {
		return task;
	}

	public void setTask(Runnable task) {
		this.task = task;
	}

	// Остальные методы класса...
}
